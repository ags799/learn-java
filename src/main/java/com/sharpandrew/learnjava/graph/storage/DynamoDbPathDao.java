package com.sharpandrew.learnjava.graph.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterables;
import com.sharpandrew.learnjava.graph.model.GraphPath;
import com.sharpandrew.learnjava.graph.model.ImmutableGraphPath;
import com.sharpandrew.learnjava.serverless.Environment;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DynamoDbPathDao implements PathDao {
  private static DynamoDbPathDao instance = null;

  private final DynamoDBMapper mapper;

  @VisibleForTesting
  DynamoDbPathDao(DynamoDBMapper mapper) {
    this.mapper = mapper;
  }

  public static DynamoDbPathDao getInstance() {
    if (instance == null) {
      String tableName = Environment.getPathTableName();
      DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
          .withTableNameOverride(
              DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(tableName))
          .build();
      DynamoDBMapper mapper = new DynamoDBMapper(
          AmazonDynamoDBClientBuilder.defaultClient(), config);
      instance = new DynamoDbPathDao(mapper);
      return instance;
    } else {
      return instance;
    }
  }

  @Override
  public GraphPath get(String pathId) {
    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
    scanExpression.addFilterCondition(
        "pathId",
        new Condition()
            .withComparisonOperator(ComparisonOperator.EQ)
            .withAttributeValueList(new AttributeValue(pathId)));
    List<StoragePathEdge> matchingEdges = mapper.scan(StoragePathEdge.class, scanExpression);
    List<Integer> vertices = matchingEdges.stream()
        .map(StoragePathEdge::getStartVertex)
        .collect(Collectors.toList());
    vertices.add(Iterables.getLast(matchingEdges).getEndVertex());
    return ImmutableGraphPath.builder()
        .verticesStartToFinish(vertices)
        .build();
  }

  @Override
  public void put(String pathId, GraphPath graphPath) {
    Set<StoragePathEdge> storagePathEdges = IntStream
        .range(0, graphPath.verticesStartToFinish().size() - 1)
        .boxed()
        .map(place -> {
          StoragePathEdge storagePathEdge = new StoragePathEdge();
          storagePathEdge.setPathId(pathId);
          storagePathEdge.setPlace(place);
          storagePathEdge.setStartVertex(graphPath.verticesStartToFinish().get(place));
          storagePathEdge.setEndVertex(graphPath.verticesStartToFinish().get(place + 1));
          return storagePathEdge;
        })
        .collect(Collectors.toSet());
    List<DynamoDBMapper.FailedBatch> failedBatches = mapper.batchSave(storagePathEdges);
    if (!failedBatches.isEmpty()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Failed to save some batches:\n");
      failedBatches.forEach(batch -> {
        stringBuilder.append('\t');
        stringBuilder.append(batch.getUnprocessedItems());
        stringBuilder.append("\t\t");
        stringBuilder.append(batch.getException());
      });
      throw new RuntimeException(stringBuilder.toString());
    }
  }
}

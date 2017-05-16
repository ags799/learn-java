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
import com.sharpandrew.learnjava.graph.storage.dynamodb.DynamoDbPathEdge;
import com.sharpandrew.learnjava.serverless.Environment;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DefaultPathTable implements PathTable {
  private static DefaultPathTable instance = null;

  private final DynamoDBMapper mapper;

  @VisibleForTesting
  DefaultPathTable(DynamoDBMapper mapper) {
    this.mapper = mapper;
  }

  public static DefaultPathTable getInstance() {
    if (instance == null) {
      String tableName = Environment.getPathTableName();
      DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
          .withTableNameOverride(
              DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(tableName))
          .build();
      DynamoDBMapper mapper = new DynamoDBMapper(
          AmazonDynamoDBClientBuilder.defaultClient(), config);
      instance = new DefaultPathTable(mapper);
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
    List<DynamoDbPathEdge> matchingEdges = mapper.scan(DynamoDbPathEdge.class, scanExpression);
    List<Integer> vertices = matchingEdges.stream()
        .map(DynamoDbPathEdge::getStartVertex)
        .collect(Collectors.toList());
    vertices.add(Iterables.getLast(matchingEdges).getEndVertex());
    return ImmutableGraphPath.builder()
        .verticesStartToFinish(vertices)
        .build();
  }

  @Override
  public String post(GraphPath graphPath) {
    String pathId = UUID.randomUUID().toString();
    Set<DynamoDbPathEdge> dynamoDbPathEdges = IntStream
        .range(0, graphPath.verticesStartToFinish().size() - 1)
        .boxed()
        .map(place -> {
          DynamoDbPathEdge dynamoDbPathEdge = new DynamoDbPathEdge();
          dynamoDbPathEdge.setPathId(pathId);
          dynamoDbPathEdge.setPlace(place);
          dynamoDbPathEdge.setStartVertex(graphPath.verticesStartToFinish().get(place));
          dynamoDbPathEdge.setEndVertex(graphPath.verticesStartToFinish().get(place + 1));
          return dynamoDbPathEdge;
        })
        .collect(Collectors.toSet());
    List<DynamoDBMapper.FailedBatch> failedBatches = mapper.batchSave(dynamoDbPathEdges);
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
    } else {
      return pathId;
    }
  }
}

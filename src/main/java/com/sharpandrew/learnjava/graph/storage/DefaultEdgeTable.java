package com.sharpandrew.learnjava.graph.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import com.sharpandrew.learnjava.graph.storage.dynamodb.DynamoDbEdge;
import com.sharpandrew.learnjava.serverless.Environment;
import java.util.List;
import java.util.Set;

public final class DefaultEdgeTable implements EdgeTable {
  private static DefaultEdgeTable instance = null;

  private final DynamoDBMapper mapper;

  @VisibleForTesting
  DefaultEdgeTable(DynamoDBMapper mapper) {
    this.mapper = mapper;
  }

  public static DefaultEdgeTable getInstance() {
    if (instance == null) {
      String tableName = Environment.getEdgeTableName();
      DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
          .withTableNameOverride(
              DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(tableName))
          .build();
      DynamoDBMapper mapper = new DynamoDBMapper(
          AmazonDynamoDBClientBuilder.defaultClient(), config);
      instance = new DefaultEdgeTable(mapper);
      return instance;
    } else {
      return instance;
    }
  }

  @Override
  public Set<DynamoDbEdge> get(String graphId) {
    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
    scanExpression.addFilterCondition(
        "graphId",
        new Condition()
            .withComparisonOperator(ComparisonOperator.EQ)
            .withAttributeValueList(new AttributeValue(graphId)));
    List<DynamoDbEdge> matchingEdges = mapper.scan(DynamoDbEdge.class, scanExpression);
    return ImmutableSet.<DynamoDbEdge>builder().addAll(matchingEdges).build();
  }

  @Override
  public Set<DynamoDbEdge> getAll() {
    List<DynamoDbEdge> allEdges = mapper.scan(
        DynamoDbEdge.class, new DynamoDBScanExpression());
    return ImmutableSet.<DynamoDbEdge>builder().addAll(allEdges).build();
  }

  @Override
  public void post(Set<DynamoDbEdge> edges) {
    List<DynamoDBMapper.FailedBatch> failedBatches = mapper.batchSave(edges);
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

  @Override
  public void delete(String graphId) {
    Set<DynamoDbEdge> edgesToDelete = get(graphId);
    mapper.batchDelete(edgesToDelete);
  }
}

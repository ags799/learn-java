package com.sharpandrew.learnjava.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import com.sharpandrew.learnjava.serverless.ServerlessEnvironment;
import java.util.List;
import java.util.Set;

public final class DynamoDbEdgeDao implements EdgeDao {
  private static DynamoDbEdgeDao instance = null;

  private final DynamoDBMapper mapper;

  @VisibleForTesting
  DynamoDbEdgeDao(DynamoDBMapper mapper) {
    this.mapper = mapper;
  }

  public static DynamoDbEdgeDao getInstance() {
    if (instance == null) {
      String tableName = ServerlessEnvironment.getEdgeTableName();
      DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
          .withTableNameOverride(
              DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(tableName))
          .build();
      DynamoDBMapper mapper = new DynamoDBMapper(
          AmazonDynamoDBClientBuilder.defaultClient(), config);
      instance = new DynamoDbEdgeDao(mapper);
      return instance;
    } else {
      return instance;
    }
  }

  @Override
  public Set<StorageEdge> get(String graphId) {
    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
    scanExpression.addFilterCondition(
        "graphId",
        new Condition()
            .withComparisonOperator(ComparisonOperator.EQ)
            .withAttributeValueList(new AttributeValue(graphId)));
    List<StorageEdge> matchingEdges = mapper.scan(StorageEdge.class, scanExpression);
    return ImmutableSet.<StorageEdge>builder().addAll(matchingEdges).build();
  }

  @Override
  public Set<StorageEdge> getAll() {
    List<StorageEdge> allEdges = mapper.scan(
        StorageEdge.class, new DynamoDBScanExpression());
    return ImmutableSet.<StorageEdge>builder().addAll(allEdges).build();
  }

  @Override
  public void post(Set<StorageEdge> edges) {
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
    Set<StorageEdge> edgesToDelete = get(graphId);
    mapper.batchDelete(edgesToDelete);
  }
}

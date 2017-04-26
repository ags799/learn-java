package com.serverless.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;

public class DynamoDbEdgeDao implements EdgeDao {
  private static final DynamoDBMapper mapper = new DynamoDBMapper(
      AmazonDynamoDBClientBuilder.defaultClient());

  private static DynamoDbEdgeDao instance = null;

  private DynamoDbEdgeDao() {}

  /** Use to get instances of this class. */
  public static DynamoDbEdgeDao getInstance() {
    if (instance == null) {
      instance = new DynamoDbEdgeDao();
    }
    return instance;
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
    mapper.batchLoad(edges);
  }

  @Override
  public void delete(String graphId) {
    Set<StorageEdge> edgesToDelete = get(graphId);
    mapper.batchDelete(edgesToDelete);
  }
}

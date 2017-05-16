package com.sharpandrew.learnjava.graph.storage.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.google.common.collect.ImmutableSet;
import com.sharpandrew.learnjava.graph.storage.Dao;
import java.util.List;
import java.util.Set;

public final class DynamoDbDao<T> implements Dao<T> {
  private final DynamoDBMapper mapper;
  private final Class<T> storageClass;

  private DynamoDbDao(DynamoDBMapper mapper, Class<T> storageClass) {
    this.storageClass = storageClass;
    this.mapper = mapper;
  }

  public static <T> DynamoDbDao<T> create(Class<T> storageClass, String tableName) {
    DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
        .withTableNameOverride(
            DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(tableName))
        .build();
    DynamoDBMapper dynamoDbMapper =
        new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient(), config);
    return new DynamoDbDao<T>(dynamoDbMapper, storageClass);
  }

  @Override
  public List<T> getMatchingRows(String key, String value) {
    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
    scanExpression.addFilterCondition(
        key,
        new Condition()
            .withComparisonOperator(ComparisonOperator.EQ)
            .withAttributeValueList(new AttributeValue(value)));
    return mapper.scan(storageClass, scanExpression);
  }

  @Override
  public Set<T> getAllRows() {
    return ImmutableSet.copyOf(mapper.scan(storageClass, new DynamoDBScanExpression()));
  }

  @Override
  public void saveAll(Set<T> items) {
    handleFailedBatches(mapper.batchSave(items));
  }

  @Override
  public void deleteAll(List<T> edgesToDelete) {
    handleFailedBatches(mapper.batchDelete(edgesToDelete));
  }

  private void handleFailedBatches(List<DynamoDBMapper.FailedBatch> failedBatches) {
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

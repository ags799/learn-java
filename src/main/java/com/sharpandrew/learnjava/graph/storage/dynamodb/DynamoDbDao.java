package com.sharpandrew.learnjava.graph.storage.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.sharpandrew.learnjava.graph.storage.Dao;

public final class DynamoDbDao<T> implements Dao {
  private final DynamoDBMapper mapper;
  private final Class<T> storageClass;

  private DynamoDbDao(DynamoDBMapper mapper, Class<T> storageClass) {
    this.storageClass = storageClass;
    this.mapper = mapper;
  }

  static <T> DynamoDbDao<T> create(Class<T> storageClass, String tableName) {
    DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
        .withTableNameOverride(
            DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(tableName))
        .build();
    DynamoDBMapper dynamoDbMapper =
        new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient(), config);
    return new DynamoDbDao<T>(dynamoDbMapper, storageClass);
  }
}

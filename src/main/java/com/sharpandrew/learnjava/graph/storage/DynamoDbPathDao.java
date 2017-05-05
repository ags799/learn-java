package com.sharpandrew.learnjava.graph.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.graph.model.Path;
import com.sharpandrew.learnjava.serverless.Environment;
import java.util.Set;

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
  public Path get(String pathId) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<Path> getForGraph(String graphId) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<Path> getAll() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void put(String graphId, String pathId, Path path) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void delete(String pathId) {
    throw new UnsupportedOperationException();
  }
}

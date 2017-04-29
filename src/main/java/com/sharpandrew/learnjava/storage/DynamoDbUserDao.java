package com.sharpandrew.learnjava.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.serverless.Environment;

public final class DynamoDbUserDao implements UserDao {
  private static DynamoDbUserDao instance;

  private final DynamoDBMapper mapper;

  @VisibleForTesting
  DynamoDbUserDao(DynamoDBMapper mapper) {
    this.mapper = mapper;
  }

  public static UserDao getInstance() {
    if (instance == null) {
      String tableName = Environment.getUserTableName();
      DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
          .withTableNameOverride(
              DynamoDBMapperConfig.TableNameOverride.withTableNameReplacement(tableName))
          .build();
      DynamoDBMapper mapper = new DynamoDBMapper(
          AmazonDynamoDBClientBuilder.defaultClient(), config);
      instance = new DynamoDbUserDao(mapper);
      return instance;
    } else {
      return instance;
    }
  }

  @Override
  public boolean containsEmail(String email) {
    return mapper.load(StorageUser.class, email) != null;
  }

  @Override
  public void put(String email, String passwordHash, String salt) {
    StorageUser storageUser = new StorageUser();
    storageUser.setEmail(email);
    storageUser.setPasswordHash(passwordHash);
    storageUser.setSalt(salt);
    mapper.save(storageUser);
  }
}

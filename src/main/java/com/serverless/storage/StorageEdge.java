package com.serverless.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * The names used in this class's annotations must match serverless.yml.
 */
@Value.Immutable
@JsonDeserialize(as = ImmutableStorageEdge.class)
@JsonSerialize(as = ImmutableStorageEdge.class)
@DynamoDBTable(tableName = "edges")
public interface StorageEdge {
  @DynamoDBHashKey(attributeName = "graphName")
  String graphName();

  @DynamoDBAttribute(attributeName = "startVertex")
  int startVertex();

  @DynamoDBAttribute(attributeName = "endVertex")
  int endVertex();
}

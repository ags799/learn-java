package com.sharpandrew.learnjava.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

// The names used in this class's annotations must match serverless.yml.
@DynamoDBTable(tableName = "edges")
public final class StorageEdge {
  private String edgeId;
  private String graphId;
  private int startVertex;
  private int endVertex;

  @DynamoDBAutoGeneratedKey
  @DynamoDBHashKey(attributeName = "edgeId")
  public String getEdgeId() {
    return edgeId;
  }

  public void setEdgeId(String edgeId) {
    this.edgeId = edgeId;
  }

  @DynamoDBAttribute(attributeName = "graphId")
  public String getGraphId() {
    return graphId;
  }

  public void setGraphId(String graphId) {
    this.graphId = graphId;
  }

  @DynamoDBAttribute(attributeName = "startVertex")
  public int getStartVertex() {
    return startVertex;
  }

  public void setStartVertex(int startVertex) {
    this.startVertex = startVertex;
  }

  @DynamoDBAttribute(attributeName = "endVertex")
  public int getEndVertex() {
    return endVertex;
  }

  public void setEndVertex(int endVertex) {
    this.endVertex = endVertex;
  }
}

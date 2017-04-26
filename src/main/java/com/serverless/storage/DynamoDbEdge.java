package com.serverless.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * The names used in this class's annotations must match serverless.yml.
 */
@DynamoDBTable(tableName = "edges")
public final class DynamoDbEdge {
  private String graphName;
  private int startVertex;
  private int endVertex;

  @DynamoDBHashKey(attributeName = "graphName")
  public String getGraphName() {
    return graphName;
  }

  public void setGraphName(String graphName) {
    this.graphName = graphName;
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

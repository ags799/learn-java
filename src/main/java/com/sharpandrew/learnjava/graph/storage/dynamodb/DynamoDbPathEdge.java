package com.sharpandrew.learnjava.graph.storage.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

public class DynamoDbPathEdge {
  private String pathId;
  private int place;
  private int startVertex;
  private int endVertex;

  @DynamoDBHashKey(attributeName = "pathId")
  public String getPathId() {
    return pathId;
  }

  public void setPathId(String pathId) {
    this.pathId = pathId;
  }

  @DynamoDBRangeKey(attributeName =  "place")
  public int getPlace() {
    return place;
  }

  public void setPlace(int place) {
    this.place = place;
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

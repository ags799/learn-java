package com.sharpandrew.learnjava.graph.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

public class StoragePathEdge {
  private String pathIdAndPlace;
  private String graphId;
  private String pathId;
  private int place;
  private int startVertex;
  private int endVertex;

  @DynamoDBRangeKey(attributeName =  "pathIdAndPlace")
  public String getPathIdAndPlace() {
    return pathIdAndPlace;
  }

  public void setPathIdAndPlace(String pathIdAndPlace) {
    this.pathIdAndPlace = pathIdAndPlace;
  }

  @DynamoDBAttribute(attributeName = "graphId")
  public String getGraphId() {
    return graphId;
  }

  public void setGraphId(String graphId) {
    this.graphId = graphId;
  }

  @DynamoDBAttribute(attributeName = "pathId")
  public String getPathId() {
    return pathId;
  }

  public void setPathId(String pathId) {
    this.pathId = pathId;
  }

  @DynamoDBAttribute(attributeName = "place")
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

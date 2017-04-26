package com.serverless.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.google.common.collect.Iterables;
import com.serverless.models.Edge;
import com.serverless.models.Graph;
import com.serverless.models.ImmutableEdge;
import com.serverless.models.ImmutableGraph;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DynamoDbGraphDao implements GraphDao {
  private static final DynamoDBMapper mapper = new DynamoDBMapper(
      AmazonDynamoDBClientBuilder.defaultClient());

  private static DynamoDbGraphDao instance = null;

  private DynamoDbGraphDao() {}

  public static DynamoDbGraphDao getInstance() {
    if (instance == null) {
      instance = new DynamoDbGraphDao();
    }
    return instance;
  }

  @Override
  public Graph get(String name) {
    DynamoDbEdge dynamoDbEdge = mapper.load(DynamoDbEdge.class, name);
    Edge edge = ImmutableEdge.builder()
        .startVertex(dynamoDbEdge.getStartVertex())
        .endVertex(dynamoDbEdge.getEndVertex())
        .build();
    return ImmutableGraph.builder().addEdges(edge).build();
  }

  @Override
  public Set<Graph> getAll() {
    List<DynamoDbEdge> dynamoDbEdges = mapper.scan(
        DynamoDbEdge.class, new DynamoDBScanExpression());
    return dynamoDbEdges.stream()
        .collect(Collectors.groupingBy(DynamoDbEdge::getGraphName, Collectors.toSet()))
        .values()
        .stream()
        .map(dynamoDbEdgesByGraph -> dynamoDbEdgesByGraph.stream()
          .map(dynamoDbEdge -> ImmutableEdge.builder()
            .startVertex(dynamoDbEdge.getStartVertex())
            .endVertex(dynamoDbEdge.getEndVertex())
            .build())
          .collect(Collectors.toSet()))
        .map(edges -> ImmutableGraph.builder()
          .edges(edges)
          .build())
        .collect(Collectors.toSet());
  }

  @Override
  public void put(String name, Graph graph) {
    Edge edge = Iterables.getOnlyElement(graph.edges());
    DynamoDbEdge dynamoDbEdge = new DynamoDbEdge();
    dynamoDbEdge.setGraphName(name);
    dynamoDbEdge.setStartVertex(edge.startVertex());
    dynamoDbEdge.setEndVertex(edge.endVertex());
    mapper.save(dynamoDbEdge);
  }

  @Override
  public void delete(String name) {
    DynamoDbEdge dynamoDbEdge = mapper.load(DynamoDbEdge.class, name);
    mapper.delete(dynamoDbEdge);
  }
}

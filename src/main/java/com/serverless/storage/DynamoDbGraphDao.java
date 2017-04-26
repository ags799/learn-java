package com.serverless.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.serverless.models.Edge;
import com.serverless.models.Graph;
import com.serverless.models.ImmutableEdge;
import com.serverless.models.ImmutableGraph;
import java.util.Set;

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
    // TODO: actually scan the table
    Edge edge = ImmutableEdge.builder()
        .startVertex(0)
        .endVertex(1)
        .build();
    Graph graph = ImmutableGraph.builder().addEdges(edge).build();
    return ImmutableSet.of(graph);
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
}

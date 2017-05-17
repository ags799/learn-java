package com.sharpandrew.learnjava.graph.storage.dynamodb;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.graph.model.Edge;
import com.sharpandrew.learnjava.graph.model.Graph;
import com.sharpandrew.learnjava.graph.model.ImmutableEdge;
import com.sharpandrew.learnjava.graph.model.ImmutableGraph;
import com.sharpandrew.learnjava.graph.storage.Dao;
import com.sharpandrew.learnjava.graph.storage.GraphTable;
import com.sharpandrew.learnjava.serverless.Environment;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class DynamoDbGraphTable implements GraphTable {
  private static DynamoDbGraphTable instance = null;

  private final Dao<DynamoDbEdge> dao;

  @VisibleForTesting
  DynamoDbGraphTable(Dao<DynamoDbEdge> dao) {
    this.dao = dao;
  }

  public static DynamoDbGraphTable create() {
    if (instance == null) {
      Dao<DynamoDbEdge> dao =
          DynamoDbDao.create(DynamoDbEdge.class, Environment.getEdgeTableName());
      instance = new DynamoDbGraphTable(dao);
    }
    return instance;
  }

  @Override
  public Graph get(String graphId) {
    List<DynamoDbEdge> dynamoDbEdges = dao.getMatchingRows("graphId", graphId);
    Set<Edge> edges = edgesFromDynamoDbEdges(dynamoDbEdges);
    return ImmutableGraph.builder()
        .id(graphId)
        .edges(edges)
        .build();
  }

  @Override
  public Set<Graph> getAll() {
    return dao.getAllRows()
        .stream()
        .collect(Collectors.groupingBy(DynamoDbEdge::getGraphId))
        .entrySet()
        .stream()
        .map(entry -> ImmutableGraph.builder()
          .id(entry.getKey())
          .edges(edgesFromDynamoDbEdges(entry.getValue()))
          .build())
        .collect(Collectors.toSet());
  }

  @Override
  public void post(Graph graph) {
    Set<DynamoDbEdge> edges = graph.edges()
        .stream()
        .map(edge -> {
          DynamoDbEdge dynamoDbEdge = new DynamoDbEdge();
          dynamoDbEdge.setGraphId(graph.id());
          dynamoDbEdge.setStartVertex(edge.startVertex());
          dynamoDbEdge.setEndVertex(edge.endVertex());
          return dynamoDbEdge;
        })
        .collect(Collectors.toSet());
    dao.saveAll(edges);
  }

  @Override
  public void delete(String graphId) {
    List<DynamoDbEdge> edgesToDelete = dao.getMatchingRows("graphId", graphId);
    dao.deleteAll(edgesToDelete);
  }

  private Set<Edge> edgesFromDynamoDbEdges(List<DynamoDbEdge> dynamoDbEdges) {
    return dynamoDbEdges.stream()
        .map(dynamoDbEdge -> ImmutableEdge.builder()
          .startVertex(dynamoDbEdge.getStartVertex())
          .endVertex(dynamoDbEdge.getEndVertex())
          .build())
        .collect(Collectors.toSet());
  }

}

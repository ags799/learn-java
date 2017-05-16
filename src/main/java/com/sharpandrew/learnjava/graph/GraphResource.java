package com.sharpandrew.learnjava.graph;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.graph.model.Edge;
import com.sharpandrew.learnjava.graph.model.Graph;
import com.sharpandrew.learnjava.graph.model.ImmutableEdge;
import com.sharpandrew.learnjava.graph.model.ImmutableGraph;
import com.sharpandrew.learnjava.graph.storage.DefaultEdgeTable;
import com.sharpandrew.learnjava.graph.storage.EdgeTable;
import com.sharpandrew.learnjava.graph.storage.dynamodb.DynamoDbEdge;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.NotFoundException;

public final class GraphResource implements GraphService {
  private final EdgeTable edgeTable;

  @VisibleForTesting
  GraphResource(EdgeTable edgeTable) {
    this.edgeTable = edgeTable;
  }

  public static GraphResource create() {
    return new GraphResource(DefaultEdgeTable.getInstance());
  }

  public Graph get(String id) {
    Set<DynamoDbEdge> dynamoDbEdges = edgeTable.get(id);
    if (dynamoDbEdges.isEmpty()) {
      throw new NotFoundException();
    } else {
      Set<Edge> edges = dynamoDbEdges.stream()
          .map(dynamoDbEdge -> ImmutableEdge.builder()
              .startVertex(dynamoDbEdge.getStartVertex())
              .endVertex(dynamoDbEdge.getEndVertex())
              .build())
          .collect(Collectors.toSet());
      return ImmutableGraph.builder().edges(edges).build();
    }
  }

  public Set<Graph> getAll() {
    Set<DynamoDbEdge> dynamoDbEdges = edgeTable.getAll();
    return dynamoDbEdges.stream()
        .collect(Collectors.groupingBy(DynamoDbEdge::getGraphId, Collectors.toSet()))
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

  public void put(String id, Graph graph) {
    Set<DynamoDbEdge> dynamoDbEdges = graph.edges().stream()
        .map(edge -> {
          DynamoDbEdge dynamoDbEdge = new DynamoDbEdge();
          dynamoDbEdge.setGraphId(id);
          dynamoDbEdge.setStartVertex(edge.startVertex());
          dynamoDbEdge.setEndVertex(edge.endVertex());
          return dynamoDbEdge;
        })
        .collect(Collectors.toSet());
    edgeTable.post(dynamoDbEdges);
  }

  public void delete(String id) {
    edgeTable.delete(id);
  }
}

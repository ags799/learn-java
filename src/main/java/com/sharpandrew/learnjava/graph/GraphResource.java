package com.sharpandrew.learnjava.graph;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.graph.model.Graph;
import com.sharpandrew.learnjava.graph.model.GraphDescription;
import com.sharpandrew.learnjava.graph.model.ImmutableGraph;
import com.sharpandrew.learnjava.graph.storage.EdgeTable;
import com.sharpandrew.learnjava.graph.storage.dynamodb.DynamoDbEdgeTable;
import java.util.Set;

public final class GraphResource implements GraphService {
  private final EdgeTable edgeTable;

  @VisibleForTesting
  GraphResource(EdgeTable edgeTable) {
    this.edgeTable = edgeTable;
  }

  public static GraphResource create() {
    return new GraphResource(DynamoDbEdgeTable.getInstance());
  }

  @Override
  public Graph get(String id) {
    return edgeTable.get(id);
  }

  @Override
  public Set<Graph> getAll() {
    return edgeTable.getAll();
  }

  @Override
  public void put(String id, GraphDescription graphDescription) {
    Graph graph = ImmutableGraph.builder()
        .id(id)
        .edges(graphDescription.edges())
        .build();
    edgeTable.post(graph);
  }

  @Override
  public void delete(String id) {
    edgeTable.delete(id);
  }
}

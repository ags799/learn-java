package com.sharpandrew.learnjava.graph;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.graph.model.Graph;
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

  public Graph get(String id) {
    return edgeTable.get(id);
  }

  public Set<Graph> getAll() {
    return edgeTable.getAll();
  }

  public void put(String id, Graph graph) {
    Graph graphWithId = ImmutableGraph.builder()
        .from(graph)
        .id(id)
        .build();
    edgeTable.post(graphWithId);
  }

  public void delete(String id) {
    edgeTable.delete(id);
  }
}

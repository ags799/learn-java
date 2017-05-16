package com.sharpandrew.learnjava.graph;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.graph.model.Graph;
import com.sharpandrew.learnjava.graph.model.GraphDescription;
import com.sharpandrew.learnjava.graph.model.ImmutableGraph;
import com.sharpandrew.learnjava.graph.storage.GraphTable;
import com.sharpandrew.learnjava.graph.storage.dynamodb.DynamoDbGraphTable;
import java.util.Set;

public final class GraphResource implements GraphService {
  private final GraphTable graphTable;

  @VisibleForTesting
  GraphResource(GraphTable graphTable) {
    this.graphTable = graphTable;
  }

  public static GraphResource create() {
    return new GraphResource(DynamoDbGraphTable.getInstance());
  }

  @Override
  public Graph get(String id) {
    return graphTable.get(id);
  }

  @Override
  public Set<Graph> getAll() {
    return graphTable.getAll();
  }

  @Override
  public void put(String id, GraphDescription graphDescription) {
    Graph graph = ImmutableGraph.builder()
        .id(id)
        .edges(graphDescription.edges())
        .build();
    graphTable.post(graph);
  }

  @Override
  public void delete(String id) {
    graphTable.delete(id);
  }
}

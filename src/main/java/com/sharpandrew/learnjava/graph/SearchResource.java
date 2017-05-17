package com.sharpandrew.learnjava.graph;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.graph.model.Graph;
import com.sharpandrew.learnjava.graph.storage.GraphTable;
import com.sharpandrew.learnjava.graph.storage.dynamodb.DynamoDbGraphTable;
import java.util.List;

public final class SearchResource implements SearchService {
  private final GraphTable graphTable;

  @VisibleForTesting
  SearchResource(GraphTable graphTable) {
    this.graphTable = graphTable;
  }

  public static SearchResource create() {
    return new SearchResource(DynamoDbGraphTable.create());
  }

  @Override
  public List<Integer> breadthFirstSearch(String graphId) {
    Graph graph = graphTable.get(graphId);
    throw new UnsupportedOperationException();  // TODO
  }

  @Override
  public List<Integer> depthFirstSearch(String graphId) {
    throw new UnsupportedOperationException();
  }
}

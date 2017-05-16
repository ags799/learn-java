package com.sharpandrew.learnjava.graph;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.graph.storage.GraphTable;
import java.util.List;

public final class SearchResource implements SearchService {
  @VisibleForTesting
  SearchResource(GraphTable graphTable) {
    throw new UnsupportedOperationException();
  }

  public static SearchResource create() {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Integer> breadthFirstSearch(String graphId) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Integer> depthFirstSearch(String graphId) {
    throw new UnsupportedOperationException();
  }
}

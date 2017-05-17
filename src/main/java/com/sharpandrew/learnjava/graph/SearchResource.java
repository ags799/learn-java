package com.sharpandrew.learnjava.graph;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Queues;
import com.sharpandrew.learnjava.graph.model.Graph;
import com.sharpandrew.learnjava.graph.storage.GraphTable;
import com.sharpandrew.learnjava.graph.storage.dynamodb.DynamoDbGraphTable;
import java.util.List;
import java.util.Queue;

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
  public List<Integer> breadthFirstSearch(String graphId, int rootVertex) {
    Graph graph = graphTable.get(graphId);
    checkArgument(graph.vertices().contains(rootVertex));
    ImmutableList.Builder<Integer> resultBuilder = ImmutableList.builder();
    Queue<Integer> queue = Queues.newArrayDeque();
    queue.add(rootVertex);
    while (!queue.isEmpty()) {
      int current = queue.remove();
      resultBuilder.add(current);
      queue.addAll(graph.children(current));
    }
    return resultBuilder.build();
  }

  @Override
  public List<Integer> depthFirstSearch(String graphId, int rootVertex) {
    throw new UnsupportedOperationException();
  }
}

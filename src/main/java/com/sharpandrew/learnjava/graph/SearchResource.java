package com.sharpandrew.learnjava.graph;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.sharpandrew.learnjava.graph.model.Graph;
import com.sharpandrew.learnjava.graph.model.Vertex;
import com.sharpandrew.learnjava.graph.storage.GraphTable;
import com.sharpandrew.learnjava.graph.storage.dynamodb.DynamoDbGraphTable;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

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
  public List<Vertex> breadthFirstSearch(String graphId, int rootVertexId) {
    Graph graph = graphTable.get(graphId);
    Vertex rootVertex = Vertex.create(rootVertexId);
    checkArgument(graph.vertices().contains(rootVertex));
    ImmutableList.Builder<Vertex> resultBuilder = ImmutableList.builder();
    Queue<Vertex> queue = Queues.newArrayDeque();
    queue.add(rootVertex);
    while (!queue.isEmpty()) {
      Vertex current = queue.remove();
      resultBuilder.add(current);
      queue.addAll(graph.children(current).stream().sorted().collect(Collectors.toList()));
    }
    return resultBuilder.build();
  }

  @Override
  public List<Vertex> depthFirstSearch(String graphId, int rootVertexId) {
    Graph graph = graphTable.get(graphId);
    Vertex rootVertex = Vertex.create(rootVertexId);
    checkArgument(graph.vertices().contains(rootVertex));
    ImmutableList.Builder<Vertex> resultBuilder = ImmutableList.builder();
    Deque<Vertex> stack = Queues.newArrayDeque();
    stack.push(rootVertex);
    Set<Vertex> visited = Sets.newHashSet();
    while (!stack.isEmpty()) {
      Vertex current = stack.pop();
      resultBuilder.add(current);
      if (!visited.contains(current)) {
        visited.add(current);
        pushChildrenInOrder(graph, stack, current);  // we add complexity to maintain order
      }
    }
    return resultBuilder.build();
  }

  private void pushChildrenInOrder(Graph graph, Deque<Vertex> stack, Vertex current) {
    List<Vertex> children = graph.children(current)
        .stream()
        .sorted()
        .collect(Collectors.toList());
    for (int i = children.size() - 1; i >= 0; i--) {
      stack.push(children.get(i));
    }
  }
}

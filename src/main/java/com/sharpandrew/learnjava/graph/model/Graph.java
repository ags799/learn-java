package com.sharpandrew.learnjava.graph.model;

import static com.google.common.base.Preconditions.checkState;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.immutables.value.Value;

/** Usually created from a user's input as a {@link GraphDescription}. */
@Value.Immutable
@JsonDeserialize(as = ImmutableGraph.class)
@JsonSerialize(as = ImmutableGraph.class)
@Value.Style(jdkOnly = true)
public abstract class Graph {
  public abstract String id();

  public abstract Set<Edge> edges();

  @Value.Check
  public void check() {
    checkState(!edges().isEmpty(), "There must be at least one edge.");
  }

  @Value.Lazy
  @JsonIgnore
  public Set<Integer> vertices() {
    return edges().stream()
        .flatMap(edge -> edge.vertices().stream())
        .collect(Collectors.toSet());
  }

  @Value.Lazy
  @JsonIgnore
  public Map<Integer, Set<Edge>> edgesByStartingVertex() {
    return edges().stream()
        .collect(Collectors.groupingBy(Edge::startVertex, Collectors.toSet()));
  }

  public Set<Integer> children(int current) {
    return edgesByStartingVertex().get(current)
        .stream()
        .map(Edge::endVertex)
        .collect(Collectors.toSet());
  }
}

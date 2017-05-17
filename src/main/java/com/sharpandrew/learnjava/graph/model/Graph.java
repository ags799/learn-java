package com.sharpandrew.learnjava.graph.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableSet;
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

  /** Consecutive pairs in {@code vertices} form edges of the returned {@code Graph}. */
  public static Graph create(String id, int... vertices) {
    checkArgument(
        vertices.length % 2 == 0,
        "Vertices must be provided in (startVertex, endVertex) pairs to form edges.");
    ImmutableGraph.Builder builder = ImmutableGraph.builder()
        .id(id);
    for (int i = 0; i < vertices.length - 1; i += 2) {
      builder.addEdges(Edge.create(vertices[i], vertices[i + 1]));
    }
    return builder.build();
  }

  @Value.Check
  public void check() {
    checkState(!edges().isEmpty(), "There must be at least one edge.");
  }

  @Value.Lazy
  @JsonIgnore
  public Set<Vertex> vertices() {
    return edges().stream()
        .flatMap(edge -> edge.vertices().stream())
        .collect(Collectors.toSet());
  }

  @Value.Lazy
  @JsonIgnore
  public Map<Vertex, Set<Vertex>> childrenByStartingVertex() {
    return edges().stream()
        .collect(Collectors.groupingBy(Edge::startVertex, Collectors.toSet()))
        .entrySet()
        .stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            entry -> entry.getValue()
                .stream()
                .map(Edge::endVertex)
                .collect(Collectors.toSet())));
  }

  public Set<Vertex> children(Vertex current) {
    Set<Vertex> vertices = childrenByStartingVertex().get(current);
    if (vertices == null) {
      return ImmutableSet.of();
    } else {
      return vertices;
    }
  }
}

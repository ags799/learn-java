package com.sharpandrew.learnjava.graph.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableEdge.class)
@JsonSerialize(as = ImmutableEdge.class)
public abstract class Edge {
  public abstract int startVertex();

  public abstract int endVertex();

  @Value.Lazy
  @JsonIgnore
  public Set<Integer> vertices() {
    return ImmutableSet.of(startVertex(), endVertex());
  }
}

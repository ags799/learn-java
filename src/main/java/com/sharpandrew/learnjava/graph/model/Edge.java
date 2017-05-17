package com.sharpandrew.learnjava.graph.model;

import static com.google.common.base.Preconditions.checkArgument;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableEdge.class)
@JsonSerialize(as = ImmutableEdge.class)
public abstract class Edge implements Comparable {
  public abstract int startVertex();

  public abstract int endVertex();

  @Value.Lazy
  @JsonIgnore
  public Set<Integer> vertices() {
    return ImmutableSet.of(startVertex(), endVertex());
  }

  @Override
  public int compareTo(Object o) {
    checkArgument(o.getClass() == ImmutableEdge.class);
    Edge other = (Edge) o;
    if (startVertex() == other.startVertex()) {
      return endVertex() - other.endVertex();
    } else {
      return startVertex() - other.startVertex();
    }
  }
}

package com.sharpandrew.learnjava.graph.model;

import static com.google.common.base.Preconditions.checkArgument;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableVertex.class)
@JsonSerialize(as = ImmutableVertex.class)
@Value.Style(jdkOnly = true)
public abstract class Vertex implements Comparable {
  public abstract int id();

  public static Vertex create(int id) {
    return ImmutableVertex.builder()
        .id(id)
        .build();
  }

  @Override
  public int compareTo(Object o) {
    checkArgument(o.getClass() == ImmutableVertex.class);
    Vertex otherVertex = (Vertex) o;
    return id() - otherVertex.id();
  }
}

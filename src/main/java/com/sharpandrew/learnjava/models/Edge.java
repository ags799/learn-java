package com.sharpandrew.learnjava.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableEdge.class)
@JsonSerialize(as = ImmutableEdge.class)
public abstract class Edge {
  public abstract int startVertex();

  public abstract int endVertex();
}

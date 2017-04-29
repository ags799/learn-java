package com.sharpandrew.learnjava.graph.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableEdge.class)
@JsonSerialize(as = ImmutableEdge.class)
public interface Edge {
  int startVertex();

  int endVertex();
}

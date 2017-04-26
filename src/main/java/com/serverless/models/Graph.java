package com.serverless.models;

import static com.google.common.base.Preconditions.checkState;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Set;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableGraph.class)
@JsonSerialize(as = ImmutableGraph.class)
@Value.Style(jdkOnly = true)
public abstract class Graph {
  public abstract Set<Edge> edges();

  @Value.Check
  public void check() {
    checkState(!edges().isEmpty(), "There must be at least one edge.");
  }
}

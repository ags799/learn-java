package com.serverless.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Set;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableGraph.class)
@JsonSerialize(as = ImmutableGraph.class)
@Value.Style(jdkOnly = true)
public interface Graph {
  Set<Edge> edges();
}

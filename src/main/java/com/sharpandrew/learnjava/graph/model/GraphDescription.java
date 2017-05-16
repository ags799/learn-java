package com.sharpandrew.learnjava.graph.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Set;
import org.immutables.value.Value;

/** For describing a graph. */
@Value.Immutable
@JsonDeserialize(as = ImmutableGraphDescription.class)
@JsonSerialize(as = ImmutableGraphDescription.class)
@Value.Style(jdkOnly = true)
public interface GraphDescription {
  Set<Edge> edges();
}

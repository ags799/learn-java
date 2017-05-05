package com.sharpandrew.learnjava.graph.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableGraphPath.class)
@JsonSerialize(as = ImmutableGraphPath.class)
@Value.Style(jdkOnly = true)
public interface GraphPath {
  List<Integer> verticesStartToFinish();
}

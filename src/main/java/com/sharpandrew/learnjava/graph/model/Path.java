package com.sharpandrew.learnjava.graph.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutablePath.class)
@JsonSerialize(as = ImmutablePath.class)
@Value.Style(jdkOnly = true)
public interface Path {
  List<Integer> verticesStartToFinish();
}

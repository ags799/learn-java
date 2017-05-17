package com.sharpandrew.learnjava.graph.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

public final class EdgeTest {
  private static final Edge SOME_EDGE = ImmutableEdge.builder()
      .startVertex(0)
      .endVertex(2)
      .build();
  private static final Edge SOME_EDGE_WITH_DIFFERENT_START_VERTEX = ImmutableEdge.builder()
      .startVertex(1)
      .endVertex(2)
      .build();
  private static final Edge SOME_EDGE_WITH_DIFFERENT_END_VERTEX = ImmutableEdge.builder()
      .startVertex(0)
      .endVertex(1)
      .build();

  @Test
  public void compareTo_wrongType() throws Exception {
    assertThatThrownBy(() -> SOME_EDGE.compareTo(new Object()))
        .hasSameClassAs(new IllegalArgumentException());
  }

  @Test
  public void compareTo_firstByStartVertex() throws Exception {
    assertThat(SOME_EDGE.compareTo(SOME_EDGE_WITH_DIFFERENT_START_VERTEX)).isLessThan(0);
  }

  @Test
  public void compareTo_secondByEndVertex() throws Exception {
    assertThat(SOME_EDGE.compareTo(SOME_EDGE_WITH_DIFFERENT_END_VERTEX)).isGreaterThan(0);
  }
}
package com.sharpandrew.learnjava.graph.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

public final class EdgeTest {
  private static final Edge SOME_EDGE = Edge.create(0, 2);
  private static final Edge SOME_EDGE_WITH_DIFFERENT_START_VERTEX = Edge.create(1, 2);
  private static final Edge SOME_EDGE_WITH_DIFFERENT_END_VERTEX = Edge.create(0, 1);

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
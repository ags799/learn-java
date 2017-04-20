package com.sharpandrew;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public final class MathematicsTest {
  @Test
  public void addOne() throws Exception {
    assertThat(Mathematics.addOne(1)).isEqualTo(2);
  }
}
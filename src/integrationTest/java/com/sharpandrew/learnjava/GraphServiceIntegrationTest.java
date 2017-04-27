package com.sharpandrew.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class GraphServiceIntegrationTest {
  private String url;

  /** Checkstyle wants javadoc. */
  @Before
  public void setUp() throws Exception {
    String dirtyUrl = System.getenv("LEARN_JAVA_URL");
    assert dirtyUrl.endsWith("{proxy+}");
    url = dirtyUrl.substring(0, dirtyUrl.length() - "{proxy+}".length());
    System.out.println("cleaned url: " + url);
  }

  @Test
  public void initiallyThereAreNoGraphs() throws Exception {
    assertThat(IOUtils.toString(new URL(url + "graph").openStream(), StandardCharsets.UTF_8))
        .isEqualTo("[]");
  }
}

package com.sharpandrew.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jaxrs.JAXRSContract;
import org.junit.Before;
import org.junit.Test;

public class GraphServiceIntegrationTest {
  private GraphService graphService;

  /** Checkstyle wants javadoc. */
  @Before
  public void setUp() throws Exception {
    String dirtyUrl = System.getenv("LEARN_JAVA_URL");
    assert dirtyUrl.endsWith("api/{proxy+}");
    String url = dirtyUrl.substring(0, dirtyUrl.length() - "api/{proxy+}".length());
    graphService = Feign.builder()
        .decoder(new JacksonDecoder())
      .contract(new JAXRSContract())
      .target(GraphService.class, url);
  }

  @Test
  public void initiallyThereAreNoGraphs() throws Exception {
    assertThat(graphService.getAll()).isEmpty();
  }
}

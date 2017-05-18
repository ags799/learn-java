package com.sharpandrew.learnjava;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.io.Files;
import com.sharpandrew.learnjava.graph.GraphService;
import com.sharpandrew.learnjava.graph.model.Edge;
import com.sharpandrew.learnjava.graph.model.Graph;
import com.sharpandrew.learnjava.graph.model.GraphDescription;
import com.sharpandrew.learnjava.graph.model.ImmutableGraphDescription;
import com.sharpandrew.learnjava.util.FeignClients;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public final class GraphResourceIntegrationTest {
  private GraphService graphService;

  /** Checkstyle wants javadoc. */
  @Before
  public void setUp() throws Exception {
    String url = getUrl();
    graphService = FeignClients.create(GraphService.class, url);
  }

  @Test
  public void putAndGet() throws Exception {
    GraphDescription graphDescription = ImmutableGraphDescription.builder()
        .addEdges(Edge.create(0, 1))
        .build();
    graphService.put("some-id", graphDescription);
    Graph expected = Graph.create("some-id", 0, 1);
    assertThat(graphService.get("some-id")).isEqualTo(expected);
  }

  private String getUrl() throws IOException {
    List<String> deploymentOutput = Files.readLines(
        new File(getClass().getClassLoader()
            .getResource("most-recent-deployment-output.txt")
            .getFile()),
        StandardCharsets.UTF_8);
    for (int i = 0; i < deploymentOutput.size(); i++) {
      String line = deploymentOutput.get(i);
      if (line.equals("endpoints:")) {
        return getUrl(deploymentOutput.get(i + 1));
      }
    }
    throw new RuntimeException("File lacks a URL.");
  }

  private String getUrl(String line) {
    String dirtyUrl = line.split(" ")[4];
    assert dirtyUrl.endsWith("api/{proxy+}");
    return dirtyUrl.substring(0, dirtyUrl.length() - "api/{proxy+}".length());
  }
}

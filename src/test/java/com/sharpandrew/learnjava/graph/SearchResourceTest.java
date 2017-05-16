package com.sharpandrew.learnjava.graph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.sharpandrew.learnjava.graph.model.Graph;
import com.sharpandrew.learnjava.graph.model.ImmutableEdge;
import com.sharpandrew.learnjava.graph.model.ImmutableGraph;
import com.sharpandrew.learnjava.graph.storage.GraphTable;
import org.junit.Test;

public final class SearchResourceTest {
  private final GraphTable graphTable = mock(GraphTable.class);
  private final SearchResource searchResource = new SearchResource(graphTable);

  @Test
  public void breadthFirstSearch_oneEdge() throws Exception {
    Graph graph = ImmutableGraph.builder()
        .id("some-id")
        .addEdges(ImmutableEdge.builder()
          .startVertex(0)
          .endVertex(1)
          .build())
        .build();
    when(graphTable.get("some-id")).thenReturn(graph);
    assertThat(searchResource.breadthFirstSearch("some-id"))
        .containsExactly(0, 1);
  }
}
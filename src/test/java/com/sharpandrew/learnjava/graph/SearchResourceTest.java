package com.sharpandrew.learnjava.graph;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.sharpandrew.learnjava.graph.model.Graph;
import com.sharpandrew.learnjava.graph.model.Vertex;
import com.sharpandrew.learnjava.graph.storage.GraphTable;
import org.junit.Test;

public final class SearchResourceTest {
  private final GraphTable graphTable = mock(GraphTable.class);
  private final SearchResource searchResource = new SearchResource(graphTable);

  @Test
  public void breadthFirstSearch_vertexNotInGraph() throws Exception {
    Graph graph = Graph.create("some-id", 0, 1);
    when(graphTable.get("some-id")).thenReturn(graph);
    assertThatThrownBy(() -> searchResource.breadthFirstSearch("some-id", 2))
        .isInstanceOf(IllegalArgumentException.class);
  }

  /**
   * 0
   * |
   * 1
   *
   * should return [0, 1]
   */
  @Test
  public void breadthFirstSearch_oneEdge() throws Exception {
    Graph graph = Graph.create("some-id", 0, 1);
    when(graphTable.get("some-id")).thenReturn(graph);
    assertThat(searchResource.breadthFirstSearch("some-id", 0))
        .containsExactly(Vertex.create(0), Vertex.create(1));
  }

  /**
   *   0
   *  / \
   * 1  2
   *
   * should return [0, 1, 2]
   */
  @Test
  public void breadthFirstSearch_threeNodeBinaryTree() throws Exception {
    Graph graph = Graph.create("some-id",
        0, 1,
        0, 2);
    when(graphTable.get("some-id")).thenReturn(graph);
    assertThat(searchResource.breadthFirstSearch("some-id", 0))
        .containsExactly(Vertex.create(0), Vertex.create(1), Vertex.create(2));
  }

  /**
   *     0
   *    / \
   *   1  2
   *  / \
   * 3  4
   *
   * should return [0, 1, 2, 3, 4]
   */
  @Test
  public void breadthFirstSearch_fiveNodeBinaryTree() throws Exception {
    Graph graph = Graph.create("some-id",
        0, 1,
        0, 2,
        1, 3,
        1, 4);
    when(graphTable.get("some-id")).thenReturn(graph);
    assertThat(searchResource.breadthFirstSearch("some-id", 0)).containsExactly(
        Vertex.create(0), Vertex.create(1), Vertex.create(2), Vertex.create(3), Vertex.create(4));
  }

  @Test
  public void depthFirstSearch_rootVertexNotInGraph() throws Exception {
    Graph graph = Graph.create("some-id", 0, 1);
    when(graphTable.get("some-id")).thenReturn(graph);
    assertThatThrownBy(() -> searchResource.depthFirstSearch("some-id", 2))
        .isInstanceOf(IllegalArgumentException.class);
  }

  /**
   * 0
   * |
   * 1
   *
   * should return [0, 1]
   */
  @Test
  public void depthFirstSearch_oneEdge() throws Exception {
    Graph graph = Graph.create("some-id", 0, 1);
    when(graphTable.get("some-id")).thenReturn(graph);
    assertThat(searchResource.depthFirstSearch("some-id", 0))
        .containsExactly(Vertex.create(0), Vertex.create(1));
  }

  /**
   *   0
   *  / \
   * 1  2
   *
   * should return [0, 1, 2]
   */
  @Test
  public void depthFirstSearch_threeNodeBinaryTree() throws Exception {
    Graph graph = Graph.create(
        "some-id",
        0, 1,
        0, 2);
    when(graphTable.get("some-id")).thenReturn(graph);
    assertThat(searchResource.depthFirstSearch("some-id", 0))
        .containsExactly(Vertex.create(0), Vertex.create(1), Vertex.create(2));
  }

  /**
   *     0
   *    / \
   *   1  4
   *  / \
   * 2  3
   *
   * should return [0, 1, 2, 3, 4]
   */
  @Test
  public void depthFirstSearch_fiveNodeBinaryTree() throws Exception {
    Graph graph = Graph.create(
        "some-id",
        0, 1,
        0, 4,
        1, 2,
        1, 3);
    when(graphTable.get("some-id")).thenReturn(graph);
    assertThat(searchResource.depthFirstSearch("some-id", 0)).containsExactly(
        Vertex.create(0),
        Vertex.create(1),
        Vertex.create(2),
        Vertex.create(3),
        Vertex.create(4));
  }
}
package com.sharpandrew.learnjava.graph;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.graph.model.Edge;
import com.sharpandrew.learnjava.graph.model.Graph;
import com.sharpandrew.learnjava.graph.model.ImmutableEdge;
import com.sharpandrew.learnjava.graph.model.ImmutableGraph;
import com.sharpandrew.learnjava.graph.storage.DynamoDbEdgeDao;
import com.sharpandrew.learnjava.graph.storage.EdgeDao;
import com.sharpandrew.learnjava.graph.storage.StorageEdge;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.NotFoundException;

public final class GraphResource implements GraphService {
  private final EdgeDao edgeDao;

  @VisibleForTesting
  GraphResource(EdgeDao edgeDao) {
    this.edgeDao = edgeDao;
  }

  public static GraphResource create() {
    return new GraphResource(DynamoDbEdgeDao.getInstance());
  }

  public Graph get(String id) {
    Set<StorageEdge> storageEdges = edgeDao.get(id);
    if (storageEdges.isEmpty()) {
      throw new NotFoundException();
    } else {
      Set<Edge> edges = storageEdges.stream()
          .map(storageEdge -> ImmutableEdge.builder()
              .startVertex(storageEdge.getStartVertex())
              .endVertex(storageEdge.getEndVertex())
              .build())
          .collect(Collectors.toSet());
      return ImmutableGraph.builder().edges(edges).build();
    }
  }

  public Set<Graph> getAll() {
    Set<StorageEdge> storageEdges = edgeDao.getAll();
    return storageEdges.stream()
        .collect(Collectors.groupingBy(StorageEdge::getGraphId, Collectors.toSet()))
        .values()
        .stream()
        .map(dynamoDbEdgesByGraph -> dynamoDbEdgesByGraph.stream()
            .map(storageEdge -> ImmutableEdge.builder()
                .startVertex(storageEdge.getStartVertex())
                .endVertex(storageEdge.getEndVertex())
                .build())
            .collect(Collectors.toSet()))
        .map(edges -> ImmutableGraph.builder()
            .edges(edges)
            .build())
        .collect(Collectors.toSet());
  }

  public void put(String id, Graph graph) {
    Set<StorageEdge> storageEdges = graph.edges().stream()
        .map(edge -> {
          StorageEdge storageEdge = new StorageEdge();
          storageEdge.setGraphId(id);
          storageEdge.setStartVertex(edge.startVertex());
          storageEdge.setEndVertex(edge.endVertex());
          return storageEdge;
        })
        .collect(Collectors.toSet());
    edgeDao.post(storageEdges);
  }

  public void delete(String id) {
    edgeDao.delete(id);
  }
}

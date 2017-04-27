package com.sharpandrew.learnjava;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.models.Edge;
import com.sharpandrew.learnjava.models.Graph;
import com.sharpandrew.learnjava.models.ImmutableEdge;
import com.sharpandrew.learnjava.models.ImmutableGraph;
import com.sharpandrew.learnjava.storage.DaoFactory;
import com.sharpandrew.learnjava.storage.DynamoDbDaoFactory;
import com.sharpandrew.learnjava.storage.EdgeDao;
import com.sharpandrew.learnjava.storage.StorageEdge;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.NotFoundException;

public final class GraphResource implements GraphService {
  private final EdgeDao edgeDao;

  /** Create an instance. */
  public static GraphResource create() {
    String edgesTableName = ServerlessEnvironment.getEdgesTableName();
    DaoFactory daoFactory = DynamoDbDaoFactory.getInstance(edgesTableName);
    return new GraphResource(daoFactory.getEdgeDao());
  }

  @VisibleForTesting
  GraphResource(EdgeDao edgeDao) {
    this.edgeDao = edgeDao;
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  public void delete(String id) {
    edgeDao.delete(id);
  }
}

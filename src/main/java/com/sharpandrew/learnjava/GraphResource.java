package com.sharpandrew.learnjava;

import com.sharpandrew.learnjava.models.Edge;
import com.sharpandrew.learnjava.models.Graph;
import com.sharpandrew.learnjava.models.ImmutableEdge;
import com.sharpandrew.learnjava.models.ImmutableGraph;
import com.sharpandrew.learnjava.storage.DynamoDbEdgeDao;
import com.sharpandrew.learnjava.storage.EdgeDao;
import com.sharpandrew.learnjava.storage.StorageEdge;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/graph")
public class GraphResource {
  private final EdgeDao edgeDao = DynamoDbEdgeDao.getInstance();

  /** Returns the graph for given ID. */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Graph get(@PathParam("id") String id) {
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

  /** Returns all graphs. */
  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
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

  /** Adds a graph. */
  @PUT
  @Path("/{id}")
  public void put(@PathParam("id") String id, Graph graph) {
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

  /** Deletes the graph for given ID. */
  @DELETE
  @Path("/{id}")
  public void delete(@PathParam("id") String id) {
    edgeDao.delete(id);
  }
}

package com.serverless;

import com.serverless.models.Edge;
import com.serverless.models.Graph;
import com.serverless.models.ImmutableEdge;
import com.serverless.models.ImmutableGraph;
import com.serverless.storage.DynamoDbEdgeDao;
import com.serverless.storage.EdgeDao;
import com.serverless.storage.StorageEdge;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/graph")
public class GraphResource {
  private static final Logger log = LoggerFactory.getLogger(GraphResource.class);

  private final EdgeDao edgeDao = DynamoDbEdgeDao.getInstance();

  /** Returns the graph for given ID. */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Graph get(@PathParam("id") String id) {
    log.error("get-by-id");
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
    log.error("get-all");
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
    log.error("put");
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
    log.error("delete");
    edgeDao.delete(id);
  }
}

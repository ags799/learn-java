package com.serverless;

import com.google.common.collect.ImmutableSet;
import com.serverless.models.Graph;
import com.serverless.models.ImmutableEdge;
import com.serverless.models.ImmutableGraph;
import com.serverless.storage.DynamoDbGraphDao;
import com.serverless.storage.GraphDao;
import java.util.Set;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/graph")
public class GraphResource {
  GraphDao graphDao = DynamoDbGraphDao.getInstance();

  @GET
  @Path("/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public Graph get(@QueryParam("name") String name) {
    return graphDao.get(name);
  }

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Set<Graph> getAll() {
    return ImmutableSet.of(ImmutableGraph.builder().addEdges(
        ImmutableEdge.builder().startVertex(0).endVertex(1).build()).build());
//    return graphDao.getAll();
  }

  @PUT
  @Path("/{name}")
  public void put(@PathParam("name") String name, Graph graph) {
    graphDao.put(name, graph);
  }
}

package com.serverless;

import com.serverless.models.Graph;
import com.serverless.storage.DynamoDbEdgeDao;
import com.serverless.storage.EdgeDao;
import java.util.Set;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/graph")
public class GraphResource {
  EdgeDao edgeDao = DynamoDbEdgeDao.getInstance();

  @GET
  @Path("/{name}")
  @Produces(MediaType.APPLICATION_JSON)
  public Graph get(@PathParam("name") String name) {
    return edgeDao.get(name);
  }

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Set<Graph> getAll() {
    return edgeDao.getAll();
  }

  @PUT
  @Path("/{name}")
  public void put(@PathParam("name") String name, Graph graph) {
    edgeDao.put(name, graph);
  }

  @DELETE
  @Path("/{name}")
  public void delete(@PathParam("name") String name) {
    edgeDao.delete(name);
  }
}

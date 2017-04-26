package com.serverless;

import com.serverless.models.Graph;
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
  public Graph get(@PathParam("name") String name) {
    return graphDao.get(name);
  }

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Set<Graph> getAll() {
    return graphDao.getAll();
  }

  @PUT
  @Path("/{name}")
  public void put(@PathParam("name") String name, Graph graph) {
    graphDao.put(name, graph);
  }

  @DELETE
  @Path("/{name}")
  public void delete(@PathParam("name") String name) {
    graphDao.delete(name);
  }
}

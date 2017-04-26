package com.serverless;

import com.serverless.models.Graph;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class Resource {
  @PUT
  @Path("/graph")
  public void put(String name, Graph graph) {
  }

  @GET
  @Path("/ping")
  @Produces(MediaType.TEXT_PLAIN)
  public String ping() {
    return "pong";
  }
}

package com.serverless;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class Resource {
  @GET
  @Path("/hello")
  @Produces(MediaType.APPLICATION_JSON)
  public Response hello() {
    return ImmutableResponse.builder().message("some message").build();
  }
}

package com.serverless;

import javax.ws.rs.Path;

@Path("/hello")
public class HelloResource {
  public Response hello() {
    return ImmutableResponse.builder().message("some message").build();
  }
}

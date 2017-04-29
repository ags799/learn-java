package com.sharpandrew.learnjava;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/api/user")
public interface UserService {
  @PUT
  void put(@PathParam("email") String email, @PathParam("password") String password);
}

package com.sharpandrew.learnjava.user;

import com.sharpandrew.learnjava.user.model.AccessTokenAndExpiration;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/user")
public interface UserService {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  AccessTokenAndExpiration login(
      @PathParam("email") String email, @PathParam("password") String password);

  @PUT
  void put(@PathParam("email") String email, @PathParam("password") String password);
}

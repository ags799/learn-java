package com.sharpandrew.learnjava.graph;

import com.sharpandrew.learnjava.graph.model.GraphPath;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/path")
public interface PathService {
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  GraphPath get(@PathParam("id") String id);

  /** Returns the path's ID. */
  @PUT
  @Path("/")
  String put(GraphPath path);
}

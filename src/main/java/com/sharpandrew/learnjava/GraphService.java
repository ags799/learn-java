package com.sharpandrew.learnjava;

import com.sharpandrew.learnjava.models.Graph;
import java.util.Set;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/graph")
public interface GraphService {
  /** Returns the graph for given ID. */
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  Graph get(@PathParam("id") String id);

  /** Returns all graphs. */
  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  Set<Graph> getAll();

  /** Adds a graph. */
  @PUT
  @Path("/{id}")
  void put(@PathParam("id") String id, Graph graph);

  /** Deletes the graph for given ID. */
  @DELETE
  @Path("/{id}")
  void delete(@PathParam("id") String id);
}

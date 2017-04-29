package com.sharpandrew.learnjava.graph;

import com.sharpandrew.learnjava.graph.model.Graph;
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
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  Graph get(@PathParam("id") String id);

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  Set<Graph> getAll();

  @PUT
  @Path("/{id}")
  void put(@PathParam("id") String id, Graph graph);

  @DELETE
  @Path("/{id}")
  void delete(@PathParam("id") String id);
}

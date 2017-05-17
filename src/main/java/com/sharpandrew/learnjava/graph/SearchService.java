package com.sharpandrew.learnjava.graph;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/graph")
public interface SearchService {
  /** Throws {@link IllegalArgumentException} if {@code rootVertex} does not exist. */
  @GET
  @Path("/{graphId}/bfs/{rootVertex}")
  @Produces(MediaType.APPLICATION_JSON)
  List<Integer> breadthFirstSearch(
      @PathParam("graphId") String graphId, @PathParam("rootVertex") int rootVertex);

  @GET
  @Path("/{graphId}/dfs/{rootVertex}")
  @Produces(MediaType.APPLICATION_JSON)
  List<Integer> depthFirstSearch(
      @PathParam("graphId") String graphId, @PathParam("rootVertex") int rootVertex);
}

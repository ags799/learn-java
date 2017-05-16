package com.sharpandrew.learnjava.graph;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/graph")
public interface SearchService {
  @GET
  @Path("/{graphId}/bfs")
  @Produces(MediaType.APPLICATION_JSON)
  List<Integer> breadthFirstSearch(@PathParam("graphId") String graphId);

  @GET
  @Path("/{graphId}/dfs")
  @Produces(MediaType.APPLICATION_JSON)
  List<Integer> depthFirstSearch(@PathParam("graphId") String graphId);
}

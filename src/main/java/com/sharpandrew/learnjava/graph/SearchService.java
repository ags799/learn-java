package com.sharpandrew.learnjava.graph;

import com.sharpandrew.learnjava.graph.model.GraphPath;
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
  GraphPath breadthFirstSearch(@PathParam("graphId") String graphId);

  @GET
  @Path("/{graphId}/dfs")
  @Produces(MediaType.APPLICATION_JSON)
  GraphPath depthFirstSearch(@PathParam("graphId") String graphId);
}

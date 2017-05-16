package com.sharpandrew.learnjava.graph.storage;

import com.sharpandrew.learnjava.graph.model.Graph;
import java.util.Set;

public interface EdgeTable {
  Graph get(String graphId);

  Set<Graph> getAll();

  void post(Graph graph);

  void delete(String graphId);
}

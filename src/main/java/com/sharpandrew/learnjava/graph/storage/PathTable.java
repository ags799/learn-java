package com.sharpandrew.learnjava.graph.storage;

import com.sharpandrew.learnjava.graph.model.GraphPath;

public interface PathTable {
  GraphPath get(String pathId);

  /** Returns the path's ID. */
  String post(GraphPath graphPath);
}

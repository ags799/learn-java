package com.sharpandrew.learnjava.graph.storage;

import com.sharpandrew.learnjava.graph.model.GraphPath;

public interface PathDao {
  GraphPath get(String pathId);

  /** Returns the path's ID. */
  String post(GraphPath graphPath);
}

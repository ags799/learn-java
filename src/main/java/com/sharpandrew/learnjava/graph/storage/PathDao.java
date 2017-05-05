package com.sharpandrew.learnjava.graph.storage;

import com.sharpandrew.learnjava.graph.model.GraphPath;

public interface PathDao {
  GraphPath get(String pathId);

  void put(String pathId, GraphPath graphPath);
}

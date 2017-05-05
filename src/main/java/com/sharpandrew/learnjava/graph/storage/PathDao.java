package com.sharpandrew.learnjava.graph.storage;

import com.sharpandrew.learnjava.graph.model.Path;
import java.util.Set;

public interface PathDao {
  Path get(String pathId);

  Set<Path> getForGraph(String graphId);

  Set<Path> getAll();

  void put(String graphId, String pathId, Path path);

  void delete(String pathId);
}

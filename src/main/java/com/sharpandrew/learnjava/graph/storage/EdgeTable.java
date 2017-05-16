package com.sharpandrew.learnjava.graph.storage;

import com.sharpandrew.learnjava.graph.storage.dynamodb.StorageEdge;
import java.util.Set;

public interface EdgeTable {
  Set<StorageEdge> get(String graphId);

  Set<StorageEdge> getAll();

  void post(Set<StorageEdge> edges);

  void delete(String graphId);
}

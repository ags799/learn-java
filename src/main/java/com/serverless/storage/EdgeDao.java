package com.serverless.storage;

import java.util.Set;

public interface EdgeDao {
  Set<StorageEdge> get(String graphId);

  Set<StorageEdge> getAll();

  void post(Set<StorageEdge> edges);

  void delete(String graphId);
}

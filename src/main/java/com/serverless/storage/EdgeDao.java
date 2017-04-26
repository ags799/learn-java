package com.serverless.storage;

import java.util.Set;

public interface EdgeDao {
  Set<StorageEdge> get(String name);

  Set<StorageEdge> getAll();

  void post(Set<StorageEdge> edges);

  void delete(String name);
}

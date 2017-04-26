package com.serverless.storage;

import com.serverless.models.Graph;
import java.util.Set;

public interface GraphDao {
  Graph get(String name);

  Set<Graph> getAll();

  void put(String name, Graph graph);

  void delete(String name);
}

package com.sharpandrew.learnjava.graph.storage;

import com.sharpandrew.learnjava.graph.storage.dynamodb.DynamoDbEdge;
import java.util.Set;

public interface EdgeTable {
  Set<DynamoDbEdge> get(String graphId);

  Set<DynamoDbEdge> getAll();

  void post(Set<DynamoDbEdge> edges);

  void delete(String graphId);
}

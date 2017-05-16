package com.sharpandrew.learnjava.graph.storage;

import java.util.List;
import java.util.Set;

/** for interacting with a store like Dynamo DB. */
public interface Dao<T> {
  List<T> getMatchingRows(String key, String value);

  Set<T> getAllRows();

  void saveAll(Set<T> items);

  void deleteAll(List<T> edgesToDelete);
}

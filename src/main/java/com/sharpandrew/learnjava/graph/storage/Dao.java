package com.sharpandrew.learnjava.graph.storage;

import java.util.List;
import java.util.Set;

public interface Dao<T> {
  List<T> getMatchingRows(String key, String value);

  Set<T> getAllRows();

  void saveAll(Set<T> items);

  void deleteAll(List<T> edgesToDelete);
}

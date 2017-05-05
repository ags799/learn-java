package com.sharpandrew.learnjava.graph;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.graph.model.GraphPath;
import com.sharpandrew.learnjava.graph.storage.DynamoDbPathDao;
import com.sharpandrew.learnjava.graph.storage.PathDao;

public class PathResource implements PathService {
  private final PathDao pathDao;

  @VisibleForTesting
  PathResource(PathDao pathDao) {
    this.pathDao = pathDao;
  }

  public static PathResource create() {
    return new PathResource(DynamoDbPathDao.getInstance());
  }

  @Override
  public GraphPath get(String id) {
    return pathDao.get(id);
  }

  @Override
  public String put(GraphPath path) {
    return pathDao.post(path);
  }
}

package com.sharpandrew.learnjava.graph;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.graph.model.GraphPath;
import com.sharpandrew.learnjava.graph.storage.DefaultPathTable;
import com.sharpandrew.learnjava.graph.storage.PathTable;

public final class PathResource implements PathService {
  private final PathTable pathTable;

  @VisibleForTesting
  PathResource(PathTable pathTable) {
    this.pathTable = pathTable;
  }

  public static PathResource create() {
    return new PathResource(DefaultPathTable.getInstance());
  }

  @Override
  public GraphPath get(String id) {
    return pathTable.get(id);
  }

  @Override
  public String put(GraphPath path) {
    return pathTable.post(path);
  }
}

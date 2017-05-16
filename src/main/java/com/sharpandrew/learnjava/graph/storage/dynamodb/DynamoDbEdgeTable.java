package com.sharpandrew.learnjava.graph.storage.dynamodb;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import com.sharpandrew.learnjava.graph.storage.Dao;
import com.sharpandrew.learnjava.graph.storage.EdgeTable;
import com.sharpandrew.learnjava.serverless.Environment;
import java.util.List;
import java.util.Set;

public final class DynamoDbEdgeTable implements EdgeTable {
  private static DynamoDbEdgeTable instance = null;

  private final Dao<DynamoDbEdge> dao;

  @VisibleForTesting
  DynamoDbEdgeTable(Dao<DynamoDbEdge> dao) {
    this.dao = dao;
  }

  public static DynamoDbEdgeTable getInstance() {
    if (instance == null) {
      Dao<DynamoDbEdge> dao =
          DynamoDbDao.create(DynamoDbEdge.class, Environment.getEdgeTableName());
      instance = new DynamoDbEdgeTable(dao);
    }
    return instance;
  }

  @Override
  public Set<DynamoDbEdge> get(String graphId) {
    List<DynamoDbEdge> matchingEdges = dao.getMatchingRows("graphId", graphId);
    return ImmutableSet.<DynamoDbEdge>builder().addAll(matchingEdges).build();
  }

  @Override
  public Set<DynamoDbEdge> getAll() {
    return dao.getAllRows();
  }

  @Override
  public void post(Set<DynamoDbEdge> edges) {
    dao.saveAll(edges);
  }

  @Override
  public void delete(String graphId) {
    List<DynamoDbEdge> edgesToDelete = dao.getMatchingRows("graphId", graphId);
    dao.deleteAll(edgesToDelete);
  }
}

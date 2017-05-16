package com.sharpandrew.learnjava.graph.storage;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import com.sharpandrew.learnjava.graph.storage.dynamodb.DynamoDbDao;
import com.sharpandrew.learnjava.graph.storage.dynamodb.DynamoDbEdge;
import com.sharpandrew.learnjava.serverless.Environment;
import java.util.List;
import java.util.Set;

public final class DefaultEdgeTable implements EdgeTable {
  private static DefaultEdgeTable instance = null;

  private final Dao<DynamoDbEdge> dao;

  @VisibleForTesting
  DefaultEdgeTable(Dao<DynamoDbEdge> dao) {
    this.dao = dao;
  }

  public static DefaultEdgeTable getInstance() {
    if (instance == null) {
      Dao<DynamoDbEdge> dao =
          DynamoDbDao.create(DynamoDbEdge.class, Environment.getEdgeTableName());
      instance = new DefaultEdgeTable(dao);
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

package com.sharpandrew.learnjava.graph.storage;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Iterables;
import com.sharpandrew.learnjava.graph.model.GraphPath;
import com.sharpandrew.learnjava.graph.model.ImmutableGraphPath;
import com.sharpandrew.learnjava.graph.storage.dynamodb.DynamoDbDao;
import com.sharpandrew.learnjava.graph.storage.dynamodb.DynamoDbPathEdge;
import com.sharpandrew.learnjava.serverless.Environment;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DefaultPathTable implements PathTable {
  private static DefaultPathTable instance = null;

  private final Dao<DynamoDbPathEdge> dao;

  @VisibleForTesting
  DefaultPathTable(Dao<DynamoDbPathEdge> dao) {
    this.dao = dao;
  }

  public static DefaultPathTable getInstance() {
    if (instance == null) {
      Dao<DynamoDbPathEdge> dao =
          DynamoDbDao.create(DynamoDbPathEdge.class, Environment.getPathTableName());
      instance = new DefaultPathTable(dao);
    }
    return instance;
  }

  @Override
  public GraphPath get(String pathId) {
    List<DynamoDbPathEdge> matchingEdges = dao.getMatchingRows("pathId", pathId);
    List<Integer> vertices = matchingEdges.stream()
        .map(DynamoDbPathEdge::getStartVertex)
        .collect(Collectors.toList());
    vertices.add(Iterables.getLast(matchingEdges).getEndVertex());
    return ImmutableGraphPath.builder()
        .verticesStartToFinish(vertices)
        .build();
  }

  @Override
  public String post(GraphPath graphPath) {
    String pathId = UUID.randomUUID().toString();
    Set<DynamoDbPathEdge> dynamoDbPathEdges = IntStream
        .range(0, graphPath.verticesStartToFinish().size() - 1)
        .boxed()
        .map(place -> {
          DynamoDbPathEdge dynamoDbPathEdge = new DynamoDbPathEdge();
          dynamoDbPathEdge.setPathId(pathId);
          dynamoDbPathEdge.setPlace(place);
          dynamoDbPathEdge.setStartVertex(graphPath.verticesStartToFinish().get(place));
          dynamoDbPathEdge.setEndVertex(graphPath.verticesStartToFinish().get(place + 1));
          return dynamoDbPathEdge;
        })
        .collect(Collectors.toSet());
    dao.saveAll(dynamoDbPathEdges);
    return pathId;
  }
}

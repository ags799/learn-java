package com.serverless.storage;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.google.common.collect.Iterables;
import com.serverless.models.Edge;
import com.serverless.models.Graph;
import com.serverless.models.ImmutableEdge;
import com.serverless.models.ImmutableGraph;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DynamoDbEdgeDao implements EdgeDao {
  private static final DynamoDBMapper mapper = new DynamoDBMapper(
      AmazonDynamoDBClientBuilder.defaultClient());

  private static DynamoDbEdgeDao instance = null;

  private DynamoDbEdgeDao() {}

  public static DynamoDbEdgeDao getInstance() {
    if (instance == null) {
      instance = new DynamoDbEdgeDao();
    }
    return instance;
  }

  @Override
  public Set<StorageEdge> get(String name) {
    StorageEdge storageEdge = mapper.load(StorageEdge.class, name);
    Edge edge = ImmutableEdge.builder()
        .startVertex(storageEdge.getStartVertex())
        .endVertex(storageEdge.getEndVertex())
        .build();
    return ImmutableGraph.builder().addEdges(edge).build();
  }

  @Override
  public Set<StorageEdge> getAll() {
    List<StorageEdge> storageEdges = mapper.scan(
        StorageEdge.class, new DynamoDBScanExpression());
    return storageEdges.stream()
        .collect(Collectors.groupingBy(StorageEdge::getGraphName, Collectors.toSet()))
        .values()
        .stream()
        .map(dynamoDbEdgesByGraph -> dynamoDbEdgesByGraph.stream()
          .map(storageEdge -> ImmutableEdge.builder()
            .startVertex(storageEdge.getStartVertex())
            .endVertex(storageEdge.getEndVertex())
            .build())
          .collect(Collectors.toSet()))
        .map(edges -> ImmutableGraph.builder()
          .edges(edges)
          .build())
        .collect(Collectors.toSet());
  }

  @Override
  public void post(Set<StorageEdge> edges) {
    Edge edge = Iterables.getOnlyElement(graph.edges());
    StorageEdge storageEdge = new StorageEdge();
    storageEdge.setGraphName(name);
    storageEdge.setStartVertex(edge.startVertex());
    storageEdge.setEndVertex(edge.endVertex());
    mapper.save(storageEdge);
  }

  @Override
  public void delete(String name) {
    StorageEdge storageEdge = mapper.load(StorageEdge.class, name);
    mapper.delete(storageEdge);
  }
}

package com.sharpandrew.learnjava.storage;

import com.google.common.annotations.VisibleForTesting;

public final class DynamoDbDaoFactory implements DaoFactory {
  private static DynamoDbDaoFactory instance = null;

  private final String edgesTableName;

  /** Returns an instance. */
  public static DynamoDbDaoFactory getInstance(String edgesTableName) {
    if (instance == null) {
      instance = new DynamoDbDaoFactory(edgesTableName);
    } else if (!edgesTableName.equals(instance.edgesTableName)) {
      String message = String.format(
          "Attempted to create a DynamoDbDaoFactory with edges table name '%s' but an instance "
            + "with edges table name '%s' already exists",
          edgesTableName,
          instance.edgesTableName);
      throw new IllegalArgumentException(message);
    }
    return instance;
  }

  @VisibleForTesting
  DynamoDbDaoFactory(String edgesTableName) {
    this.edgesTableName = edgesTableName;
  }

  @Override
  public EdgeDao getEdgeDao() {
    return DynamoDbEdgeDao.create(edgesTableName);
  }
}

package com.sharpandrew.learnjava.storage;

import com.google.common.annotations.VisibleForTesting;

public final class DynamoDbDaoFactory implements DaoFactory {
  private static DynamoDbDaoFactory instance = null;

  /** Returns an instance. */
  public static DynamoDbDaoFactory getInstance(String stage) {
    String tableNamePrefix = stage + '-';
    if (instance == null) {
      instance = new DynamoDbDaoFactory(stage);
    } else if (!tableNamePrefix.equals(instance.tableNamePrefix)) {
      String message = String.format(
          "Attempted to create a DynamoDbDaoFactory with table name prefix '%s' but an instance "
            + "with table name prefix '%s' already exists",
          tableNamePrefix,
          instance.tableNamePrefix);
      throw new IllegalArgumentException(message);
    }
    return instance;
  }

  private final String tableNamePrefix;

  @VisibleForTesting
  DynamoDbDaoFactory(String tableNamePrefix) {
    this.tableNamePrefix = tableNamePrefix;
  }

  @Override
  public EdgeDao getEdgeDao() {
    return DynamoDbEdgeDao.create(tableNamePrefix);
  }
}

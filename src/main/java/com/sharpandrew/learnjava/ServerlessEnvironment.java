package com.sharpandrew.learnjava;

// Values here must match serverless.yml.
public final class ServerlessEnvironment {
  public static String getEdgesTableName() {
    return System.getenv("EDGES_TABLE_NAME");
  }

  public static String getUsersTableName() {
    return System.getenv("USERS_TABLE_NAME");
  }
}

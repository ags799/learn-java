package com.sharpandrew.learnjava;

// Values here must match serverless.yml.
public final class ServerlessEnvironment {
  public static String getEdgeTableName() {
    return System.getenv("EDGES_TABLE_NAME");
  }

  public static String getUserTableName() {
    return System.getenv("USERS_TABLE_NAME");
  }
}

package com.sharpandrew.learnjava;

// Values here must match serverless.yml.
public final class ServerlessEnvironment {
  public static String getEdgesTableName() {
    return System.getenv("EDGES_TABLE_NAME");
  }
}

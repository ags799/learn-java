package com.sharpandrew.learnjava;

/** Values here must match serverless.yml. */
public final class ServerlessEnvironment {
  public static String getStage() {
    return System.getenv("STAGE");
  }
}

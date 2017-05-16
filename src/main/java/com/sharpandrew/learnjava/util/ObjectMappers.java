package com.sharpandrew.learnjava.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public final class ObjectMappers {
  private static ObjectMapper instance = null;

  private ObjectMappers() {}

  public static ObjectMapper create() {
    if (instance == null) {
      instance = new ObjectMapper().registerModule(new Jdk8Module());
    }
    return instance;
  }
}

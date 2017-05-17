package com.sharpandrew.learnjava.util;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs.JAXRSContract;

public final class FeignClients {
  private FeignClients() {}

  public static <T> T create(Class<T> apiType, String url) {
    return Feign.builder()
        .encoder(new JacksonEncoder(ObjectMappers.create()))
        .decoder(new JacksonDecoder(ObjectMappers.create()))
        .contract(new JAXRSContract())
        .target(apiType, url);
  }
}

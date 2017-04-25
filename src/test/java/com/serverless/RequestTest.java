package com.serverless;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public final class RequestTest {
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final String serialized = "{\"greeting\":\"some greeting\"}";
  private static final Request deserialized = ImmutableRequest.builder()
      .greeting("some greeting")
      .build();

  @Test
  public void deserialize() throws Exception {
    assertThat(mapper.readValue(serialized, Request.class)).isEqualTo(deserialized);
  }

  @Test
  public void serialize() throws Exception {
    assertThat(mapper.writeValueAsString(deserialized)).isEqualTo(serialized);
  }
}
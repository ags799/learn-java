package com.serverless;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

public final class ResponseTest {
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final Request deserializedRequest = ImmutableRequest.builder()
      .greeting("some greeting")
      .build();
  private static final Response deserialized = ImmutableResponse.builder()
      .message("some message")
      .request(deserializedRequest)
      .build();

  private String serialized;
  private String serializedRequest;

  @Before
  public void setUp() throws Exception {
    serializedRequest = mapper.writeValueAsString(deserializedRequest);
    serialized = String.format(
        "{\"message\":\"some message\",\"request\":%s}", serializedRequest);
  }

  @Test
  public void deserialize() throws Exception {
    assertThat(mapper.readValue(serialized, Response.class)).isEqualTo(deserialized);
  }

  @Test
  public void serialize() throws Exception {
    assertThat(mapper.writeValueAsString(deserialized)).isEqualTo(serialized);
  }
}
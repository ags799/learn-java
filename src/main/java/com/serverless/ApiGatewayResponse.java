package com.serverless;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableResponse.class)
@JsonSerialize(as = ImmutableResponse.class)
public interface ApiGatewayResponse {
  int statusCode();

  String body();

  Map<String, String> headers();

  boolean isBase64Encoded();
}

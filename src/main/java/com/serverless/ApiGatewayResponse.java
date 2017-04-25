package com.serverless;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableApiGatewayResponse.class)
@JsonSerialize(as = ImmutableApiGatewayResponse.class)
public interface ApiGatewayResponse {
  int getStatusCode();

  Map<String, String> getHeaders();

  String getBody();

  boolean isBase64Encoded();
}

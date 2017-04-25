package com.serverless;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableResponse.class)
@JsonSerialize(as = ImmutableResponse.class)
public interface ApiGatewayResponse {
	int getStatusCode();
	String getBody();
	Map<String, String> getHeaders();
	boolean isBase64Encoded();
}

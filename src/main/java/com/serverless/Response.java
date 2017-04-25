package com.serverless;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableResponse.class)
@JsonSerialize(as = ImmutableResponse.class)
public interface Response {
  String message();
}

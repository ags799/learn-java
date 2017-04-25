package com.serverless;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableRequest.class)
@JsonSerialize(as = ImmutableRequest.class)
public interface Request {
  String greeting();
}

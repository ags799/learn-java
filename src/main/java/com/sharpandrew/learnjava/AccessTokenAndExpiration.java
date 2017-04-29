package com.sharpandrew.learnjava;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableAccessTokenAndExpiration.class)
@JsonSerialize(as = ImmutableAccessTokenAndExpiration.class)
public interface AccessTokenAndExpiration {
  String accessToken();

  int expiresIn();
}

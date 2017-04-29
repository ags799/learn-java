package com.sharpandrew.learnjava.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.UUID;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(as = ImmutableUser.class)
@JsonSerialize(as = ImmutableUser.class)
public interface User {
  UUID uuid();

  String email();

  String passwordHash();

  String salt();
}

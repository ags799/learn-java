package com.sharpandrew.learnjava.user;

import java.util.Optional;

public interface AccessTokenManager {
  String createAccessToken(String email);

  Optional<String> getEmail(String token);
}

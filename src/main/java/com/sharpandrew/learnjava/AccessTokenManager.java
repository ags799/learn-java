package com.sharpandrew.learnjava;

public interface AccessTokenManager {
  AccessTokenAndExpiration createAccessTokenAndExpiration(String email);
}

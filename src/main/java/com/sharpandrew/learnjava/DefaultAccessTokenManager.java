package com.sharpandrew.learnjava;

public class DefaultAccessTokenManager implements AccessTokenManager {
  @Override
  public AccessTokenAndExpiration createAccessTokenAndExpiration(String email) {
    throw new UnsupportedOperationException();
  }
}

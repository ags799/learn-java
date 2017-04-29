package com.sharpandrew.learnjava.user;

import com.sharpandrew.learnjava.user.model.AccessTokenAndExpiration;

public class DefaultAccessTokenManager implements AccessTokenManager {
  @Override
  public AccessTokenAndExpiration createAccessTokenAndExpiration(String email) {
    throw new UnsupportedOperationException();
  }
}

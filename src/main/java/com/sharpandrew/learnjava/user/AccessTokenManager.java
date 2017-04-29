package com.sharpandrew.learnjava.user;

import com.sharpandrew.learnjava.user.model.AccessTokenAndExpiration;

public interface AccessTokenManager {
  AccessTokenAndExpiration createAccessTokenAndExpiration(String email);
}

package com.sharpandrew.learnjava.user.storage;

public interface AccessTokenDao {
  void put(String token, String email, String timestamp);
}

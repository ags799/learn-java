package com.sharpandrew.learnjava.user.storage;

public class DynamoDbAccessTokenDao implements AccessTokenDao {
  public static AccessTokenDao getInstance() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void put(String token, String email, String timestamp) {
    throw new UnsupportedOperationException();
  }
}

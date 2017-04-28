package com.sharpandrew.learnjava.storage;

import java.util.UUID;

public class DynamoDbUserDao implements UserDao {
  public static UserDao getInstance() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsUuid(UUID uuid) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsEmail(String email) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void put(UUID uuid, String email, String passwordHash, String salt) {
    throw new UnsupportedOperationException();
  }
}

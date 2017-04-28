package com.sharpandrew.learnjava.storage;

import java.util.UUID;

public interface UserDao {
  boolean containsUuid(UUID uuid);

  boolean containsEmail(String email);

  void put(UUID uuid, String email, String passwordHash, String salt);
}

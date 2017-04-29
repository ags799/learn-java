package com.sharpandrew.learnjava.storage;

public interface UserDao {
  boolean containsEmail(String email);

  void put(String email, String passwordHash, String salt);
}

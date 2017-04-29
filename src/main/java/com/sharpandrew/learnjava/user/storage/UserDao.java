package com.sharpandrew.learnjava.user.storage;

public interface UserDao {
  boolean containsEmail(String email);

  StorageUser get(String email);

  void put(String email, String passwordHash, String salt);
}
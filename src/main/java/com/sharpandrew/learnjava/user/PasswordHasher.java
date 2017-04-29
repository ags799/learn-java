package com.sharpandrew.learnjava.user;

public interface PasswordHasher {
  String createSalt();

  String getPasswordHash(String password, String salt);
}

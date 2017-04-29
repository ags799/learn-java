package com.sharpandrew.learnjava;

public interface PasswordHasher {
  String createSalt();

  String getPasswordHash(String password, String salt);
}

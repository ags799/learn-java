package com.sharpandrew.learnjava.user;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class DefaultPasswordHasher implements PasswordHasher {
  @Override
  public String createSalt() {
    return new String(new SecureRandom().generateSeed(32), StandardCharsets.UTF_8);
  }

  // http://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
  @Override
  public String getPasswordHash(String password, String salt) {
    SecretKeyFactory factory;
    try {
      factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    PBEKeySpec keySpec = new PBEKeySpec(
        password.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), 1000, 64 * 8);
    byte[] hash;
    try {
      hash = factory.generateSecret(keySpec).getEncoded();
    } catch (InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
    return new String(hash, StandardCharsets.UTF_8);
  }
}

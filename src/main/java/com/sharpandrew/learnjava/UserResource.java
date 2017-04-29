package com.sharpandrew.learnjava;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.storage.DynamoDbUserDao;
import com.sharpandrew.learnjava.storage.UserDao;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class UserResource implements UserService {
  private final UserDao userDao;

  @VisibleForTesting
  UserResource(UserDao userDao) {
    this.userDao = userDao;
  }

  public static UserResource create() {
    return new UserResource(DynamoDbUserDao.getInstance());
  }

  @Override
  public AccessTokenAndExpiration login(String email, String password) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void put(String email, String password) {
    checkArgument(!userDao.containsEmail(email), "A user with email '%s' already exists.", email);
    String salt = new String(new SecureRandom().generateSeed(32), StandardCharsets.UTF_8);
    String passwordHash = getPasswordHash(password, salt);
    userDao.put(email, passwordHash, salt);
  }

  // http://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
  private static String getPasswordHash(String password, String salt) {
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

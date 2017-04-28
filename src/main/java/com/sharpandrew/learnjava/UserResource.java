package com.sharpandrew.learnjava;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.storage.DynamoDbUserDao;
import com.sharpandrew.learnjava.storage.UserDao;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class UserResource implements UserService {
  private final UserDao userDao;

  /** Create an instance. */
  public static UserResource create() {
    return new UserResource(DynamoDbUserDao.getInstance());
  }

  @VisibleForTesting
  UserResource(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public void put(String email, String password) {
    checkArgument(!userDao.containsEmail(email), "A user with email '%s' already exists.", email);
    UUID uuid = UUID.fromString(email);
    assert !userDao.containsUuid(uuid);
    String salt = new String(new SecureRandom().generateSeed(32));
    String passwordHash = getPasswordHash(password, salt);
    userDao.put(uuid, email, passwordHash, salt);
  }

  private static String getPasswordHash(String password, String salt) {
    SecretKeyFactory factory;
    try {
      factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 1000, 64 * 8);
    byte[] hash;
    try {
      hash = factory.generateSecret(keySpec).getEncoded();
    } catch (InvalidKeySpecException e) {
      throw new RuntimeException(e);
    }
    return new String(hash);
  }
}

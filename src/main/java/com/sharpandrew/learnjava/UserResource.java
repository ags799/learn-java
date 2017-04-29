package com.sharpandrew.learnjava;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.storage.DynamoDbUserDao;
import com.sharpandrew.learnjava.storage.StorageUser;
import com.sharpandrew.learnjava.storage.UserDao;
import java.util.Objects;

public final class UserResource implements UserService {
  private final AccessTokenManager accessTokenManager;
  private final PasswordHasher passwordHasher;
  private final UserDao userDao;

  @VisibleForTesting
  UserResource(
      AccessTokenManager accessTokenManager, PasswordHasher passwordHasher, UserDao userDao) {
    this.accessTokenManager = accessTokenManager;
    this.passwordHasher = passwordHasher;
    this.userDao = userDao;
  }

  public static UserResource create() {
    return new UserResource(
        new DefaultAccessTokenManager(),
        new DefaultPasswordHasher(),
        DynamoDbUserDao.getInstance());
  }

  @Override
  public AccessTokenAndExpiration login(String email, String password) {
    StorageUser storageUser = userDao.get(email);
    checkArgument(storageUser != null, "No user with email '%s' exists.", email);
    String passwordHash = passwordHasher.getPasswordHash(password, storageUser.getSalt());
    checkArgument(Objects.equals(passwordHash, storageUser.getPasswordHash()), "Incorrect password.");
    return accessTokenManager.createAccessTokenAndExpiration(storageUser.getEmail());
  }

  @Override
  public void put(String email, String password) {
    checkArgument(!userDao.containsEmail(email), "A user with email '%s' already exists.", email);
    String salt = passwordHasher.createSalt();
    String passwordHash = passwordHasher.getPasswordHash(password, salt);
    userDao.put(email, passwordHash, salt);
  }
}

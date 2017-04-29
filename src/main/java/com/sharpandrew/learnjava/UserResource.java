package com.sharpandrew.learnjava;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.storage.DynamoDbUserDao;
import com.sharpandrew.learnjava.storage.UserDao;

public final class UserResource implements UserService {
  private final PasswordHasher passwordHasher;
  private final UserDao userDao;

  @VisibleForTesting
  UserResource(PasswordHasher passwordHasher, UserDao userDao) {
    this.passwordHasher = passwordHasher;
    this.userDao = userDao;
  }

  public static UserResource create() {
    return new UserResource(new DefaultPasswordHasher(), DynamoDbUserDao.getInstance());
  }

  @Override
  public AccessTokenAndExpiration login(String email, String password) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void put(String email, String password) {
    checkArgument(!userDao.containsEmail(email), "A user with email '%s' already exists.", email);
    String salt = passwordHasher.createSalt();
    String passwordHash = passwordHasher.getPasswordHash(password, salt);
    userDao.put(email, passwordHash, salt);
  }
}

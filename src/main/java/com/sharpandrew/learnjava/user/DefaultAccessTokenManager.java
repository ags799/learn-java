package com.sharpandrew.learnjava.user;

import com.google.common.annotations.VisibleForTesting;
import com.sharpandrew.learnjava.user.storage.AccessTokenDao;
import com.sharpandrew.learnjava.user.storage.DynamoDbAccessTokenDao;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class DefaultAccessTokenManager implements AccessTokenManager {
  private final AccessTokenDao accessTokenDao;

  @VisibleForTesting
  DefaultAccessTokenManager(AccessTokenDao accessTokenDao) {
    this.accessTokenDao = accessTokenDao;
  }

  public static DefaultAccessTokenManager create() {
    return new DefaultAccessTokenManager(DynamoDbAccessTokenDao.getInstance());
  }

  @Override
  public String createAccessToken(String email) {
    String token = new String(new SecureRandom().generateSeed(32), StandardCharsets.UTF_8);
    String timestamp = getTimestampForNow();
    accessTokenDao.put(token, email, timestamp);
    return token;
  }

  @Override
  public Optional<String> getEmail(String token) {
    // TODO: ensure you remove expired tokens with the same email
    throw new UnsupportedOperationException();
  }

  private String getTimestampForNow() {
    ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("UTC"));
    return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
  }
}

package com.sharpandrew.learnjava.user.storage;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

// The names used in this class's annotations must match serverless.yml.
public class StorageUser {
  private String email;
  private String passwordHash;
  private String salt;

  @DynamoDBHashKey(attributeName = "email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @DynamoDBAttribute(attributeName = "passwordHash")
  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  @DynamoDBAttribute(attributeName = "salt")
  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }
}

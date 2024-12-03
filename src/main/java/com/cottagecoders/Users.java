package com.cottagecoders;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class Users {
  @DatabaseField(canBeNull = false, id = true)
  private long userId;
  @DatabaseField(canBeNull = false)
  private String name;
  @DatabaseField(canBeNull = false)
  private String email;
  @DatabaseField(canBeNull = false)
  private long organizationId;
  @DatabaseField(canBeNull = false)
  private boolean active;

  Users() {

  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(long organizationId) {
    this.organizationId = organizationId;
  }

  public boolean getActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
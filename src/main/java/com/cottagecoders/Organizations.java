package com.cottagecoders;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

@DatabaseTable(tableName = "organizations")
public class Organizations {

  @DatabaseField(canBeNull = false, id = true)
  private Long organizationId;
  @DatabaseField(canBeNull = true)
  private Long externalId;
  @DatabaseField(canBeNull = false)
  private String name;
  @DatabaseField(canBeNull = false)
  private String url;
  @DatabaseField(canBeNull = true)
  private Long groupId;
  @DatabaseField(canBeNull = false)
  private Date createdAt;
  @DatabaseField(canBeNull = false)
  private Date updatedAt;

  Organizations() {
  }

  public Long getExternalId() {
    return externalId;
  }

  public void setExternalId(Long externalId) {
    this.externalId = externalId;
  }

  public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long OrganizationId) {
    this.organizationId = OrganizationId;
  }


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Long getGroupId() {
    return groupId;
  }

  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}


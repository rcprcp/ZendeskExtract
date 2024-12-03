package com.cottagecoders;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "tickets")
public class Tickets {
  @DatabaseField(canBeNull = false, id = true)
  private Long ticketId;
  @DatabaseField(canBeNull = false)
  private Long organizationId;
  @DatabaseField(canBeNull = false)
  private Long submitterId;

  @DatabaseField(canBeNull = true)
  private Long assigneeId;

  @DatabaseField(canBeNull = true)
  private String priority;

  @DatabaseField(canBeNull = false)
  private String currentStatus;

  @DatabaseField(canBeNull = true)
  private Date solvedAt;

  @DatabaseField(canBeNull = false)
  private String subject;

  Tickets() {

  }
  public Long getTicketId() {
    return ticketId;
  }

  public void setTicketId(Long ticketId) {
    this.ticketId = ticketId;
  }
  public Long getSubmitterId() {
    return submitterId;
  }

  public void setSubmitterId(Long submitterId) {
    this.submitterId = submitterId;
  }

  public Long getAssigneeId() {
    return assigneeId;
  }

  public void setAssigneeId(Long assigneeId) {
    this.assigneeId = assigneeId;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public String getCurrentStatus() {
    return currentStatus;
  }

  public void setCurrentStatus(String current_status) {
    this.currentStatus = current_status;
  }

  public Date getSolvedAt() {
    return solvedAt;
  }

  public void setSolvedAt(Date solvedAt) {
    this.solvedAt = solvedAt;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public Long getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Long organizationId) {
    this.organizationId = organizationId;
  }
}

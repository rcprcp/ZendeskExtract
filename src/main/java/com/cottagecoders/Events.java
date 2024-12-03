package com.cottagecoders;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "organization")

public class Events {

  @DatabaseField(canBeNull = false)
  private long ticketId;
  @DatabaseField(canBeNull = false)
  private long eventType;

  @DatabaseField(canBeNull = false)
  private Date eventDate;

  @DatabaseField(canBeNull = false)
  private long eventText;

  Events() {
    //nothing
  }

  public long getTicketId() {
    return ticketId;
  }

  public void setTicketId(long ticketId) {
    this.ticketId = ticketId;
  }

  public long getEventType() {
    return eventType;
  }

  public void setEventType(long eventType) {
    this.eventType = eventType;
  }

  public Date getEventDate() {
    return eventDate;
  }

  public void setEventDate(Date eventDate) {
    this.eventDate = eventDate;
  }

  public long getEventText() {
    return eventText;
  }

  public void setEventText(long eventText) {
    this.eventText = eventText;
  }
}

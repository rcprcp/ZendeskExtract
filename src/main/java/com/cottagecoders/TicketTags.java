package com.cottagecoders;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tickettags")

public class TicketTags {
  @DatabaseField(canBeNull = false)
  private Long ticketId;

  @DatabaseField(canBeNull = false)
  private String tag;

  TicketTags() {

  }

  public Long getTicketId() {
    return ticketId;
  }

  public void setTicketId(Long ticketId) {
    this.ticketId = ticketId;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }
}

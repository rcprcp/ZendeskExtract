package com.cottagecoders;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "organizationTags")
public class Audits {
  @DatabaseField(canBeNull = false, id = true)
  private Long ticketId;

  Audits() {
    //nothing

  }

}

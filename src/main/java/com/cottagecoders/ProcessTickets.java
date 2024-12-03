package com.cottagecoders;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.zendesk.client.v2.Zendesk;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProcessTickets {
  Zendesk zd = null;
  JdbcPooledConnectionSource conn;
  
  ProcessTickets(JdbcPooledConnectionSource conn, Zendesk zd) {
    this.zd = zd;
    this.conn = conn;
  }

  void run() throws SQLException {

    // these are for the ticket
    Dao<Tickets, String> ticketDao = DaoManager.createDao(conn, Tickets.class);
    TableUtils.dropTable(ticketDao, true);
    TableUtils.createTableIfNotExists(conn, Tickets.class);

    // set this up for the addTag routine.
    Dao<TicketTags, String> ticketTagDao = DaoManager.createDao(conn, TicketTags.class);
    TableUtils.dropTable(ticketTagDao, true);
    TableUtils.createTableIfNotExists(conn, TicketTags.class);


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date endDate = new Date();
    Date startDate = new Date();

    try {
      startDate = sdf.parse("2020-01-01");
      endDate = sdf.parse("2024-08-31");

    } catch (ParseException ex) {
      System.out.println("Exception: " + ex.getMessage());
      ex.printStackTrace();
      System.exit(1);
    }
    long diff = endDate.getTime() - startDate.getTime();

    while (startDate.getTime() < endDate.getTime() | diff > 0) {
      endDate = new Date(startDate.getTime() + ZendeskExtract.INTERVAL);
      diff -= ZendeskExtract.INTERVAL;

      String searchTerm =
              String.format("created>=%s created<%s", sdf.format(startDate), sdf.format(endDate));

      System.out.println("searchTerm: " + searchTerm);
      // set this for next time.
      startDate = endDate;

      for (org.zendesk.client.v2.model.Ticket t : zd.getTicketsFromSearch(searchTerm)) {
        Tickets tick = new Tickets();
        tick.setTicketId(t.getId());
        if (ZendeskExtract.nullCheck(t.getOrganizationId())) {
          tick.setOrganizationId(0L);
        } else {
          tick.setOrganizationId(t.getOrganizationId());
        }

        tick.setSubmitterId(t.getSubmitterId());
        tick.setSubject(t.getSubject());
        tick.setSolvedAt(null);

        if (t.getAssigneeId() == null) {
          tick.setAssigneeId(null);
        } else {
          tick.setAssigneeId(t.getAssigneeId());
        }
        if (ZendeskExtract.nullCheck(t.getStatus())) {
          tick.setCurrentStatus("none");
        } else {
          tick.setCurrentStatus(t.getStatus().toString());
        }

        if (ZendeskExtract.nullCheck(t.getPriority())) {
          tick.setPriority("none");
        } else {
          tick.setPriority(t.getPriority().name());
        }

        ticketDao.create(tick);

        addTicketTags(ticketTagDao, t.getId(), t.getTags());
      }
    }
  }

  private void addTicketTags(Dao<TicketTags, String> ticketTagDao, Long ticketId, List<String> tags)
          throws SQLException {

    for (String s : tags) {
      TicketTags ttag = new TicketTags();

      ttag.setTicketId(ticketId);
      ttag.setTag(s);
      ticketTagDao.create(ttag);
    }
  }

}

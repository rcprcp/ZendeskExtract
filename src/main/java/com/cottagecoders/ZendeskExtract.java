package com.cottagecoders;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import org.zendesk.client.v2.Zendesk;
import org.zendesk.client.v2.model.User;

/** Hello world! */
public class ZendeskExtract {
  static final long INTERVAL = 86400 * 1000 * 7;

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    ZendeskExtract zde = new ZendeskExtract();

    try (JdbcPooledConnectionSource connectionSource =
        new JdbcPooledConnectionSource(
            System.getenv("DB_URL"), System.getenv("DB_USERNAME"), System.getenv("DB_PASSWORD"))) {

      try (Zendesk zd =
          new Zendesk.Builder(System.getenv("ZENDESK_URL"))
              .setUsername(System.getenv("ZENDESK_EMAIL"))
              .setToken(System.getenv("ZENDESK_TOKEN"))
              .build()) {

        zde.run(args, zd, connectionSource);

      } catch (Exception ex) {
        System.out.println("Exception 2: " + ex.getMessage());
        ex.printStackTrace();
        System.exit(1);
      }
    } catch (Exception ex) {
      System.out.println("Exception 2: " + ex.getMessage());
      ex.printStackTrace();
      System.exit(1);
    }
    long elapsed = System.currentTimeMillis() - start;
    System.out.println("elapsed: " + elapsed + "ms");
  }

  static boolean nullCheck(Object o) {
    return o == null;
  }

  private void run(String[] args, Zendesk zd, JdbcPooledConnectionSource conn) {
    try {

      // copy orgs.
      processOrganizations(conn, zd);

      // users need to link to an organization.
      processUsers(conn, zd);

      // tickets will link orgs and users.
      // this routine copies the ticket tags as well.
      ProcessTickets pt = new ProcessTickets(conn, zd);
      pt.run();

    } catch (Exception ex) {
      System.out.println("Exception: " + ex.getMessage());
      ex.printStackTrace();
      System.exit(4);
    }
  }

  private void processOrganizations(JdbcPooledConnectionSource conn, Zendesk zd)
      throws SQLException {
    Dao<Organizations, String> organizationDao = DaoManager.createDao(conn, Organizations.class);
    TableUtils.dropTable(organizationDao, true);
    TableUtils.createTableIfNotExists(conn, Organizations.class);

    for (org.zendesk.client.v2.model.Organization o : zd.getOrganizations()) {
      Organizations org = new Organizations();
      org.setName(o.getName());
      org.setOrganizationId(o.getId());
      org.setUrl("https://cottagecoders.com");
      org.setGroupId(o.getGroupId());
      org.setCreatedAt(o.getCreatedAt());
      org.setUpdatedAt(o.getUpdatedAt());
      organizationDao.create(org);
    }
  }

  private void processUsers(JdbcPooledConnectionSource conn, Zendesk zd) {

    try {
      Dao<Users, String> userDao = DaoManager.createDao(conn, Users.class);
      TableUtils.dropTable(userDao, true);
      TableUtils.createTableIfNotExists(conn, Users.class);

      for (User u : zd.getUsers()) {
        Users user = new Users();
        user.setUserId(u.getId());
        user.setActive(u.getActive());
        user.setName(u.getName());
        if (u.getOrganizationId() == null) {
          user.setOrganizationId(-1L);
        } else {
          user.setOrganizationId(u.getOrganizationId());
        }
        user.setEmail(u.getEmail());
        userDao.create(user);
        System.out.println("user " + user.getUserId() + " " + user.getName());
      }
    } catch (Exception ex) {
      System.out.println("Exception: " + ex.getMessage());
      ex.printStackTrace();
      System.exit(9);
    }
  }

  private void processAudits(JdbcPooledConnectionSource conn, Zendesk zd) throws SQLException {}

  private void processEvents(JdbcPooledConnectionSource conn, Zendesk zd) throws SQLException {}
}

package com.jeeconf.hibernate.performancetuning.sqltracker;

import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.sqltracker.entity.Account;
import com.jeeconf.hibernate.performancetuning.sqltracker.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount.assertSelectCount;

@Sql("/sqltracker.sql")
public class SqlTrackerTest extends BaseTest {
    @Test
    public void showStatistics() {
        Client client = session.get(Client.class, 1);
    }

    @Test
    public void sqlCountAssertion() {
        Account account1 = session.get(Account.class, 1);
        Account account2 = session.get(Account.class, 2);

        assertSelectCount(2);
    }
}

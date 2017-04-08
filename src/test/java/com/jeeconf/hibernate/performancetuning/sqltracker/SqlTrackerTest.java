package com.jeeconf.hibernate.performancetuning.sqltracker;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.sqltracker.entity.Account;
import com.jeeconf.hibernate.performancetuning.sqltracker.entity.Client;
import org.junit.Test;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 5/1/16
 */
@DatabaseSetup("/sqltracker.xml")
public class SqlTrackerTest extends BaseTest {
    @Test
    public void showStatistics() {
        Client client = session.get(Client.class, 1);
    }

    @Test
    public void sqlCountAssertion() {
        Account account1 = session.get(Account.class, 1);
        Account account2 = session.get(Account.class, 2);

        AssertSqlCount.assertSelectCount(2);
    }
}

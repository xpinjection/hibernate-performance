package com.jeeconf.hibernate.performancetuning.batchfetching;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.batchfetching.entity.Account;
import com.jeeconf.hibernate.performancetuning.batchfetching.entity.Client;
import com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount;
import org.junit.Test;

import java.util.List;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 5/18/16
 */
@DatabaseSetup("/nplusone.xml")
public class BatchFetchingTest extends BaseTest {
    @SuppressWarnings("unchecked")
    @Test
    public void batchFetching() {
        List<Client> clients = session.createQuery("select c from " +
                "com.jeeconf.hibernate.performancetuning.batchfetching.entity.Client c " +
                "where c.age >= :age")
                .setParameter("age", 18)
                .list();
        clients.forEach(c -> c.getAccounts().size());

        AssertSqlCount.assertSelectCount(3);
    }

    @Test
    public void batchSizeCache() {
        Account account1 = session.get(Account.class, 1);
        Account account2 = session.get(Account.class, 4);
        account1.getClient().getName();

        AssertSqlCount.assertSelectCount(3);
    }
}

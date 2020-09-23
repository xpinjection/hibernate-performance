package com.jeeconf.hibernate.performancetuning.batchfetching;

import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.batchfetching.entity.Account;
import com.jeeconf.hibernate.performancetuning.batchfetching.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount.assertSelectCount;

@Sql("/nplusone.sql")
@SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
public class BatchFetchingTest extends BaseTest {
    @Test
    public void batchFetching() {
        List<Client> clients = session.createQuery("select c from BatchableFetchedClientEntity c where c.age >= :age")
                .setParameter("age", 18)
                .list();
        clients.forEach(c -> c.getAccounts().size());

        assertSelectCount(3);
    }

    @Test
    public void batchSizeCache() {
        Account account1 = session.get(Account.class, 1);
        Account account2 = session.get(Account.class, 4);
        account1.getClient().getName();

        assertSelectCount(3);
    }
}

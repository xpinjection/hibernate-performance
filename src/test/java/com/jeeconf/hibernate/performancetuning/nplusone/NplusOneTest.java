package com.jeeconf.hibernate.performancetuning.nplusone;

import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.nplusone.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount.assertSelectCount;

@Sql("/nplusone.sql")
@SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
public class NplusOneTest extends BaseTest {
    @Test
    public void npo() {
        List<Client> clients = session.createQuery("select c from NPlusOneClient c where c.age >= :age")
                .setParameter("age", 18)
                .list();
        clients.forEach(c -> c.getAccounts().size());

        assertSelectCount(11);
    }

    @Test
    public void joinFetch() {
        List<Client> clients = session.createQuery("select c from NPlusOneClient c join fetch c.accounts where c.age >= :age")
                .setParameter("age", 18)
                .list();
        clients.forEach(c -> c.getAccounts().size());

        assertSelectCount(1);
    }
}

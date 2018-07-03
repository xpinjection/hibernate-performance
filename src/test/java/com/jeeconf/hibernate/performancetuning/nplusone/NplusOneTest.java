package com.jeeconf.hibernate.performancetuning.nplusone;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.nplusone.entity.Client;
import com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount;
import org.junit.Test;

import java.util.List;

@DatabaseSetup("/nplusone.xml")
public class NplusOneTest extends BaseTest {
    @SuppressWarnings("unchecked")
    @Test
    public void npo() {
        List<Client> clients = session.createQuery("select c from NPlusOneClient c where c.age >= :age")
                .setParameter("age", 18)
                .list();
        clients.forEach(c -> c.getAccounts().size());

        AssertSqlCount.assertSelectCount(11);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void joinFetch() {
        List<Client> clients = session.createQuery("select c from NPlusOneClient c join fetch c.accounts where c.age >= :age")
                .setParameter("age", 18)
                .list();
        clients.forEach(c -> c.getAccounts().size());

        AssertSqlCount.assertSelectCount(1);
    }
}

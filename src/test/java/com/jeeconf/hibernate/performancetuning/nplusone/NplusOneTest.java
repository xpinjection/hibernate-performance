package com.jeeconf.hibernate.performancetuning.nplusone;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.nplusone.entity.Client;
import org.junit.Test;

import java.util.List;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 4/30/16
 */
@DatabaseSetup("/nplusone.xml")
public class NplusOneTest extends BaseTest {
    @SuppressWarnings("unchecked")
    @Test
    public void npo() {
        List<Client> clients = session.createQuery("select c from com.jeeconf.hibernate.performancetuning.nplusone.entity.Client" +
                " c where c.age >= :age")
                .setParameter("age", 18)
                .list();
        clients.forEach(c -> c.getAccounts().size());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void joinFetch() {
        List<Client> clients = session.createQuery("select c from com.jeeconf.hibernate.performancetuning.nplusone.entity.Client c" +
                " join fetch c.accounts " +
                " where c.age >= :age")
                .setParameter("age", 18)
                .list();
        clients.forEach(c -> c.getAccounts().size());
    }
}

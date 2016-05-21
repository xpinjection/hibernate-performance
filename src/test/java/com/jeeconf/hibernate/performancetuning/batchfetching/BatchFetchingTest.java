package com.jeeconf.hibernate.performancetuning.batchfetching;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.batchfetching.entity.Account;
import com.jeeconf.hibernate.performancetuning.batchfetching.entity.Client;
import org.junit.Test;

import java.util.List;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 5/18/16
 */
@DatabaseSetup("/nplusone.xml")
public class BatchFetchingTest extends BaseTest {

    @Test
    public void batchFetching() {
        //noinspection unchecked
        List<Client> clients = getSession().createQuery("select c from com.jeeconf.hibernate.performancetuning.batchfetching.entity.Client c " +
                "where c.age >= :age")
                .setParameter("age", 18)
                .list();
        clients.forEach(c -> c.getAccounts().size());
    }

    @Test
    public void batchSizeCache() {
        Account account1 = getSession().get(Account.class, 1);
        Account account2 = getSession().get(Account.class, 4);
        account1.getClient().getName();
    }

}

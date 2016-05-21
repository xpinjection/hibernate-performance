package com.jeeconf.hibernate.performancetuning.subselect;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.subselect.entity.Client;
import org.junit.Test;

import java.util.List;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 5/18/16
 */
@DatabaseSetup("/nplusone.xml")
public class SubselectTest extends BaseTest {
    @Test
    public void subSelect() {
        //noinspection unchecked
        List<Client> clients = getSession().createQuery("select c from Client c " +
                "where c.age >= :age")
                .setParameter("age", 18)
                .list();
        clients.forEach(c -> c.getAccounts().size());
    }
}

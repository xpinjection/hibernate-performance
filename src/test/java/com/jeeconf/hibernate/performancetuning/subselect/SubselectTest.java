package com.jeeconf.hibernate.performancetuning.subselect;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount;
import com.jeeconf.hibernate.performancetuning.subselect.entity.Client;
import org.junit.Test;

import java.util.List;

@DatabaseSetup("/nplusone.xml")
public class SubselectTest extends BaseTest {
    @SuppressWarnings("unchecked")
    @Test
    public void subSelect() {
        List<Client> clients = session.createQuery("select c from SubSelectableEntity c where c.age >= :age")
                .setParameter("age", 18)
                .list();
        clients.forEach(c -> c.getAccounts().size());

        AssertSqlCount.assertSelectCount(2);
    }
}

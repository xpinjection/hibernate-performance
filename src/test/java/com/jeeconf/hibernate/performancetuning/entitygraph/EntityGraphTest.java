package com.jeeconf.hibernate.performancetuning.entitygraph;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.entitygraph.entity.Client;
import com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount;
import org.hibernate.annotations.QueryHints;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 5/18/16
 */
@DatabaseSetup("/nplusone.xml")
public class EntityGraphTest extends BaseTest {
    @Test
    public void clientFetchAccounts() {
        List<Client> clients = findAdultClientsQuery()
                .setHint(QueryHints.FETCHGRAPH, em.getEntityGraph(Client.ACCOUNTS_GRAPH))
                .getResultList();
        clients.forEach(c -> c.getAccounts().size());

        AssertSqlCount.assertSelectCount(1);
    }

    private TypedQuery<Client> findAdultClientsQuery() {
        return em.createQuery("select c from com.jeeconf.hibernate.performancetuning.entitygraph.entity.Client c " +
                "where c.age >= :age", Client.class)
                .setParameter("age", 18);
    }
}

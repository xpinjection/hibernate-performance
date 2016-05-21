package com.jeeconf.hibernate.performancetuning.entitygraph;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.entitygraph.entity.Client;
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
        List<Client> clients = findAdultClientsFetchAccounts();
        clients.forEach(c -> c.getAccounts().size());
    }

    private TypedQuery<Client> findAdultClientsQuery() {
        return em.createQuery("select c from com.jeeconf.hibernate.performancetuning.entitygraph.entity.Client c " +
                "where c.age >= :age", Client.class)
                .setParameter("age", 18);
    }

    public List<Client> findAdultClients() {
        return findAdultClientsQuery().getResultList();
    }

    public List<Client> findAdultClientsFetchAccounts() {
        return findAdultClientsQuery()
                .setHint(QueryHints.FETCHGRAPH, em.getEntityGraph(Client.ACCOUNTS_GRAPH))
                .getResultList();
    }

}

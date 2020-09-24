package com.jeeconf.hibernate.performancetuning.entitygraph;

import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.entitygraph.entity.Client;
import org.hibernate.annotations.QueryHints;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.TypedQuery;
import java.util.List;

import static com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount.assertSelectCount;

@Sql("/nplusone.sql")
@SuppressWarnings("ResultOfMethodCallIgnored")
public class EntityGraphTest extends BaseTest {
    @Test
    public void clientFetchAccounts() {
        List<Client> clients = findAdultClientsQuery()
                .setHint(QueryHints.FETCHGRAPH, em.getEntityGraph(Client.ACCOUNTS_GRAPH))
                .getResultList();
        clients.forEach(c -> c.getAccounts().size());

        assertSelectCount(1);
    }

    private TypedQuery<Client> findAdultClientsQuery() {
        return em.createQuery("select c from EntityGraphClient c where c.age >= :age", Client.class)
                .setParameter("age", 18);
    }
}

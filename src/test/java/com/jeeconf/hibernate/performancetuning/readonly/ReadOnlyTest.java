package com.jeeconf.hibernate.performancetuning.readonly;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.readonly.dto.ClientSummary;
import com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount;
import org.hibernate.transform.Transformers;
import org.junit.Test;

@DatabaseSetup("/readonly.xml")
public class ReadOnlyTest extends BaseTest {
    @SuppressWarnings("unchecked")
    @Test
    public void hqlConstructor() {
        String query = "select new com.jeeconf.hibernate.performancetuning.readonly.dto.ClientSummary(c.id,c.name,sum(a.amount)) " +
                "from ReadOnlyClient c " +
                "left join c.accounts a " +
                "where c.id=:id group by a.client";
        ClientSummary result = (ClientSummary) session.createQuery(query)
                .setParameter("id", 1).uniqueResult();
        System.out.println(result);

        AssertSqlCount.assertSelectCount(1);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void aliasToBeanResultTransformer() {
        String query = "select c.id as clientId," +
                "c.name as clientName," +
                "sum(a.amount) as totalAmount " +
                "from ReadOnlyClient c " +
                "left join c.accounts a where c.id = :id " +
                "group by a.client";
        ClientSummary result = (ClientSummary) session.createQuery(query)
                .setParameter("id", 1)
                .setResultTransformer(Transformers.aliasToBean(ClientSummary.class))
                .uniqueResult();
        System.out.println(result);

        AssertSqlCount.assertSelectCount(1);
    }
}

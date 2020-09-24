package com.jeeconf.hibernate.performancetuning.subselect;

import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.subselect.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount.assertSelectCount;

@Sql("/nplusone.sql")
@SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
public class SubselectTest extends BaseTest {
    @Test
    public void subSelect() {
        List<Client> clients = session.createQuery("select c from SubSelectableEntity c where c.age >= :age")
                .setParameter("age", 18)
                .list();
        clients.forEach(c -> c.getAccounts().size());

        assertSelectCount(2);
    }
}

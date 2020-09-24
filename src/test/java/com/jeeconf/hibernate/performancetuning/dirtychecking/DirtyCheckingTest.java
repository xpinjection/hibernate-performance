package com.jeeconf.hibernate.performancetuning.dirtychecking;

import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.dirtychecking.entity.Account;
import com.jeeconf.hibernate.performancetuning.dirtychecking.entity.Client;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.StatelessSession;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount.assertSelectCount;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql("/readonly.sql")
public class DirtyCheckingTest extends BaseTest {
    @Test
    @Commit
    public void dirtyChecking() {
        Account account = session.get(Account.class, 1);
        account.setAmount(100);

        Client client = session.get(Client.class, 1);

        assertSelectCount(2);
    }

    @Test
    @Commit
    @Transactional(readOnly = true)
    public void dirtyCheckingDisableForQuery() {
        // add @Immutable to Client
        Client client = session.get(Client.class, 1);
        // or mark entity as read only
        //session.setReadOnly(Client.class, true);
        // for queries it is also possible
        /*session.createQuery("select c " +
                "from DirtyCheckableClient c")
                .setReadOnly(true).list();*/

        assertSelectCount(1);
    }

    @Test
    @Commit
    public void statelessSession() {
        StatelessSession statelessSession = getSessionFactory().openStatelessSession();
        ScrollableResults scroll = statelessSession.createQuery("select c from DirtyCheckableClient c")
                .scroll(ScrollMode.FORWARD_ONLY);
        int count = 0;
        while (scroll.next()) {
            Client client = (Client) scroll.get(0);
            count++;
            System.out.println("Old name: " + client.getName());
            client.setName("TEST");
            statelessSession.update(client); // direct database low-level operation
        }

        assertEquals(1, count);
    }
}

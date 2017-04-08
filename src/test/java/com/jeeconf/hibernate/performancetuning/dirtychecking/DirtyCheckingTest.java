package com.jeeconf.hibernate.performancetuning.dirtychecking;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.dirtychecking.entity.Account;
import com.jeeconf.hibernate.performancetuning.dirtychecking.entity.Client;
import com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.StatelessSession;
import org.junit.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Created by Igor Dmitriev on 5/18/16
 */
@DatabaseSetup("/readonly.xml")
public class DirtyCheckingTest extends BaseTest {
    @Test
    @Commit
    public void dirtyChecking() {
        Account account = session.get(Account.class, 1);
        account.setAmount(100);

        Client client = session.get(Client.class, 1);

        AssertSqlCount.assertSelectCount(2);
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
                "from com.jeeconf.hibernate.performancetuning.dirtychecking.entity.Client c")
                .setReadOnly(true).list();*/

        AssertSqlCount.assertSelectCount(1);
    }

    @Test
    @Commit
    public void statelessSession() {
        StatelessSession statelessSession = getSessionFactory().openStatelessSession();
        ScrollableResults scroll = statelessSession.createQuery("select c from " +
                "com.jeeconf.hibernate.performancetuning.dirtychecking.entity.Client c")
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

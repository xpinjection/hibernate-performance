package com.jeeconf.hibernate.performancetuning.batchprocessing;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.batchprocessing.entity.Account;
import com.jeeconf.hibernate.performancetuning.batchprocessing.entity.Client;
import com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount;
import org.hibernate.CacheMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.query.Query;
import org.junit.Test;
import org.springframework.test.annotation.Commit;

import java.util.List;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 4/30/16
 */
@DatabaseSetup("/batchprocessing.xml")
public class BatchingProcessingTest extends BaseTest {
    @Commit
    @Test
    public void batchInsert() {
        for (int i = 0; i < 20; i++) {
            Client client = new Client();
            client.setName("Robot# " + i);
            System.out.println("Generate client with account #" + i);

            Account account = new Account();
            client.getAccounts().add(account);
            account.setClient(client);

            session.persist(client);
            session.persist(account);

            if (i % 10 == 0) { // the same as JDBC batch size
                flushAndClear();
            }
        }

        AssertSqlCount.assertInsertCount(4);
    }

    @Commit
    @Test
    public void batchUpdate() {
        Query query = session.createQuery("select c from " +
                "com.jeeconf.hibernate.performancetuning.batchprocessing.entity.Client c");
        ScrollableResults scroll = query.setFetchSize(50)
                .setCacheMode(CacheMode.IGNORE)
                .scroll(ScrollMode.FORWARD_ONLY);
        int count = 0;
        while (scroll.next()) {
            Client client = (Client) scroll.get(0);
            client.setName("NEW NAME");
            System.out.println("Update client name #" + client.getId());

            if (++count % 10 == 0) { // the same as JDBC batch size
                flushAndClear();
            }
        }
        flushAndClear();

        AssertSqlCount.assertSelectCount(1);
        AssertSqlCount.assertUpdateCount(1);
    }

    @SuppressWarnings("unchecked")
    @Commit
    @Test
    public void batchCascadeDelete() {
        List<Client> clients = session.createQuery("select c from " +
                "com.jeeconf.hibernate.performancetuning.batchprocessing.entity.Client c")
                .list();
        clients.forEach(session::delete);
        flushAndClear();

        AssertSqlCount.assertSelectCount(3);
        AssertSqlCount.assertDeleteCount(4);
    }

    private void flushAndClear() {
        session.flush();
        session.clear();
    }
}

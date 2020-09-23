package com.jeeconf.hibernate.performancetuning.batchprocessing;

import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.batchprocessing.entity.Account;
import com.jeeconf.hibernate.performancetuning.batchprocessing.entity.Client;
import org.hibernate.CacheMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount.*;

@Sql("/batchprocessing.sql")
@SuppressWarnings({"rawtypes", "unchecked"})
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

        assertInsertCount(4);
    }

    @Commit
    @Test
    public void batchUpdate() {
        Query query = session.createQuery("select c from BatchableClientEntity c");
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

        assertSelectCount(1);
        assertUpdateCount(1);
    }

    @Commit
    @Test
    public void batchCascadeDelete() {
        List<Client> clients = session.createQuery("select c from BatchableClientEntity c")
                .list();
        clients.forEach(session::delete);
        flushAndClear();

        assertSelectCount(3);
        assertDeleteCount(4);
    }

    private void flushAndClear() {
        session.flush();
        session.clear();
    }
}

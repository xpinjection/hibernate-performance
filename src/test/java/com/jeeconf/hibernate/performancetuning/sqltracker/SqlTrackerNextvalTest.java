package com.jeeconf.hibernate.performancetuning.sqltracker;

import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.sqltracker.entity.Client;
import org.junit.jupiter.api.Test;

import static com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount.*;

public class SqlTrackerNextvalTest extends BaseTest {
    @Test
    public void sqlCountAssertion_nextval() {
        Client client = new Client();
        client.setAge(42);
        client.setName("Anton");

        session.persist(client);
        session.flush();

        assertInsertCount(1);

        // in case of "call next value for hibernate_sequence"
        assertCallCount(1);
        assertNextvalCount(0);

        // in case of "select nextval ('hibernate_sequence')"
//        assertCallCount(0);
//        assertNextvalCount(1);
    }
}

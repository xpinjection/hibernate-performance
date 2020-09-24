package com.jeeconf.hibernate.performancetuning.dynamicprocessing;

import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.dynamicprocessing.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;

import static com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount.assertSelectCount;
import static com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount.assertUpdateCount;

@Sql("/dynamicprocessing.sql")
public class DynamicEntityFieldsProcessing extends BaseTest {
    @Test
    @Commit
    public void dynamicUpdate() {
        // add @DynamicUpdate(true) to Client
        Client client = session.get(Client.class, 1);
        client.setAge(35);
        session.flush();

        assertSelectCount(1);
        assertUpdateCount(1);
    }
}

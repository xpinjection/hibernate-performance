package com.jeeconf.hibernate.performancetuning;

import com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;

import static com.jeeconf.hibernate.performancetuning.sqltracker.QueryCountInfoHolder.getQueryInfo;

@SpringBootTest
@Transactional
public abstract class BaseTest {
    private static final String[] DB_UNIT_SET_UP = {"",
            "****************************************************************",
            "*************** DATABASE HAS BEEN ALREADY SET UP ***************",
            "****************************************************************"
    };

    @PersistenceContext
    protected EntityManager em;

    protected Session session;

    @BeforeEach
    public void dbAllSet() {
        Arrays.stream(DB_UNIT_SET_UP).forEach(System.out::println);
        AssertSqlCount.reset();
        session = em.unwrap(Session.class);
    }

    @AfterTransaction
    public void showSqlCount() {
        System.out.print("\nSql count: " + getQueryInfo().countAll());
    }

    protected SessionFactory getSessionFactory() {
        return session.getSessionFactory();
    }
}

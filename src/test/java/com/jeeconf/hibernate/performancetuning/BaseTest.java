package com.jeeconf.hibernate.performancetuning;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.jeeconf.hibernate.performancetuning.sqltracker.QueryCountInfoHolder.getQueryInfo;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 4/29/16
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestExecutionListeners({
        TransactionalTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@IfProfileValue(name = "run-tests", value = "true")
@Transactional
public abstract class BaseTest {

    private static final String[] DB_UNIT_SET_UP = {"",
            "****************************************************************",
            "*************** DATABASE HAS BEEN ALREADY SET UP ***************",
            "****************************************************************"
    };

    @Before
    public void dbAllSet() {
        for (String line : DB_UNIT_SET_UP) {
            System.out.println(line);
        }
        System.out.println();
    }

    @AfterTransaction
    public void showSqlCount() {
        System.out.printf("\nSql count: " + getQueryInfo().countAll());
    }

    @PersistenceContext
    protected EntityManager em;

    protected Session getSession() {
        return em.unwrap(Session.class);
    }

    protected SessionFactory getSessionFactory() {
        return getSession().getSessionFactory();
    }
}

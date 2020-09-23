package com.jeeconf.hibernate.performancetuning.cache;

import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.cache.entity.City;
import org.hibernate.Cache;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static com.jeeconf.hibernate.performancetuning.sqltracker.AssertSqlCount.assertSelectCount;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql("/cache.sql")
public class EntityCacheTest extends BaseTest {
    @Test
    public void secondLevelCache() {
        City city = session.get(City.class, 1);
        Cache secondLevelCache = getSessionFactory().getCache();
        assertTrue(secondLevelCache.containsEntity(City.class, 1));
        //secondLevelCache.evictEntity(City.class, 1);
        session.clear(); // clear first level cache
        City cachedCity = session.get(City.class, 1);

        assertSelectCount(1);
    }

    @Test
    public void queryCache() {
        String query = "select c from CachableFromSecondLevelCacheCity c";
        executeCacheableQuery(query);
        session.clear();
        executeCacheableQuery(query);

        assertSelectCount(1);
    }

    @Test
    public void queryCacheInConjunctionWithSecondLevel() {
        String query = "select c from CachableFromSecondLevelCacheClient c";
        executeCacheableQuery(query);
        session.clear();
        executeCacheableQuery(query);

        assertSelectCount(3);
    }

    private void executeCacheableQuery(String query) {
        session.createQuery(query).setCacheable(true).list();
    }
}

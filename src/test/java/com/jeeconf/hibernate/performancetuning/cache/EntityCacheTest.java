package com.jeeconf.hibernate.performancetuning.cache;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.jeeconf.hibernate.performancetuning.BaseTest;
import com.jeeconf.hibernate.performancetuning.cache.entity.City;
import org.hibernate.Cache;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 4/30/16
 */
@DatabaseSetup("/cache.xml")
public class EntityCacheTest extends BaseTest {

    @Test
    public void secondLevelCache() {
        City city = getSession().get(City.class, 1);
        Cache secondLevelCache = getSessionFactory().getCache();
        assertTrue(secondLevelCache.containsEntity(City.class, 1));
        //secondLevelCache.evictEntity(City.class, 1);
        getSession().clear(); // clear first level cache
        City cachedCity = getSession().get(City.class, 1);
    }

    @Test
    public void queryCache() {
        String query = "select c from City c";
        executeCacheableQuery(query);
        getSession().clear();
        executeCacheableQuery(query);
    }

    @Test
    public void queryCacheInConjunctionWithSecondLevel() {
        String query = "select c from Client c";
        executeCacheableQuery(query);
        getSession().clear();
        executeCacheableQuery(query);
    }

    private void executeCacheableQuery(String query) {
        getSession().createQuery(query).setCacheable(true).list();
    }
}

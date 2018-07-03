package com.jeeconf.hibernate.performancetuning.dirtychecking.repository;

import com.jeeconf.hibernate.performancetuning.readonly.entity.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Client findById(Integer id) {
        return em.find(Client.class, 1);
    }
}

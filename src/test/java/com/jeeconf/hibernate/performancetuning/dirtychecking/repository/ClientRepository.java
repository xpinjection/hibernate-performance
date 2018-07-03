package com.jeeconf.hibernate.performancetuning.dirtychecking.repository;

import com.jeeconf.hibernate.performancetuning.readonly.entity.Client;

public interface ClientRepository {
    Client findById(Integer id);
}

package com.jeeconf.hibernate.performancetuning.dirtychecking.service;

import com.jeeconf.hibernate.performancetuning.readonly.entity.Client;

public interface ClientService {
    Client findClient(Integer id);
}

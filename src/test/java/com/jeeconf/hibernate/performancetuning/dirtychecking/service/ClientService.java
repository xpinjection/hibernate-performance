package com.jeeconf.hibernate.performancetuning.dirtychecking.service;

import com.jeeconf.hibernate.performancetuning.readonly.entity.Client;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 4/30/16
 */
public interface ClientService {
    Client findClient(Integer id);
}

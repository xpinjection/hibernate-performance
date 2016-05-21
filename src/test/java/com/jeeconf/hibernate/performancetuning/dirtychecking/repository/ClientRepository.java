package com.jeeconf.hibernate.performancetuning.dirtychecking.repository;

import com.jeeconf.hibernate.performancetuning.readonly.entity.Client;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 4/30/16
 */
public interface ClientRepository {
    Client findById(Integer id);
}

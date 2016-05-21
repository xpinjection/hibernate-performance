package com.jeeconf.hibernate.performancetuning.dirtychecking.service;

import com.jeeconf.hibernate.performancetuning.dirtychecking.repository.ClientRepository;
import com.jeeconf.hibernate.performancetuning.readonly.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 4/30/16
 */
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Client findClient(Integer id) {
        return clientRepository.findById(id);
    }
}

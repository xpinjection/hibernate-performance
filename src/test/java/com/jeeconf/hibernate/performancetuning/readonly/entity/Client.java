package com.jeeconf.hibernate.performancetuning.readonly.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 4/29/16
 */
@Entity
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_client")
    private Integer id;

    private String name;
    private int age;

    @OneToMany(mappedBy = "client")
    private List<Account> accounts = new ArrayList<>();
}

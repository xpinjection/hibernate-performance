package com.jeeconf.hibernate.performancetuning.cache.entity;

import lombok.Getter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Getter
@Cacheable
@Cache(region = "referenceCache", usage = CacheConcurrencyStrategy.READ_ONLY)
@Immutable
@Entity(name = "CachableFromSecondLevelCacheCity")
@Table(name = "City")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_city")
    private Integer id;

    private String name;
    private String country;
}

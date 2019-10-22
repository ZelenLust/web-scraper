package com.scraper.web.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public abstract class GeneralItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

}

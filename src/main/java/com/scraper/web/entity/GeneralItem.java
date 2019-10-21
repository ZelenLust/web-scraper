package com.scraper.web.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@MappedSuperclass
public abstract class GeneralItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private String weight;

    @Column(name = "guarantee")
    private String guarantee;

    @Column(name = "price")
    private BigInteger price;

}

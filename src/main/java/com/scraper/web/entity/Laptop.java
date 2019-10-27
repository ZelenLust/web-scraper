package com.scraper.web.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "laptop")
public class Laptop {

    @Id
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @Column(name = "name")
    private String name;

    @Column(name = "processor")
    private String processor;

    @Column(name = "screen_diagonal")
    private String screenDiagonal;

    @Column(name = "amount_of_ram")
    private String amountOfRam;

    @Column(name = "amount_of_cores")
    private Short amountOfCores;

    @Column(name = "hdd")
    private String hdd;

    @Column(name = "ssd")
    private String ssd;

    @Column(name = "url")
    private String url;

    @Column(name = "code")
    private String code;

}

package com.scraper.web.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "laptop")
public class Laptop extends GeneralItem {

    @Column(name = "processor")
    private String processor;

    @Column(name = "screen_diagonal")
    private String screenDiagonal;

    @Column(name = "amount_of_ram")
    private String amountOfRam;

    @Column(name = "amount_of_cores")
    private String amountOfCores;

    @Column(name = "hdd")
    private String hdd;

    @Column(name = "ssd")
    private String ssd;

    @Column(name = "url")
    private String url;

    @Column(name = "code")
    private String code;

    @OneToMany(fetch = FetchType.LAZY)
    private List<LaptopPrice> prices;
}

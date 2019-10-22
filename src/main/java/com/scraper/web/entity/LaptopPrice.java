package com.scraper.web.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
@Entity
@Table(name = "laptop_price")
public class LaptopPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private BigInteger price;

    @Column(name = "date")
    private LocalDate date;

    @Column(name ="currency")
    private String currency;

    @ManyToOne
    private Laptop laptop;

}

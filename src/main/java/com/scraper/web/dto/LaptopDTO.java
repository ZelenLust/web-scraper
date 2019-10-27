package com.scraper.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scraper.web.entity.Laptop;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class LaptopDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("processor")
    private String processor;

    @JsonProperty("screenDiagonal")
    private String screenDiagonal;

    @JsonProperty("amountOfRam")
    private String amountOfRam;

    @JsonProperty("amountOfCores")
    private Short amountOfCores;

    @JsonProperty("hdd")
    private String hdd;

    @JsonProperty("ssd")
    private String ssd;

    @JsonProperty("url")
    private String url;

    @JsonProperty("code")
    private String code;

    public static LaptopDTO of(@NotNull Laptop laptop) {
        LaptopDTO instance = new LaptopDTO();
        instance.setId(laptop.getId());
        instance.setName(laptop.getName());
        instance.setProcessor(laptop.getProcessor());
        instance.setScreenDiagonal(laptop.getScreenDiagonal());
        instance.setAmountOfRam(laptop.getAmountOfRam());
        instance.setAmountOfCores(laptop.getAmountOfCores());
        instance.setHdd(laptop.getHdd());
        instance.setSsd(laptop.getSsd());
        instance.setUrl(laptop.getUrl());
        instance.setCode(laptop.getCode());

        return instance;
    }

}

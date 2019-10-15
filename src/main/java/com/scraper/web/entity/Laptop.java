package com.scraper.web.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "laptop")
public class Laptop extends GeneralItem {

    @Column(name = "processor")
    private String processor;

    @Column(name = "screen_diagonal")
    private String screenDiagonal;

    @Column(name = "amount_of_ram")
    private String mountOfRam;

    @Column(name = "operating_system")
    private String operatingSystem;

    @Column(name = "short_characteristics")
    private String shortCharacteristics;

    @Column(name = "keyboard")
    private String keyboard;

    @Column(name = "color")
    private String color;

    @Column(name = "storage_volume")
    private String storageVolume;

    @Column(name = "optical_drive")
    private String opticalDrive;

    @Column(name = "slots_for_ram")
    private String numberOfSlotsForRam;

    @Column(name = "battery")
    private String battery;

    @Column(name = "screen_refresh_rate")
    private String screenRefreshRate;

    @Column(name = "type_of_ram")
    private String typeOfRam;

    @Column(name = "additional_features")
    private String additionalFeatures;

    @Column(name = "graphic_adapter")
    private String graphicAdapter;

    @Column(name = "network_adapters")
    private String networkAdapters;

    @Column(name = "connectors_and_ports")
    private String connectorsAndPorts;

    @Column(name = "battery_specifications")
    private String batterySpecifications;

    @Column(name = "dimensions")
    private String dimensions;

    @Column(name = "supplied_with")
    private String suppliedWith;

    @Column(name = "brand_registration_country")
    private String brandRegistrationCountry;

    @Column(name = "guarantee")
    private String guarantee;

    @Column(name = "additional_warranty_conditions")
    private String additionalWarrantyConditions;
}

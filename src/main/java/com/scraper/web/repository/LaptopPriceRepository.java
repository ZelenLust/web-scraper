package com.scraper.web.repository;

import com.scraper.web.entity.LaptopPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaptopPriceRepository extends JpaRepository<LaptopPrice, Integer> {

    List<LaptopPrice> findAllByLaptopCode(String code);

}

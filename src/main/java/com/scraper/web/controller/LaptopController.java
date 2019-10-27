package com.scraper.web.controller;

import com.scraper.web.dto.LaptopDTO;
import com.scraper.web.dto.PriceDTO;
import com.scraper.web.service.LaptopService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@AllArgsConstructor
@RequestMapping("/citrus/laptops")
public class LaptopController {

    private final LaptopService laptopService;

    @GetMapping("/{code}")
    public LaptopDTO getById(@PathVariable("code") String code) {
        return laptopService.getByCode(code);
    }

    @GetMapping
    public List<LaptopDTO> getAll() {
        return laptopService.getAll();
    }

    @GetMapping("/{code}/prices")
    public List<PriceDTO> getPrices(@PathVariable("code") String code) {
        return laptopService.getLaptopPricesByCode(code);
    }

}

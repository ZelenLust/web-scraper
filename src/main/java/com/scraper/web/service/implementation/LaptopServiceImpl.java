package com.scraper.web.service.implementation;

import com.scraper.web.dto.LaptopDTO;
import com.scraper.web.dto.PriceDTO;
import com.scraper.web.entity.Laptop;
import com.scraper.web.exception.NotFoundException;
import com.scraper.web.repository.LaptopPriceRepository;
import com.scraper.web.repository.LaptopRepository;
import com.scraper.web.service.LaptopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LaptopServiceImpl implements LaptopService {

    private final LaptopRepository laptopRepository;

    private final LaptopPriceRepository laptopPriceRepository;

    @Override
    public LaptopDTO getByCode(String code) {
        Laptop laptop = laptopRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Requested laptop not found"));
        return LaptopDTO.of(laptop);
    }

    @Override
    public List<LaptopDTO> getAll() {
        return laptopRepository.findAll().stream().map(LaptopDTO::of).collect(Collectors.toList());
    }

    @Override
    public List<PriceDTO> getLaptopPricesByCode(String code) {
        return laptopPriceRepository.findAllByLaptopCode(code).stream().map(lp -> PriceDTO.builder().date(lp.getDate())
                .price(lp.getPrice()).build()).collect(Collectors.toList());
    }

}

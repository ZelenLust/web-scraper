package com.scraper.web.service;

import com.scraper.web.dto.LaptopDTO;
import com.scraper.web.dto.PriceDTO;

import java.util.List;

public interface LaptopService {

    public LaptopDTO getByCode(String code);

    public List<LaptopDTO> getAll();

    public List<PriceDTO> getLaptopPricesByCode(String code);

}

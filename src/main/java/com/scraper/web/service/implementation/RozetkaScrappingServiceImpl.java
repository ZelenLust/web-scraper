package com.scraper.web.service.implementation;

import com.scraper.web.entity.Laptop;
import com.scraper.web.repository.LaptopRepository;
import com.scraper.web.service.RozetkaScrappingService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RozetkaScrappingServiceImpl implements RozetkaScrappingService {

    @Value("${rozetka.laptops.url}")
    private String laptopsUrl;

    private final LaptopRepository laptopRepository;

    @Override
    public void scrapeLaptops() {
        try {
            Document firstPage = Jsoup.connect(String.format(laptopsUrl, 1)).get();
            Elements pagination = firstPage.getElementsByClass("paginator-catalog-l-link");
            Integer lastPage = pagination.stream().map(Element::text).filter(this::isNumeric)
                    .map(Integer::valueOf).max(Integer::compareTo)
                    .orElseThrow(() -> new RuntimeException("Unable to determine last page of laptops"));

            List<Laptop> allLaptops = new ArrayList<>();

            for (int i = 1; i < lastPage; i++) {
                final Document page = Jsoup.connect(String.format(laptopsUrl, i)).get();

                // get all laptop references from page
                Elements pageLaptops = page.getElementsByClass("g-i-tile-i-title");
                pageLaptops.stream().map(e -> e.getElementsByTag("a").get(0))
                        .forEach(e -> allLaptops.add(scrapeLaptopsInfo(e.attr("href"))));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private Laptop scrapeLaptopsInfo(String laptopPageUrl) {
        try {
            Document laptopPage = Jsoup.connect(laptopPageUrl).get();
            Laptop laptop = new Laptop();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean isNumeric(String pageNumber) {
        return pageNumber.matches("-?\\d+(\\.\\d+)?");
    }

}

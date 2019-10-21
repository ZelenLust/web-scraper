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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

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
            Laptop laptop = new Laptop();

            Document laptopPage = Jsoup.connect(laptopPageUrl + "/characteristics/").get();
            Elements nameElement = laptopPage.getElementsByClass("product-tabs__heading_color_gray");
            laptop.setName( nameElement.get(0).text());

            Elements characteristicElements = laptopPage.getElementsByClass("product-characteristics-list");
            Map<String, String> characteristics = convertCharacteristicsToMap(characteristicElements);

            for (String key : characteristics.keySet()) {

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    enum Characteristic {
        SCREEN_DIAGONAL(s -> s.equals("Діагональ екрана"), (value, laptop) ->
                laptop.setScreenDiagonal(value)),
        PROCESSOR(s -> s.equals("Процесор"), (value, laptop) ->
                laptop.setProcessor(value)),
        AMOUNT_OF_RAM(s -> s.equals("Обсяг оперативної пам'яті"), (value, laptop) ->
                laptop.setAmountOfRam(value)),
        SHORT_CHARACTERISTICS(s -> s.equals("Короткі характеристики"), (value, laptop) ->
                laptop.setShortCharacteristics(value)),
        OS(s -> s.equals("Операційна система"), (value, laptop) ->
                laptop.setOperatingSystem(value)),
        COLOR(s -> s.equals("Колір"), (value, laptop) ->
                laptop.setColor(value));

        private final Predicate<String> predicate;
        private final BiConsumer<String, Laptop> processingFunction;
        Characteristic(Predicate<String> predicate, BiConsumer<String, Laptop> processingFunction) {
            this.predicate = predicate;
            this.processingFunction = processingFunction;
        }
        public Predicate<String> getPredicate() {
            return predicate;
        }
        public BiConsumer<String, Laptop> getProcessingFunction() {
            return processingFunction;
        }
    }

    private Map<String, String> convertCharacteristicsToMap(Elements characteristics) {
        Map<String, String> result = new HashMap<>();

        for (int i = 0; i < characteristics.size(); i++) {
            String name = characteristics.get(i).text();
            String value = characteristics.get(++i).text();
            result.put(name, value);
        }

        return result;
    }

    private boolean isNumeric(String pageNumber) {
        return pageNumber.matches("-?\\d+(\\.\\d+)?");
    }

}

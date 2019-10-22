package com.scraper.web.service.implementation;

import com.scraper.web.entity.Laptop;
import com.scraper.web.entity.LaptopPrice;
import com.scraper.web.repository.LaptopPriceRepository;
import com.scraper.web.repository.LaptopRepository;
import com.scraper.web.service.CitrusScrappingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

@Log4j2
@Service
@RequiredArgsConstructor
public class CitrusScrappingServiceImpl implements CitrusScrappingService {

    @Value("${citrus.laptops.url}")
    private String laptopsUrl;

    @Value("${citrus.laptops.default.url}")
    private String laptopsDefaultUrl;

    private final LaptopRepository laptopRepository;

    private final LaptopPriceRepository laptopPriceRepository;

    @Override
    public void scrapeLaptops() {
        try {
            log.debug("Started scraping laptops from: {}", laptopsUrl);
            Document firstPage = Jsoup.connect(String.format(laptopsUrl, 1)).get();
            Elements paginationContainer = firstPage.getElementsByClass("pagination-container");
            Elements pageNumbers = paginationContainer.get(0).getElementsByTag("a");
            Integer lastPage = pageNumbers.stream().map(Element::text).filter(this::isNumeric)
                    .map(Integer::valueOf).max(Integer::compareTo)
                    .orElseThrow(() -> new RuntimeException("Unable to determine last page of laptops"));

            List<Laptop> allLaptops = new ArrayList<>();

            for (int i = 1; i < lastPage; i++) {
                final Document page = Jsoup.connect(String.format(laptopsUrl, i)).get();

                // get all laptop references from page
                Elements pageLaptops = page.getElementsByClass("product-card__name");
                pageLaptops.stream().map(e -> e.getElementsByTag("a").get(0))
                        .forEach(e -> allLaptops.add(scrapeLaptopsInfo(e.attr("href"))));
            }
        } catch (IOException e) {
            log.error("An error occurred, URL: {}", laptopsUrl);
        }

    }
    private Laptop scrapeLaptopsInfo(String laptopPageIdentifier) {
        String laptopPageUrl = laptopsDefaultUrl + laptopPageIdentifier;
        try {
            Laptop laptop = new Laptop();
            Document laptopPage = Jsoup.connect(laptopPageUrl).get();

            // set name and model code
            Elements nameElement = laptopPage.getElementsByClass("product__title");
            laptop.setName(nameElement.get(0).text());
            laptop.setCode(extractCodeFromName(laptop.getName()));

            // set characteristics
            Element characteristicContainer = laptopPage.getElementsByClass("showcase__characteristics").get(0);
            Elements characteristics = characteristicContainer.getElementsByClass("item");
            setLaptopCharacteristics(laptop, characteristics);

            // create laptop price object
            Element priceElement = laptopPage.getElementsByClass("normal__prices").get(0)
                    .getElementsByClass("price").get(0);
            String price = priceElement.getElementsByTag("span").get(0).text().replace(" ", "");
            String currency = priceElement.getElementsByClass("currency").get(0).text();
            laptopPriceRepository.save(LaptopPrice.builder().date(LocalDate.now()).laptop(laptop)
                    .price(BigInteger.valueOf(Long.valueOf(price))).build());

            return laptop;
        } catch (IOException e) {
            log.error("An error occurred, URL: {}", laptopPageUrl);
        }
        throw new RuntimeException(String.format("Unable to scrape laptop from page: %s", laptopPageUrl));
    }

    private String extractCodeFromName(String name) {
        return name.substring(name.indexOf("(") + 1, name.indexOf(")"));
    }

    private void setLaptopCharacteristics(Laptop laptop, Elements characteristics) {
        for (Element characteristic : characteristics) {
            String title = characteristic.getElementsByClass("item__title").get(0).text();
            String value = characteristic.getElementsByClass("item__value").get(0).text();

            setCharacteristic(title, value, laptop);
        }
    }

    private void setCharacteristic(String title, String value, Laptop laptop) {
        for (Characteristic characteristic : Characteristic.values()) {
            if (characteristic.predicate.test(title)) {
                characteristic.processingFunction.accept(value, laptop);
                break;
            }
        }
    }

    private enum Characteristic {
        SCREEN_DIAGONAL(s -> s.equals("Розмір екрану"), (value, laptop) ->
                laptop.setScreenDiagonal(value)),
        PROCESSOR(s -> s.equals("Тип процесора"), (value, laptop) ->
                laptop.setProcessor(value)),
        AMOUNT_OF_RAM(s -> s.equals("Розмір оперативної пам'яті"), (value, laptop) ->
                laptop.setAmountOfRam(value)),
        AMOUNT_OF_CORES(s -> s.equals("Кількість ядер процесора"), (value, laptop) ->
                laptop.setAmountOfCores(value)),
        HDD(s -> s.equals("Об'єм HDD-накопичувача"), (value, laptop) ->
                laptop.setHdd(value)),
        SSD(s -> s.equals("Обсяг SSD-накопичувача"), (value, laptop) ->
                laptop.setSsd(value));

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

    private boolean isNumeric(String pageNumber) {
        return pageNumber.matches("-?\\d+(\\.\\d+)?");
    }

}

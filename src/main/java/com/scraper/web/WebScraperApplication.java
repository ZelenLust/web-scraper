package com.scraper.web;

import com.scraper.web.service.implementation.CitrusScrappingServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WebScraperApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext =
				SpringApplication.run(WebScraperApplication.class, args);

		applicationContext.getBean(CitrusScrappingServiceImpl.class).scrapeLaptops();
	}

}

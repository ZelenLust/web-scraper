package com.scraper.web.service.implementation;

import com.scraper.web.service.CitrusScrappingService;
import com.scraper.web.service.SchedulerService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@AllArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private final CitrusScrappingService citrusScrappingService;

    @Override
    @Scheduled(cron = "0 0 1 * * *")
    public void scrapeLaptops() {
        citrusScrappingService.scrapeLaptops();
    }

}

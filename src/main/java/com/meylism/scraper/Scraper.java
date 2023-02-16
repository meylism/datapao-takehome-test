package com.meylism.scraper;

import com.meylism.ds.ScrapeResult;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * Abstract class for scrapers
 */
public abstract class Scraper {
    @Getter @Setter
    private String url;

    Scraper(String url) {
        this.url = url;
    }

    /**
     * Scrape the provided url and return result
     * @return result of scraping
     */
    public abstract ScrapeResult scrape();
}

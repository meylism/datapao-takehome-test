package com.meylism.scraper;

import lombok.Getter;
import lombok.Setter;

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
    public abstract Object scrape();
}

package com.meylism.scraper;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Abstract class for scrapers
 */
public abstract class Scraper {
    private static Logger logger = LogManager.getLogger(Scraper.class);
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

    public Document fetch(String url) {
        Document doc = null;
        try {
            logger.debug("Started fetching {}", url);
            long startTime = System.currentTimeMillis();

            doc = Jsoup.connect(url)
                    .header("Accept-Language", "en")
                    .get();

            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.debug("Finished fetching: took {} milliseconds", elapsedTime);
        } catch (IOException e) {
            logger.fatal("Can't scrape the provided URL: {}", e.getMessage());
            System.exit(-1);
        }
        return doc;
    }
}

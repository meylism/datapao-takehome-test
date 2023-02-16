package com.meylism.processor;

import com.meylism.ds.ScrapeResult;

/**
 * Processor is any action/calculation that's applied to the result of web scraper
 */
public abstract class Processor {

    /**
     * Apply transformation/processing to the given result.
     * @param result result of scraping
     */
    public abstract void process(ScrapeResult result);

    public abstract String getName();
}

package com.meylism.processor;

import com.meylism.ds.ScrapeResult;

/**
 * Processor is any action/calculation that's applied to the result of a web scraper
 */
public interface Processor {

    /**
     * Apply transformation/processing to the given result.
     * @param result result of scraping
     */
    public abstract void process(ScrapeResult result);

    /**
     * Returns the name of the processor
     */
    public abstract String getName();
}

package com.meylism;

import com.google.common.base.Preconditions;
import com.meylism.reporter.Reporter;
import lombok.Getter;

/**
 * A facade interface to cater different scraping implementations.
 */
public abstract class Facade {
    @Getter
    private String url;

    Facade(String url) {
        Preconditions.checkArgument(url != null && url.length() > 0, "url should exist");
        this.url = url;
    }

    /**
     * Scrape the url
     */
    public abstract void scrape();

    /**
     * Export results to a specific format
     * @param reporter format-specific reporter
     */
    public abstract void report(Reporter reporter);
}

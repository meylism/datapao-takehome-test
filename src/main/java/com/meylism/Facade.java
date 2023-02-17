package com.meylism;

import com.google.common.base.Preconditions;
import com.meylism.reporter.FileReporter;
import lombok.Getter;

import java.io.File;

/**
 * A facade interface to cater different scraping implementations.
 */
public abstract class Facade {
    @Getter
    private String url;
    @Getter
    private FileReporter reporter;

    /**
     *
     * @param url the url from which to scrape data
     * @param reporter file-specific result reporter
     */
    Facade(String url, FileReporter reporter) {
        Preconditions.checkArgument(url != null && url.length() > 0, "url should exist");
        Preconditions.checkArgument(reporter != null);
        this.url = url;
        this.reporter = reporter;
    }

    /**
     * Scrape the url
     */
    public abstract void scrape();

    /**
     * Export results to a specific format
     */
    public abstract void report(File file);
}

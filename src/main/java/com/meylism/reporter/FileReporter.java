package com.meylism.reporter;

import com.meylism.ds.ScrapeResult;

import java.io.File;

/**
 * File reporter for exporting results of a scraping event.
 */
public interface FileReporter {

    /**
     * Export results
     * @param result scrape result
     * @param file directory where to export result
     */
    public abstract void export(ScrapeResult result, File file);
}

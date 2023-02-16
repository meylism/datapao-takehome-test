package com.meylism.ds;

import com.google.common.base.Preconditions;
import lombok.Getter;

/**
 * Class that encompasses all data of a scraping event.
 */
public class ScrapeResult<T> {
    // in milliseconds
    @Getter
    private long scrapeTime;
    @Getter
    private T data;

    public void setScrapeTime(long scrapeTime) {
        Preconditions.checkArgument(scrapeTime >= 0, "Scrape time should be non-negative");
        this.scrapeTime = scrapeTime;
    }

    public void setData(T data) {
        Preconditions.checkArgument(data != null, "Data object cannot be null");
        this.data = data;
    }
}

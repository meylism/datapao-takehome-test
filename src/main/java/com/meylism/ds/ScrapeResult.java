package com.meylism.ds;

import com.google.common.base.Preconditions;
import lombok.Getter;

/**
 * Class that encompasses all data of a scraping event.
 */
public class ScrapeResult {
    @Getter
    private Object data;

    public void setData(Object data) {
        Preconditions.checkArgument(data != null, "Data object cannot be null");
        this.data = data;
    }
}

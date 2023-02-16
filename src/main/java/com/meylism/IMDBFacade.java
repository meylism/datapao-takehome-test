package com.meylism;

import com.meylism.ds.IMDBMovie;
import com.meylism.ds.ScrapeResult;
import com.meylism.reporter.Reporter;
import com.meylism.scraper.IMDBScraper;
import com.meylism.scraper.Scraper;

import java.util.List;

public class IMDBFacade extends Facade {

    public IMDBFacade(String url) {
        super(url);
    }

    @Override
    public void scrape() {
        Scraper scraper = new IMDBScraper(getUrl());
        ScrapeResult result = (ScrapeResult)scraper.scrape();

        List<IMDBMovie> movieList = (List<IMDBMovie>) result.getData();
        for (IMDBMovie movie : movieList) {
            System.out.println(movie);

        }
    }

    @Override
    public void report(Reporter reporter) {

    }
}

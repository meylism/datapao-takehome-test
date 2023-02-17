package com.meylism;

import com.meylism.ds.IMDBMovie;
import com.meylism.ds.ScrapeResult;
import com.meylism.processor.IMDBOscarCalculator;
import com.meylism.processor.IMDBReviewPenalizer;
import com.meylism.processor.Processor;
import com.meylism.reporter.FileReporter;
import com.meylism.reporter.ImdbCsvReporter;
import com.meylism.scraper.IMDBScraper;
import com.meylism.scraper.Scraper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IMDBFacade extends Facade {
    private final Logger logger = LogManager.getLogger(IMDBFacade.class);
    private final List<Processor> processors = new ArrayList<>(Arrays.asList(new IMDBOscarCalculator(), new IMDBReviewPenalizer()));
    private ScrapeResult result;

    public IMDBFacade(String url) {
        super(url);
    }

    @Override
    public void scrape() {
        Scraper scraper = new IMDBScraper(getUrl());
        result = (ScrapeResult)scraper.scrape();

        List<IMDBMovie> movieList = (List<IMDBMovie>) result.getData();
        for (IMDBMovie movie : movieList) {
            System.out.println(movie);
        }

        // process
        for (Processor processor : processors) {
            logger.debug("Applying processor: {}", processor.getName());
            processor.process(result);
            logger.debug("Applied processor: {}", processor.getName());
        }

        for (IMDBMovie movie : movieList) {
            System.out.println(movie);

        }
    }

    @Override
    public void report(FileReporter reporter) {
        ImdbCsvReporter imdbCsvReporter = new ImdbCsvReporter(this.result);
        imdbCsvReporter.export(null);
    }
}

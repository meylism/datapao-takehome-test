package com.meylism;

import com.meylism.ds.IMDBMovie;
import com.meylism.ds.ScrapeResult;
import com.meylism.processor.IMDBOscarCalculator;
import com.meylism.processor.IMDBReviewPenalizer;
import com.meylism.processor.Processor;
import com.meylism.reporter.FileReporter;
import com.meylism.scraper.IMDBScraper;
import com.meylism.scraper.Scraper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;

public class IMDBFacade extends Facade {
    private final Logger logger = LogManager.getLogger(IMDBFacade.class);
    private final List<Processor> processors = new ArrayList<>(Arrays.asList(new IMDBOscarCalculator(), new IMDBReviewPenalizer()));
    private ScrapeResult result;

    public IMDBFacade(String url, FileReporter reporter) {
        super(url, reporter);
    }

    @Override
    public void scrape() {
        Scraper scraper = new IMDBScraper(getUrl(), 20);
        result = (ScrapeResult)scraper.scrape();

        // process
        for (Processor processor : processors) {
            logger.debug("Applying processor: {}", processor.getName());
            processor.process(result);
            logger.debug("Applied processor: {}", processor.getName());
        }
    }

    @Override
    public void report(File file) {
        Comparator<IMDBMovie> comparator = Comparator.comparingDouble(IMDBMovie::getNewRating).reversed();
        Collections.sort((List<IMDBMovie>) result.getData(), comparator);

        this.getReporter().export(result, file);
    }
}

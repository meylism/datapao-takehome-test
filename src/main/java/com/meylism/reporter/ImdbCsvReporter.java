package com.meylism.reporter;

import com.meylism.ds.IMDBMovie;
import com.meylism.ds.ScrapeResult;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ImdbCsvReporter extends FileReporter {
    private Logger logger = LogManager.getLogger(ImdbCsvReporter.class);
    private final String[] headers = {"Title", "Number of Ratings", "Number of Oscars", "Original Rating", "New Rating", "Oscar Rating", "Penalized Rating"};

    public ImdbCsvReporter(ScrapeResult result) {
        super(result);
    }

    @Override
    public void export(File file) {


        try {
            FileWriter out = new FileWriter("datapao.csv");
            CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers));

            for (IMDBMovie movie : (List<IMDBMovie>)getResult().getData()) {
                printer.printRecord(
                        movie.getTitle(),
                        movie.getNumRating(),
                        movie.getNumOscar(),
                        movie.getRating(),
                        String.format("%.1f", movie.getNewRating()),
                        String.format("%.1f", movie.getOscarInfluencedRating()),
                        String.format("%.1f", movie.getPenalizedRating()));
            }
            out.close();
        } catch (IOException e) {
            logger.error("Couldn't export the results. Exiting: {}", e.getMessage());
            System.exit(-1);
        } finally {
        }
    }
}

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

public class ImdbCsvReporter implements FileReporter {
    private final Logger logger = LogManager.getLogger(ImdbCsvReporter.class);
    private final String[] headers = {"Title", "Number of Ratings", "Number of Oscars", "Original Rating",
            "New Rating", "Oscar Rating", "Penalized Rating"};

    @Override
    public void export(ScrapeResult result, File file) {
        try {
            String path = file.getAbsolutePath() + "/datapao.csv";
            FileWriter out = new FileWriter(path);
            logger.debug("Exporting results to {}", path);
            CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers));

            for (IMDBMovie movie : (List<IMDBMovie>)result.getData()) {
                printer.printRecord(
                        movie.getTitle(),
                        movie.getNumRating(),
                        movie.getNumOscar(),
                        movie.getRating(),
                        String.format("%.1f", movie.getNewRating()),
                        String.format("%.1f", movie.getOscarInfluencedRating()),
                        String.format("%.1f", movie.getPenalizedRating()));
            }
            printer.close();
            out.close();
        } catch (IOException e) {
            logger.error("Couldn't export the results. Exiting: {}", e.getMessage());
            System.exit(-1);
        }
    }
}

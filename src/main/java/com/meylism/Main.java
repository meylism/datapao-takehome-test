package com.meylism;

import com.google.common.base.Preconditions;
import com.meylism.reporter.ImdbCsvReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;

public class Main {
    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Preconditions.checkArgument(args.length <= 1, "Extra arguments are given");
        File file;

        if (args.length == 1) {
            // handle home directory
            file = new File(args[0].replaceFirst("^~", System.getProperty("user.home")));
            if (!file.exists()) {
                logger.error("Given path doesn't exist: {}", file.toString());
                System.exit(-1);
            }
        } else {
            // if path is not given, then default to the current working directory
            file = Path.of("").toFile();
        }

        Facade facade = new IMDBFacade("https://www.imdb.com/chart/top/", new ImdbCsvReporter());
        facade.scrape();
        facade.report(file);
    }
}
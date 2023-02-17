package com.meylism.scraper;

import com.google.common.base.Preconditions;
import com.meylism.ds.IMDBMovie;
import com.meylism.ds.ScrapeResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class IMDBScraper extends Scraper {
    private final Logger logger = LogManager.getLogger(IMDBScraper.class);
    private Integer numMoviesToFetch;
    public IMDBScraper(String url, Integer numMoviesToFetch) {
        super(url);
        Preconditions.checkArgument(numMoviesToFetch >= 0);
        this.numMoviesToFetch = numMoviesToFetch;
    }

    @Override
    public ScrapeResult scrape() {
        ScrapeResult result = new ScrapeResult();
        Document doc = null;
        List<IMDBMovie> movieList = new ArrayList<>();

        doc = fetch(getUrl());

        // table
        Elements chart = doc.getElementsByClass("chart");
        // tbody
        Element chartBody = chart.get(0).child(2);

        logger.debug("Started processing scraped data");
        for(int i=0; i<numMoviesToFetch; i++) {
            movieList.add(processMovie(chartBody.child(i)));
        }
        logger.debug("Finished processing scraped data");

        // data is a list of movies
        result.setData(movieList);
        return result;
    }

    private IMDBMovie processMovie(Element row) {
        IMDBMovie imdbMovie = new IMDBMovie();

        processTitle(row.child(1), imdbMovie);
        processOscar(row.child(1), imdbMovie);
        processRatings(row.child(2), imdbMovie);

        return imdbMovie;
    }

    private void processTitle(Element title, IMDBMovie movie) {
        movie.setTitle(title.child(0).text());
    }

    private void processRatings(Element ratings, IMDBMovie movie) {
        String titleString = ratings.child(0).attr("title");
        // extracting info from a sentence
        String[] tokens = titleString.split(" ");

        movie.setRating(Double.valueOf(tokens[0]));
        movie.setNumRating(Long.valueOf(tokens[3].replace(",", "")));
    }

    /**
     * Fetch Oscar-award-related info and process.
     * The info is fetched from https://www.imdb.com/title/{movie-id}/awards/
     */
    private void processOscar(Element title, IMDBMovie movie) {
        String url = title.child(0).attr("href");
        // to get the url prefix
        String[] tokens = url.split("/");
        String awardUrl = new StringBuilder()
                .append("https://www.imdb.com/")
                .append("title/")
                .append(tokens[2])
                .append("/awards/")
                .toString();

        Document doc = fetch(awardUrl);

        movie.setNumOscar(extractOscarNumber(doc));
    }

    private int extractOscarNumber(Document document) {
        int numOscar = 0;

        Elements categories = document.getElementsByClass("award_category");

        for(Element category : categories) {
            // walking up and down in the DOM to get relevant information
            Element outcome = category.parent();
            Element outcomeChildBold = category.previousElementSibling().previousElementSibling();
            if (category.text().equals("Oscar") && outcomeChildBold.text().equals("Winner")) {
                numOscar = Integer.parseInt(outcome.attr("rowspan"));
                break;
            }
        }

        return numOscar;
    }
}

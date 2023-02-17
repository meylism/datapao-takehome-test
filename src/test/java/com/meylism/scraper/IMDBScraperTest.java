package com.meylism.scraper;

import com.meylism.ds.IMDBMovie;
import com.meylism.ds.ScrapeResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class IMDBScraperTest {
    private static final Logger logger = LogManager.getLogger(IMDBScraperTest.class);
    private final IMDBScraper scraper = new IMDBScraper("https://www.imdb.com/chart/top/", 2);;
    private final IMDBScraper mockScraper = spy(scraper);

    @Before
    public void setup() {
        // mock the scraper to load pages locally, not through the Internet
        // this allows for more stable testing and the web is dynamic
        Document main = loadHTMLFromFile("main.html");
        Document awards1 = loadHTMLFromFile("awards1.html");
        Document awards2 = loadHTMLFromFile("awards2.html");

        doReturn(main).when(mockScraper).fetch("https://www.imdb.com/chart/top/");
        doReturn(awards1).when(mockScraper).fetch("https://www.imdb.com/title/tt0111161/awards/");
        doReturn(awards2).when(mockScraper).fetch("https://www.imdb.com/title/tt0068646/awards/");
    }

    @Test
    public void validateResultOfScraper() {
        ScrapeResult result = mockScraper.scrape();
        List<IMDBMovie> movieList = (List<IMDBMovie>) result.getData();

        IMDBMovie firstMovie = movieList.get(0);
        Assert.assertEquals("The Shawshank Redemption", firstMovie.getTitle());
        Assert.assertEquals(9.2, (double)firstMovie.getRating(), 0.001);
        Assert.assertEquals(2701995, (long)firstMovie.getNumRating());
        Assert.assertEquals(0, (int)firstMovie.getNumOscar());

        IMDBMovie secondMovie = movieList.get(1);
        Assert.assertEquals("The Godfather", secondMovie.getTitle());
        Assert.assertEquals(9.2, (double)secondMovie.getRating(), 0.001);
        Assert.assertEquals(1875864, (long)secondMovie.getNumRating());
        Assert.assertEquals(3, (int)secondMovie.getNumOscar());
    }

    private Document loadHTMLFromFile(String resourceName) {
        File file = null;
        try {
            file = new File(getClass().getClassLoader().getResource(resourceName).toURI());
        } catch (URISyntaxException e) {
            logger.warn("Couldn't read file for testing: {}", e.getMessage());
            System.exit(-1);
        }

        Document doc = null;

        try {
            doc = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            logger.warn("Couldn't parse the loaded HTML: {}", e.getMessage());
            System.exit(-1);
        }
        return doc;
    }
}


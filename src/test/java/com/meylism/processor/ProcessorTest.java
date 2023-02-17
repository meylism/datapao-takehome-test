package com.meylism.processor;

import com.meylism.ds.IMDBMovie;
import com.meylism.ds.ScrapeResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ProcessorTest {
    private ScrapeResult mockResult;
    private IMDBMovie firstMovie, secondMovie;

    @Before
    public void setUp() {
        firstMovie = new IMDBMovie();
        secondMovie = new IMDBMovie();

        firstMovie.setTitle("First");
        firstMovie.setRating(10.0);
        firstMovie.setNumRating(2_071_000L);
        firstMovie.setNumOscar(0);

        secondMovie.setTitle("Second");
        secondMovie.setRating(9.2);
        secondMovie.setNumRating(1_010_150L);
        secondMovie.setNumOscar(3);

        mockResult = mock(ScrapeResult.class);
        when(mockResult.getData()).thenReturn(Arrays.asList(firstMovie, secondMovie));
    }

    @Test
    public void testOscarCalculator() {
        IMDBOscarCalculator imdbOscarCalculator = new IMDBOscarCalculator();
        imdbOscarCalculator.process(mockResult);

        List<IMDBMovie> processedMovies = (List<IMDBMovie>) mockResult.getData();

        // First movie
        // 0 Oscar results in no gain
        Assert.assertEquals(firstMovie.getRating(), processedMovies.get(0).getOscarInfluencedRating());

        // Second movie
        // 3 Oscars results in a gain of 0.5 point
        Double expected = secondMovie.getRating() + 0.5;
        Assert.assertEquals(expected, processedMovies.get(1).getOscarInfluencedRating());
    }

    @Test
    public void testRatingPenalizer() {
        IMDBReviewPenalizer imdbReviewPenalizer = new IMDBReviewPenalizer();
        imdbReviewPenalizer.process(mockResult);

        List<IMDBMovie> processedMovies = (List<IMDBMovie>) mockResult.getData();


        // First movie has the highest number of ratings: 2,071,000

        // First movie
        // No penalty
        Assert.assertEquals(firstMovie.getRating(), processedMovies.get(0).getPenalizedRating());

        // Second movie
        // diff is 1,060,850; results in 1.0 point of penalty
        Double expected = secondMovie.getRating() - 1.0;
        Assert.assertEquals(expected, processedMovies.get(1).getPenalizedRating());
    }
}
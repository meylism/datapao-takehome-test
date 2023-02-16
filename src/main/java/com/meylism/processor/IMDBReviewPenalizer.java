package com.meylism.processor;

import com.meylism.ds.IMDBMovie;
import com.meylism.ds.ScrapeResult;

import java.util.List;

/**
 * Transformation to penalize movies with low number of ratings.
 *
 * Find the movie with the maximum number of ratings.
 * This is going to be the benchmark. Compare every movie’s number of ratings to this and
 * penalize each of them based on the following rule: Every 100k deviation from the
 * maximum translates to a point deduction of 0.1.
 */

public class IMDBReviewPenalizer extends Processor {
    @Override
    public void process(ScrapeResult result) {
        List<IMDBMovie> movieList = (List<IMDBMovie>) result.getData();
        Long maxReview = findMaxReview(movieList);

        for (IMDBMovie movie : movieList) {
            long diff = maxReview - movie.getNumRating();
            long penaltyFactor = Math.floorDiv(diff, 100_000);
            Double penalty = penaltyFactor * 0.1;

            if (movie.getNewRating() == null) {
                movie.setNewRating(movie.getRating());
            }

            movie.setPenalizedRating(movie.getRating() - penalty);
            movie.setNewRating(movie.getNewRating() - penalty);
        }
    }

    @Override
    public String getName() {
        return "Low Review Number Penalizer Processor";
    }

    private Long findMaxReview(List<IMDBMovie> movieList) {
        long max = 0;

        for (IMDBMovie movie : movieList) {
            if (movie.getNumRating() > max) max = movie.getNumRating();
        }
        return max;
    }


}

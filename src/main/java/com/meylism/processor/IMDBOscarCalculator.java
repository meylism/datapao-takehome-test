package com.meylism.processor;

import com.meylism.ds.IMDBMovie;
import com.meylism.ds.ScrapeResult;

import java.util.List;

/**
 * Tranformation to credit some points based on the number of Oscars awarded.
 *
 * 1 or 2 oscars → 0.3 point
 * 3 or 5 oscars → 0.5 point
 * 6 to 10 oscars → 1 point
 * 10+ oscars → 1.5 point
 */
public class IMDBOscarCalculator implements Processor{
    @Override
    public void process(ScrapeResult result) {
        List<IMDBMovie> movieList = (List<IMDBMovie>) result.getData();

        for (IMDBMovie movie : movieList) {
            int numOscar = movie.getNumOscar();
            double bonus = 0;
            if (numOscar == 1 || numOscar == 2) { bonus = 0.3; }
            else if (numOscar >=3 && numOscar <= 5) { bonus = 0.5; }
            else if (numOscar >=6 && numOscar <= 10) { bonus = 1.0; }
            else if (numOscar > 10) { bonus = 1.5; }

            if (movie.getNewRating() == null) {
                movie.setNewRating(movie.getRating());
            }

            movie.setOscarInfluencedRating(movie.getRating() + bonus);
            movie.setNewRating(movie.getNewRating() + bonus);
        }
    }

    @Override
    public String getName() {
        return "Oscar Award Bonus Adder Processor";
    }
}

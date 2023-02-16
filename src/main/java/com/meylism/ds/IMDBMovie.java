package com.meylism.ds;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.ToString;

@ToString
public class IMDBMovie {
    @Getter
    private String title;
    @Getter
    private Double rating;
    @Getter
    private Double penalizedRating;
    @Getter
    private Double oscarInfluencedRating;
    @Getter
    private Double newRating;
    @Getter
    private Long numRating;
    @Getter
    private Integer numOscar;

    public void setTitle(String title) {
        Preconditions.checkArgument(title != null && title.length() > 0, "Title should exist");
        this.title = title;
    }

    public void setRating(Double rating) {
        Preconditions.checkArgument(rating >= 0.0);
        this.rating = rating;
    }

    public void setNumRating(Long numRating) {
        Preconditions.checkArgument(numRating >= 0);
        this.numRating = numRating;
    }

    public void setPenalizedRating(Double penalizedRating) {
        Preconditions.checkArgument(penalizedRating >= 0.0);
        this.penalizedRating = penalizedRating;
    }

    public void setOscarInfluencedRating(Double oscarInfluencedRating) {
        Preconditions.checkArgument(oscarInfluencedRating >= 0.0);
        this.oscarInfluencedRating = oscarInfluencedRating;
    }

    public void setNewRating(Double newRating) {
        Preconditions.checkArgument(newRating >= 0.0);
        this.newRating = newRating;
    }

    public void setNumOscar(Integer numOscar) {
        Preconditions.checkArgument(numOscar >= 0);
        this.numOscar = numOscar;
    }
}

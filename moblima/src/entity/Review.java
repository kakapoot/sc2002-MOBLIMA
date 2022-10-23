package entity;

import java.io.Serializable;

public class Review implements Serializable {
    private int rating;
    private String reviewText;

    public Review(int rating, String reviewText) {
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    @Override
    public String toString() {
        return "Review [rating=" + rating + ", reviewText=" + reviewText + "]";
    }
}
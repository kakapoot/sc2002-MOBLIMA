package control;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.*;
import entity.Constants.ContentRating;
import entity.Constants.MovieType;

public class MovieController extends DatabaseController<Movie> {
    public MovieController() {
        super(MainController.FILEPATH_MOVIE);
    }

    // Search for a movie by its movie id in the Movie database
    // Returns the matching movie, returns null if movie not found
    public Movie findMovie(int id) {
        ArrayList<Movie> movies = readFromDatabase();
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }

    // Search for movies with matching title in the Movie database
    // Returns an ArrayList of matching movies, returns null if no movie found
    public ArrayList<Movie> findMovies(String title) {
        ArrayList<Movie> movies = readFromDatabase();
        ArrayList<Movie> moviesResult = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().equals(title.toLowerCase())) {
                moviesResult.add(movie);
            }
        }

        if (moviesResult.isEmpty()) {
            return null;
        }
        return moviesResult;
    }

    // Search for and deletes movie with movie id in the Movie database
    // Returns true on successful deletion
    public boolean deleteMovie(int id) {
        ArrayList<Movie> movies = readFromDatabase();
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                movies.remove(movie);
                overwriteDatabase(movies);
                return true;
            }
        }
        return false;
    }

    // In the order of selection options provided to user when updating movie
    // attributes
    private enum Attributes {
        ID, TITLE, SYNOPSIS, DIRECTOR, CAST, GENRES, RELEASE_DATE, END_DATE, CONTENT_RATING, MOVIE_TYPE
    }

    // Updates the selected movie's entry in Movie database with the new attribute
    @SuppressWarnings("unchecked")
    public void updateMovieAttribute(Movie movie, int attribute, Object newAttributeValue) {
        ArrayList<Movie> movies = readFromDatabase();
        int movieIndexInDatabase = movies.indexOf(movie);

        Attributes[] attributes = Attributes.values();
        switch (attributes[attribute - 1]) {
            case ID:
                movie.setId((int) newAttributeValue);
                break;
            case TITLE:
                movie.setTitle((String) newAttributeValue);
                break;
            case SYNOPSIS:
                movie.setSynopsis((String) newAttributeValue);
                break;
            case DIRECTOR:
                movie.setDirector((String) newAttributeValue);
                break;
            case CAST:
                movie.setCast((ArrayList<String>) newAttributeValue);
                break;
            case GENRES:
                movie.setGenres((ArrayList<String>) newAttributeValue);
                break;
            case RELEASE_DATE:
                movie.setReleaseDate((LocalDate) newAttributeValue);
                break;
            case END_DATE:
                movie.setEndDate((LocalDate) newAttributeValue);
                break;
            case CONTENT_RATING:
                movie.setContentRating((ContentRating) newAttributeValue);
                break;
            case MOVIE_TYPE:
                movie.setMovieType((MovieType) newAttributeValue);
                break;
        }
        movies.set(movieIndexInDatabase, movie);
        overwriteDatabase(movies);
    }

    // Adds the given review to selected movie's entry in Movie database
    public void addReviewToMovie(Movie movie, Review review) {
        ArrayList<Movie> movies = readFromDatabase();
        int movieIndexInDatabase = movies.indexOf(movie);

        // Append new review to existing list of reviews
        ArrayList<Review> reviews = movie.getReviews();
        reviews.add(review);

        // Update movie average rating and reviews
        movie.setReviews(reviews);
        movie.setAverageReviewRating();

        // Updates the selected movie's entry in Movie database with new list of reviews
        movies.set(movieIndexInDatabase, movie);
        overwriteDatabase(movies);
    }

    public boolean updateMovieObject(Movie updatedMovie) { // method that overwrites database everytime a movie
                                                           // has an update
        ArrayList<Movie> MovieList = readFromDatabase();
        for (int i = 0; i < MovieList.size(); i++) {
            if (updatedMovie.getId() == MovieList.get(i).getId()) {
                MovieList.set(i, updatedMovie);
                overwriteDatabase(MovieList);
                return true;
            }
        }
        return false;
    }
}
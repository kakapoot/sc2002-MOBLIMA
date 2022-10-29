package control;

import java.time.LocalDate;
import java.util.ArrayList;

import boundary.MovieGoerUI;
import entity.*;
import entity.Constants.ContentRating;
import entity.Constants.MovieType;

public class MovieController extends DatabaseController<Movie> {
    public MovieController() {
        super(MainController.FILEPATH_MOVIE);
    }

    public Movie getMovieById(int id) {
        ArrayList<Movie> movies = readFromDatabase();
        for (Movie movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }

    // Returns true on successful deletion
    public boolean deleteMovieById(int id) {
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
        ID, TITLE, SYNOPSIS, DIRECTOR, CAST, GENRES, RELEASE_DATE, CONTENT_RATING, MOVIE_TYPE
    }

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

    public static void listAll() { // Method from SeachMovie() to list all movies in db
        ArrayList<Movie> MovieList = MovieGoerUI.movieController.readFromDatabase();
        System.out.println();
        MovieGoerUI.printMovieObject(MovieList);
    }

    public static boolean updateMovieObject(Movie updatedMovie) { // method that overwrites database everytime a movie
                                                                  // has an update
        ArrayList<Movie> MovieList = MovieGoerUI.movieController.readFromDatabase();
        for (int i = 0; i < MovieList.size(); i++) {
            if (updatedMovie.getId() == MovieList.get(i).getId()) {
                MovieList.set(i, updatedMovie);
                MovieGoerUI.movieController.overwriteDatabase(MovieList);
                return true;
            }
        }
        return false;
    }
}
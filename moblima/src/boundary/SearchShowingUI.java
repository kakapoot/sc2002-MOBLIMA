package boundary;

import java.util.ArrayList;

import control.ShowingController;
import entity.Cineplex;
import entity.Movie;
import entity.Showing;

public class SearchShowingUI {
    private static ShowingController showingController = new ShowingController();

    public static void main() {
        do {
            // Displays showings regardless of showing status
            System.out.println("===== SEARCH/LIST SHOWING =====\n"
                    + "1. Search By Showing ID\n"
                    + "2. Search By Cineplex\n"
                    + "3. Search By Movie Title\n"
                    + "4. List All Showings\n"
                    + "0. Return\n");
            int choice = InputHandler.scanInt();
            switch (choice) {
                case 1:
                    searchShowingById();
                    break;
                case 2:
                    // TODO: searchShowingByCineplex();
                    break;
                case 3:
                    // TODO: searchShowingByMovie();
                    break;
                case 4:
                    listAllShowings();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        } while (true);
    }

    public static void searchShowingById() {
        int id = UserHandler.getIdFromUser();
        Showing showing = showingController.findShowing(id);
        if (showing == null) {
            System.out.println("No showing with ID " + id + " found!");
            return;
        }
        System.out.println("=================================================");
        System.out.println(showing);
        System.out.println();
    }

    // Print all showings in database
    public static void listAllShowings() {
        ArrayList<Showing> showings = showingController.readFromDatabase();
        if (showings.isEmpty()) {
            System.out.println("No showings exist in Showing database!");
            return;
        }
        System.out.println("=================================================");
        for (Showing showing : showings) {
            System.out.println(showing);
        }
        System.out.println();
    }

    // Overloaded listAllShowings function to print all showings in database with
    // matching cineplex and movie
    public static void listAllShowings(Cineplex cineplex, Movie movie) {
        ArrayList<Showing> showings = showingController.findShowings(cineplex, movie);
        if (showings == null) {
            System.out.println("No showings of " + movie.getTitle() + " is found at " + cineplex.getName() + "!");
            return;
        }
        for (Showing showing : showings) {
            System.out.println(showing);
        }
        System.out.println();
    }
}

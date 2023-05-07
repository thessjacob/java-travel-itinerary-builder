/**
 * TravelPlanner.java is the entrypoint to the Travel Itinerary Planner program. It inits the database object and then
 * opens the main window. Additional countries may be added by adding additional Strings to the COUNTRY_NAMES variable.
 *
 * @author JSS
 * @last_modified 05_07_2023
 */

import database.DatabaseController;
import gui.WindowController;

public class TravelPlanner {
    private final String[] COUNTRY_NAMES = {"Serbia", "Bosnia"};  //Example of additional country name added
    //private final String[] COUNTRY_NAMES = {"Serbia"};            //Just Serbia, the main result

    /**
     * 0-arg constructor for TravelPlanner.
     */
    private TravelPlanner() {
        DatabaseController.initDatabaseObject(COUNTRY_NAMES);
        WindowController.showMainWindow();
    }

    /**
     * Driver method to create the TravelPlanner program.
     */
    private static void run() {
        new TravelPlanner();
    }

    /**
     * Run the program
     * @param args standard cli args. None expected or interpreted if entered.
     */
    public static void main(String[] args) {
        run();
    }
}

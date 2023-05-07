/**
 * WindowController.java is a public class containing static methods that allow other parts of the program to open and
 * close windows. It should not be possible for a component to close a window that it is not a part of due to needing to
 * reference the window to close it.
 */

package gui;

import javax.swing.*;

public class  WindowController {

    /**
     * Opens a new MainWindow, the initial window a user sees.
     */
    public static void showMainWindow() {
        new MainWindow();
    }

    /**
     * Creates a new MainOptionsWindow, which is the second window a user typically sees.
     */
    public static void showMainOptionsWindow() {
        new MainOptionsWindow();
    }

    /**
     * Creates a CountryWindow, the window that allows a user to explore sites and cities in a country.
     */
    public static void showCountryWindow() {
        new CountryWindow();
    }

    /**
     * Creates an ItineraryWindow, the window that allows a user to plan an itinerary.
     */
    public static void showItineraryWindow() {
        new ItineraryWindow();
    }

    /**
     * Disposes of a window passed in as an argument.
     * @param window JFrame or JFrame descended window to dispose of.
     */
    public static void disposeWindow(JFrame window) {
        window.dispose();
    }
}

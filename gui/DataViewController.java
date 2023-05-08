/**
 * DataViewController.java is a public controller for the program View. It is implemented as an enum style singleton,
 * so that all references to the DataViewController return the same state. It is a crucial intermediary for the Java
 * Swing components, allowing them to access the information needed to correctly display the Model.
 */

package gui;

import database.DatabaseController;
import destination.Country;
import destination.SuperRegion;
import plan.ItineraryController;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;

public enum DataViewController {

    INSTANCE;

    private Country country;
    private ArrayList<SuperRegion> superRegions;
    private int numOfSuperRegions;
    private boolean planning;
    private ItineraryPanel ip;
    private final ItineraryController ic = ItineraryController.INSTANCE;

    /**
     * Inits the DataViewController using the name of the country the user has chosen to visit.
     * @param countryName String name of country that the view will be of.
     */
    public void initController(String countryName) {
        country = DatabaseController.getCountry(countryName);
        superRegions = country.getSuperRegions();
        numOfSuperRegions = superRegions.size();
    }

    /**
     * Returns the Country object that the view is currently displaying.
     * @return current Country object.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Returns a list of all the countries in the DatabaseObject.
     * @return ArrayList of Country objects representing all available countries in the database.
     */
    public ArrayList<Country> getCountries() {
        return DatabaseController.getAllCountries();
    }

    /**
     * Returns description of a Site based on the String siteName passed into the method.
     * @param siteName String name of site.
     * @return String description of a site.
     */
    public String getSiteDescription(String siteName) {
        return country.getSite(siteName).getDescription();
    }

    /**
     * Returns name of the nearest city to a Site based on the String siteName passed into the method.
     * @param siteName String name of site.
     * @return  String name of nearest city to the site.
     */
    public String getSiteNearestCityName(String siteName) {
        return country.getSite(siteName).getNearestCityName();
    }

    /**
     * Returns default or base amount of time to spend at a Site based on the String siteName passed into the
     * method. This time is the base amount that can be added to the itinerary for a site.
     * @param siteName String name of a site.
     * @return double base or default amount of time to spend at the site.
     */
    public double getSiteDefaultTime(String siteName) {
        return country.getSite(siteName).getDefaultTime();
    }

    /**
     * Returns URL object that contains a picture of a site based String siteName passed into the method.
     * @param siteName String name of a site.
     * @return URL object with an image of the site.
     */
    public URL getSiteImageURL(String siteName) {
        return country.getSite(siteName).getImageURL();
    }

    /**
     * Returns description of a City based on the String cityName passed into the method.
     * @param cityName String name of city.
     * @return String description of a city.
     */
    public String getCityDescription(String cityName) {
        return country.getCity(cityName).getDescription();
    }

    /**
     * Returns URL object that contains a picture of a city based String cityName passed into the method.
     * @param cityName String name of a city.
     * @return URL object with an image of the city.
     */
    public URL getCityImageURL(String cityName) {
        return country.getCity(cityName).getImageURL();
    }

    /**
     * Returns number of SuperRegions in the current Country.
     * @return int number of SuperRegions.
     */
    public int getNumOfSuperRegions() {
        return numOfSuperRegions;
    }

    /**
     * Sets View mode. Planning is set to true when using the ItineraryWindow to build an Itinerary and false when
     * using the CountryWindow to explore.
     * @param bool boolean on whether current mode is planning.
     */
    public void setPlanning(boolean bool) {
        planning = bool;
    }

    /**
     * Getter for the planning variable.
     * @return true if planning is set to true, otherwise return false.
     */
    public boolean isPlanning() {
        return planning;
    }

    /**
     * Sets the ItineraryPanel variable to allow agnostic manipulation of what appears on the panel.
     * @param ip ItineraryPanel that displays information about the user's itinerary.
     */
    public void setItineraryPanel(ItineraryPanel ip) {
        this.ip = ip;
    }

    /**
     * Adds time to the ItineraryPanel view.
     */
    public void addTimeOnPanel() {
        ip.setTime(ic.getTotalTime());
    }

    /**
     * Resets the ItineraryPanel view to a fresh itinerary.
     */
    public void resetItineraryPanel() {
        ip.resetTime();
        ip.itemList = ic.getList();
        ic.getList().revalidate();
    }

    /**
     * Throws a pop-up warning telling the user that they need to rest before planning more activities.
     */
    public void throwTimeWarning() {
        String message = "You need to leave time for sleep! Please add overnight rest to continue planning.";
        JOptionPane.showMessageDialog(ip, message);
    }
}

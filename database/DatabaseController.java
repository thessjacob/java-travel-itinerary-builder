/**
 * DatabaseController.java is a public class containing static methods that allow other parts of the program to interact
 * with the DatabaseObject.
 */

package database;

import destination.Country;

import java.util.ArrayList;

public class DatabaseController {

    private static DatabaseObject databaseObject;

    /**
     * Inits the DatabaseObject. It takes an array of strings, each with a country name.
     * @param countryNames a String array containing the countries to init.
     */
    public static void initDatabaseObject(String[] countryNames) {
        databaseObject = new DatabaseObject();
        databaseObject.initDatabaseObject(countryNames);
    }

    /**
     * Adds a Country object to the underlying DatabaseObject.
     * @param country Country object to add to the database.
     */
    public static void addCountry(Country country) {
        databaseObject.addCountry(country);
    }

    /**
     * Returns a Country object based on the String countryName that is passed into the method.
     * @param countryName String name of country to get from the DatabaseObject.
     * @return Country object if country with countryName exists; otherwise return null.
     */
    public static Country getCountry(String countryName) {
        return databaseObject.getCountry(countryName);
    }

    /**
     * Returns an ArrayList of all Country objects contained in the DatabaseObject.
     * @return ArrayList of Country objects.
     */
    public static ArrayList<Country> getAllCountries() {
        return databaseObject.getAllCountries();
    }
}

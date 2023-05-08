/**
 * DatabaseObject.java is a default access class that countains an ArrayList of all configured countries. This data
 * can be access from outside the package by the DatabaseController.
 */
package database;

import destination.Country;
import gui.DataViewController;

import java.util.ArrayList;

class DatabaseObject {
    private final ArrayList<Country> countries;

    /**
     * 0-arg constructor.
     */
    protected DatabaseObject () {
        countries = new ArrayList<>();
    }

    /**
     * Inits the database and fills it with information based on the passed in array of countryName strings.
     * @param countryNames String array with countries to init the database with.
     */
    void initDatabaseObject(String[] countryNames) {
        for (String s : countryNames) {
            String subDir = s + "/" + s;
            SuperRegionParser.readFile(DatabaseObject.class.getClassLoader().getResourceAsStream(subDir + ".txt"));
            CityParser.readFile(DatabaseObject.class.getClassLoader().getResourceAsStream(subDir + "City.txt"), s);
            SiteParser.readFile(DatabaseObject.class.getClassLoader().getResourceAsStream(subDir + "Site.txt"), s);
            DataViewController.INSTANCE.initController(s);
        }

    }

    /**
     * Adds a country object to the Database.
     * @param country Country object to add.
     */
    void addCountry(Country country) {
       if (!countries.contains(country)) {
           countries.add(country);
       }
    }

    /**
     * Gets a country from the database.
     * @param name String name of country.
     * @return Country object if one with input String name exists, otherwise return null.
     */
    Country getCountry(String name) {
        for (Country c : countries) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Return ArrayList of all Country objects in the database.
     * @return ArrayList of all Country objects in the database.
     */
    ArrayList<Country> getAllCountries() {
        return countries;
    }
}

package database;

import destination.AbstractCity;
import destination.Country;
import gui.DataViewController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

class DatabaseObject {
    private final ArrayList<Country> countries;
    private final HashMap<Country, ArrayList<AbstractCity>> cities;
    private final String BASE_URL;

    protected DatabaseObject () {
        countries = new ArrayList<>();
        BASE_URL = System.getProperty("user.dir") + "/src/database/";
        cities = new HashMap<>();
    }

    void initDatabaseObject(String[] countryNames) {
        for (String s : countryNames) {
            String subDir = s + "/" + s;
            SuperRegionParser.readFile(new File(BASE_URL + subDir + ".txt"));
            CityParser.readFile(new File(BASE_URL + subDir + "City.txt"), s);
            SiteParser.readFile(new File(BASE_URL + subDir + "Site.txt"), s);
            DataViewController.INSTANCE.initController(s);
        }
    }

    void addCountry(Country country) {
       if (!countries.contains(country)) {
           countries.add(country);
       }

       if (!cities.containsKey(country)) {
           cities.put(country, new ArrayList<AbstractCity>());
       }
    }

    Country getCountry(String name) {
        for (Country c : countries) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    ArrayList<Country> getAllCountries() {
        return countries;
    }

    ArrayList<AbstractCity> getCities(Country country) {
        return cities.get(country);
    }
}

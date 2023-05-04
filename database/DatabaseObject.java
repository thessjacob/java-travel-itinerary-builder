package database;

import destination.AbstractCity;
import destination.Country;
import gui.DataViewController;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

class DatabaseObject {
    private final ArrayList<Country> countries;
    private final HashMap<Country, ArrayList<AbstractCity>> cities;
    private final String BASE_URL;

    protected DatabaseObject () {
        countries = new ArrayList<>();
        BASE_URL = "/database/";
        cities = new HashMap<>();
    }

    void initDatabaseObject(String[] countryNames) {
        for (String s : countryNames) {
            String subDir = s + "/" + s;
            SuperRegionParser.readFile(DatabaseObject.class.getClassLoader().getResourceAsStream(subDir + ".txt"));
            CityParser.readFile(DatabaseObject.class.getClassLoader().getResourceAsStream(subDir + "City.txt"), s);
            SiteParser.readFile(DatabaseObject.class.getClassLoader().getResourceAsStream(subDir + "Site.txt"), s);
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

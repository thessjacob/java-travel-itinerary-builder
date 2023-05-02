package database;

import destination.Country;

import java.util.ArrayList;

public enum DatabaseController {

    INSTANCE;
    private static DatabaseObject databaseObject;

    public static void initDatabaseObject(String[] countryNames) {
        databaseObject = new DatabaseObject();
        databaseObject.initDatabaseObject(countryNames);
    }

    public static void addCountry(Country country) {
        databaseObject.addCountry(country);
    }

    public static Country getCountry(String countryName) {
        return databaseObject.getCountry(countryName);
    }

    public static ArrayList<Country> getAllCountries() {
        return databaseObject.getAllCountries();
    }

}

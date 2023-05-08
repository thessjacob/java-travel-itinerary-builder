/**
 * SuperRegionParser.java parses the country.txt file, reads the names of the SuperRegions, and then adds them to the
 * Country object they belong to in the Database.
 */
package database;

import destination.Country;
import destination.SuperRegion;

import java.io.InputStream;
import java.util.Scanner;

class SuperRegionParser {
    private static String currentCountryName = "";

    /**
     * Reads a file as input stream, carefully parsing its lines and branching to correct subordinate methods.
     * @param file File to read in as an InputStream.
     */
    static void readFile(InputStream file) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                switch (line) {
                    case "Name" -> parseName(scanner);
                    case "SuperRegions" -> parseSuperRegions(scanner);
                    default -> {}
                }
            }
        }
    }

    /**
     * Parses the SuperRegion names in the file.
     * @param scanner shared scanner for the InputStream file.
     */
    private static void parseSuperRegions(Scanner scanner) {
        String[] superRegions = scanner.nextLine().split(",");
        Country country = DatabaseController.getCountry(currentCountryName);
        for (String region : superRegions) {
            country.addSuperRegion(new SuperRegion(region, currentCountryName));
        }
    }

    /**
     * Parses the name of a country and adds it to the database as a Country Object.
     * @param scanner shared scanner for the InputStream file.
     */
    private static void parseName(Scanner scanner) {
        String name = scanner.nextLine();
        currentCountryName = name;
        DatabaseController.addCountry(new Country(name));
    }
}

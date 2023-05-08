/**
 * CityParser.java provides static methods for parsing the file that contains a list of AbstractCities found in a
 * country. That list should be carefully formatted and place in a file called "countrynameCity.txt".
 */
package database;

import destination.AbstractCity;
import destination.City;
import destination.Village;
import destination.special.Banja;


import java.io.InputStream;
import java.util.Scanner;

class CityParser {
    private static String currentCountryName = "";
    private static String superRegionName = "";

    /**
     * Reads a file as input stream, carefully parsing its lines and branching to correct subordinate methods.
     * @param file File to read in as an InputStream.
     * @param countryName String name of country we are parsing information for.
     */
    static void readFile(InputStream file, String countryName) {
        currentCountryName = countryName;
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                switch (line) {
                    case "SuperRegion" -> parseSuperRegion(scanner);
                    case "Name" -> parseName(scanner);
                    default -> superRegionName = "";
                }
            }
        }
    }

    /**
     * Parses the name of a new AbstractCity from the relevant file, then adds it to the DatabaseObject.
     * @param scanner shared scanner for the InputStream file.
     */
    private static void parseName(Scanner scanner) {
        String name = scanner.nextLine();
        int size = Integer.parseInt(scanner.nextLine());
        String type = scanner.nextLine();
        AbstractCity newCity = switch (type) {
            case "City" -> new City(name, size);
            case "Village" -> new Village(name, size);
            default -> specialParser(name, type);
        };
        DatabaseController.getCountry(currentCountryName).getSuperRegion(superRegionName).addCity(newCity);
        populateCity(newCity.getName());
    }

    /**
     * Sets the SuperRegion name for the current AbstractCity being parsed.
     * @param scanner shared scanner for the InputStream file.
     */
    private static void parseSuperRegion(Scanner scanner) {
        superRegionName = scanner.nextLine();
    }

    /**
     * Parses special AbstractCity types that are not "city" or "village"
     * @param name String name of AbstractCity object to create.
     * @param type String type of AbstractCity descended object to create.
     * @return null if not AbstractCity child object type matches input parameter type.
     */
    private static AbstractCity specialParser(String name, String type) {
        return switch (type) {
            case "Banja" -> new Banja(name);
            default -> null;
        };
    }

    /**
     * Parses through multiple lines of information to populate an AbstractCity object with necessary information from
     * the text file.
     * @param cityName String name of AbstractCity object.
     */
    private static void populateCity(String cityName) {
        String fileName = String.format("%s/Cities/%s.txt", currentCountryName, cityName);
        InputStream file = CityParser.class.getClassLoader().getResourceAsStream(fileName);
        Scanner scanner = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty() || line.isBlank()) {
                sb.append(System.lineSeparator());
            } else {
                sb.append(line).append(" ");
            }
        }

        String description = sb.toString();
        DatabaseController.getCountry(currentCountryName).getSuperRegion(superRegionName).getCity(cityName).
                setDescription(description);
        String imageFileString = String.format("%s/Cities/%s.jpg", currentCountryName, cityName);
        DatabaseController.getCountry(currentCountryName).getSuperRegion(superRegionName).getCity(cityName).
                setImageURL(imageFileString);
    }
}

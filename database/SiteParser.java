/**
 * SiteParser.java provides static methods for parsing the file that contains a list of AbstractSites found in a
 * country. That list should be carefully formatted and place in a file called "countrynameSite.txt".
 */
package database;

import destination.*;

import java.io.InputStream;
import java.util.Scanner;

class SiteParser {
    private static String currentCountryName = "";
    private static String superRegionName = "";
    private static String siteName = "";
    private static String nearestCityName = "";
    private static double time = 0;


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
                    case "Time" -> parseTime(scanner);
                    case "Nearest City" -> parseNearestCity(scanner);
                    case "---" -> superRegionName = "";
                    default -> createSite(line, scanner);
                }
            }
        }
    }

    /**
     * Creates a new AbstractSite object based on branching switch logic.
     * @param type String type of AbstractSite descended object to create.
     * @param scanner shared scanner for the InputStream file.
     */
    private static void createSite(String type, Scanner scanner) {
        AbstractSite site = switch (type) {
            case "Culinary" -> new CulinarySite(siteName, time);
            case "Cultural" -> createCulturalSite(scanner);
            case "Health" -> new HealthSite(siteName, time);
            case "Nature" -> new NatureSite(siteName, time);
            default -> null;
        };

        if (site != null) {
            DatabaseController.getCountry(currentCountryName).getSuperRegion(superRegionName).addSite(site);
            populateSite(siteName);
        }
    }

    /**
     * Creates CulturalSite subclass object if a subtype is provided.
     * @param scanner shared scanner for the InputStream file.
     * @return new CulturalSite descended object.
     */
    private static AbstractSite createCulturalSite(Scanner scanner) {
        String subtype = scanner.nextLine();

        return switch (subtype) {
            case "Historical" -> new HistoricalSite(siteName, time);
            case "Performance" -> new PerformanceSite(siteName, time);
            case "Sport" -> new SportsSite(siteName, time);
            default -> new CulturalSite(siteName, time);
        };
    }

    /**
     * Parses through multiple lines of information to populate an AbstractSite object with necessary information from
     * the text file.
     * @param siteName String name of AbstractSite object.
     */
    private static void populateSite(String siteName) {
        DatabaseController.getCountry(currentCountryName).getSuperRegion(superRegionName).getSite(siteName).setNearestCityName(nearestCityName);
        String fileName = String.format("%s/Sites/%s.txt", currentCountryName, siteName);
        InputStream file = SiteParser.class.getClassLoader().getResourceAsStream(fileName);
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
        DatabaseController.getCountry(currentCountryName).getSuperRegion(superRegionName).getSite(siteName).
                setDescription(description);
        String imageFileString = String.format("%s/Sites/%s.jpg", currentCountryName, siteName);
        DatabaseController.getCountry(currentCountryName).getSuperRegion(superRegionName).getSite(siteName).
                setImageURL(imageFileString);
    }

    /**
     * Parses the line with the site name on it.
     * @param scanner shared scanner for the InputStream file.
     */
    private static void parseName(Scanner scanner) {
        siteName = scanner.nextLine();
    }

    /**
     * Parses the line with the SuperRegion on it.
     * @param scanner shared scanner for the InputStream file.
     */
    private static void parseSuperRegion(Scanner scanner) {
        superRegionName = scanner.nextLine();
    }

    /**
     * Parses the line with the default time on it.
     * @param scanner shared scanner for the InputStream file.
     */
    private static void parseTime(Scanner scanner) {
        time = Double.parseDouble(scanner.nextLine());
    }

    /**
     * Parses the line with the nearest city name on it.
     * @param scanner shared scanner for the InputStream file.
     */
    private static void parseNearestCity(Scanner scanner) {
        nearestCityName = scanner.nextLine();
    }
}

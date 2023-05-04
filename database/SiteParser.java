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

    private static AbstractSite createCulturalSite(Scanner scanner) {
        String subtype = scanner.nextLine();

        return switch (subtype) {
            case "Historical" -> new HistoricalSite(siteName, time);
            case "Performance" -> new PerformanceSite(siteName, time);
            case "Sport" -> new SportsSite(siteName, time);
            default -> new CulturalSite(siteName, time);
        };
    }

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

    private static void parseName(Scanner scanner) {
        siteName = scanner.nextLine();
    }

    private static void parseSuperRegion(Scanner scanner) {
        superRegionName = scanner.nextLine();
    }

    private static void parseTime(Scanner scanner) {
        time = Double.parseDouble(scanner.nextLine());
    }

    private static void parseNearestCity(Scanner scanner) {
        nearestCityName = scanner.nextLine();
    }
}

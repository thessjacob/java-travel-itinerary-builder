package database;

import destination.AbstractCity;
import destination.City;
import destination.special.Banja;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CityParser {
    private static String currentCountryName = "";
    private static String superRegionName = "";

    static void readFile(File file, String countryName) {
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
        } catch (FileNotFoundException e) {
            System.out.println("File " + file + " not found!");
            throw new RuntimeException(e);
        }
    }

    private static void parseName(Scanner scanner) {
        String name = scanner.nextLine();
        int size = Integer.parseInt(scanner.nextLine());
        String type = scanner.nextLine();
        AbstractCity newCity = switch (type) {
            case "City" -> new City(name, size);
            case "Village" -> new City(name, size);
            default -> specialParser(name, type);
        };
        DatabaseController.getCountry(currentCountryName).getSuperRegion(superRegionName).addCity(newCity);
        populateCity(newCity.getName());
    }

    private static void parseSuperRegion(Scanner scanner) {
        superRegionName = scanner.nextLine();
    }

    private static AbstractCity specialParser(String name, String type) {
        return switch (type) {
            case "Banja" -> new Banja(name);
            default -> null;
        };
    }

    private static void populateCity(String cityName) {
        String fileName = String.format(System.getProperty("user.dir") +"/src/database/%s/Cities/%s.txt", currentCountryName, cityName);
        File file = new File(fileName);
        try {
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
            DatabaseController.getCountry(currentCountryName).getSuperRegion(superRegionName).getCity(cityName).setDescription(description);
            fileName = String.format(System.getProperty("user.dir") +"/src/database/%s/Cities/%s.jpg", currentCountryName, cityName);
            DatabaseController.getCountry(currentCountryName).getSuperRegion(superRegionName).getCity(cityName).setImageURL(fileName);
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("oops");
        }
    }
}

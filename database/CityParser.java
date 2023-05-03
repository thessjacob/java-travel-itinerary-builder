package database;

import destination.AbstractCity;
import destination.City;
import destination.Village;
import destination.special.Banja;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
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
            case "Village" -> new Village(name, size);
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
        String fileName = String.format("/database/%s/Cities/%s.txt", currentCountryName, cityName);
        File file = new File("");

        try {
            file = new File(CityParser.class.getResource(fileName).toURI());
        } catch (URISyntaxException uriSyntaxException) {
            System.out.println("couldn't find file " + fileName);
            System.exit(1);
        }
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
            URL url = CityParser.class.getResource(String.format("/database/%s/Cities/%s.jpg", currentCountryName, cityName));
            if (url == null) {
                file = new File ("");
            } else {
                file = new File(url.toURI());
            }

            DatabaseController.getCountry(currentCountryName).getSuperRegion(superRegionName).getCity(cityName).setImageURL(file.getAbsolutePath());
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("oops");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}

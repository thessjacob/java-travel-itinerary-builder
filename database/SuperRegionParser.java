package database;

import destination.Country;
import destination.SuperRegion;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class SuperRegionParser {
    private static String currentCountryName = "";

    static void readFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                switch (line) {
                    case "Name" -> parseName(scanner);
                    case "SuperRegions" -> parseSuperRegions(scanner);
                    default -> {}
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + file + " not found!");
            throw new RuntimeException(e);
        }
    }

    private static void parseSuperRegions(Scanner scanner) {
        String[] superRegions = scanner.nextLine().split(",");
        Country country = DatabaseController.getCountry(currentCountryName);
        for (String region : superRegions) {
            country.addSuperRegion(new SuperRegion(region, currentCountryName));
        }
    }

    private static void parseName(Scanner scanner) {
        String name = scanner.nextLine();
        currentCountryName = name;
        DatabaseController.addCountry(new Country(name));
    }
}

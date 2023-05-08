/**
 * Country is the basic unit of data management for this program. The country object contains all the data needed to
 * populate the windows and fill out the itinerary. It is built at initial runtime by the DatabaseController and then
 * queried as needed by the DataViewController.
 */
package destination;

import java.net.URL;
import java.util.ArrayList;

public class Country {
    private final String name;
    private final ArrayList<SuperRegion> superRegions;
    private final String baseMapImage;

    /**
     * 0-arg constructor. Should not be used.
     */
    public Country() {
        this("");
    }
    /**
     * 1-arg constructor with country name.
     * @param name String name of the country.
     */
    public Country(String name) {
        this.name = name;
        this.superRegions = new ArrayList<>();
        this.baseMapImage = name + "/" + name + ".png";
    }

    /**
     * Getter for name.
     * @return String name of country.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the URL with the image of the country.
     * @return URL with the image of the country.
     */
    public URL getBaseMapImage() {
        return Country.class.getClassLoader().getResource(baseMapImage);
    }

    /**
     * Adds a superRegion to the Country objects superRegions instance variable.
     * @param superRegion SuperRegion object to add to the superRegions instance variable.
     */
    public void addSuperRegion(SuperRegion superRegion) {
        if (!superRegions.contains(superRegion)) {
            superRegions.add(superRegion);
        }
    }

    /**
     * Getter for SuperRegion with String input name passed into the method.
     * @param name String name of the SuperRegion to get.
     * @return SuperRegion object if one with String input name exists, otherwise return null.
     */
    public SuperRegion getSuperRegion(String name) {
        for (SuperRegion superRegion : superRegions) {
            if (superRegion.getName().equals(name)) {
                return superRegion;
            }
        }
        return null;
    }

    /**
     * Gets list of SuperRegions contained in the superRegions instance variable.
     * @return ArrayList of SuperRegion objects.
     */
    public ArrayList<SuperRegion> getSuperRegions() {
        return superRegions;
    }

    /**
     * Getter for an AbstractSite with String input name passed into the method.
     * @param siteName String name of the AbstractSite to get.
     * @return AbstractSite object if one with String input name exists, otherwise return null.
     */
    public AbstractSite getSite(String siteName) {
        for (SuperRegion sr : superRegions) {
            for (AbstractSite site : sr.getSites()) {
                if (site.getName().equals(siteName)) {
                    return site;
                }
            }
        }
        return null;
    }

    /**
     * Getter for an AbstractCity with String input name passed into the method.
     * @param cityName String name of the AbstractCity to get.
     * @return AbstractCity object if one with String input name exists, otherwise return null.
     */
    public AbstractCity getCity(String cityName) {
        for (SuperRegion sr : superRegions) {
            for (AbstractCity city : sr.getCities()) {
                if (city.getName().equals(cityName)) {
                    return city;
                }
            }
        }
        return null;
    }
}

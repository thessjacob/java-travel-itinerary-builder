/**
 * SuperRegion.java is a class that creates objects that are subordinate to Country objects but actually contain sites
 * and cities to visit.
 */
package destination;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class SuperRegion {
    private final String name;
    private final String mapURLFile;
    private final ArrayList<AbstractCity> cities;
    private final ArrayList<AbstractSite> sites;

    /**
     * 0-arg constructor. Should not be used.
     */
    public SuperRegion() {
        this("","");
    }

    /**
     * Standard 2-arg constructor.
     * @param name String name of SuperRegion.
     * @param countryName String name of Country the SuperRegion belongs to.
     */
    public SuperRegion(String name, String countryName) {
        this.name = name;
        this.cities = new ArrayList<>();
        this.sites = new ArrayList<>();
        this.mapURLFile = countryName + "/SuperRegions/" + name + ".png";
    }

    /**
     * Getter for name.
     * @return String SuperRegion name.
     */
    public String getName() {
        return name;
    }

    /**
     * Adds an AbstractCity object to the list of AbstractCities.
     * @param city AbstractCity object to add.
     */
    public void addCity(AbstractCity city) {
        cities.add(city);
    }

    /**
     * Adds an AbstractSite object to the list of AbstractSites.
     * @param site AbstractSite object to add.
     */
    public void addSite(AbstractSite site) {
        sites.add(site);
    }

    /**
     * Getter for AbstractCity with String input name passed into the method.
     * @param cityName String name of the AbstractCity to get.
     * @return AbstractCity object with name cityName, otherwise return null.
     */
    public AbstractCity getCity(String cityName) {
        for (AbstractCity city : cities) {
            if (city.getName().equals(cityName)) {
                return city;
            }
        }
        return null;
    }

    /**
     * Getter for AbstractSite with String input name passed into the method.
     * @param siteName String name of the AbstractSite to get.
     * @return AbstractSite object with name siteName, otherwise return null.
     */
    public AbstractSite getSite(String siteName) {
        for (AbstractSite site : sites) {
            if (site.getName().equals(siteName)) {
                return site;
            }
        }
        return null;
    }

    /**
     * Gets list of all AbstractCity objects.
     * @return ArrayList of all AbstractCity objects.
     */
    public ArrayList<AbstractCity> getCities() {
        return cities;
    }

    /**
     * Gets list of all AbstractSite objects.
     * @return ArrayList of all AbstractSite objects.
     */
    public ArrayList<AbstractSite> getSites() {
        return sites;
    }

    /**
     * Gets URL with map image of the SuperRegion.
     * @return URL to map image of the SuperRegion.
     */
    public URL getMapImageFile() {
        return SuperRegion.class.getClassLoader().getResource(mapURLFile);
    }

    /**
     * Intellij generated equals method for comparison of SuperRegions.
     * @param o Object to compare current object against.
     * @return true if objects are equal based on name.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuperRegion that = (SuperRegion) o;
        return name.equals(that.name);
    }

    /**
     * Intellij generated Hashcode for comparison of SuperRegions.
     * @return int hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

package destination;

import database.DatabaseController;

import java.util.ArrayList;
import java.util.Objects;

public class SuperRegion {

    private final String name;
    private final String mapFile;
    private final ArrayList<AbstractCity> cities;
    private final ArrayList<AbstractSite> sites;

    public SuperRegion(String name, String countryName) {
        this.name = name;
        this.cities = new ArrayList<>();
        this.sites = new ArrayList<>();
        this.mapFile = System.getProperty("user.dir") + "/src/database/" + countryName + "/SuperRegions/" +
                name + ".png";
    }

    public String getName() {
        return name;
    }

    public void addCity(AbstractCity city) {
        cities.add(city);
    }

    public void addSite(AbstractSite site) {
        sites.add(site);
    }

    public ArrayList<AbstractCity> getCities() {
        return cities;
    }

    public ArrayList<AbstractSite> getSites() {
        return sites;
    }

    public AbstractSite getSite(String siteName) {
        for (AbstractSite site : sites) {
            if (site.getName().equals(siteName)) {
                return site;
            }
        }
        return null;
    }

    public AbstractCity getCity(String cityName) {
        for (AbstractCity city : cities) {
            if (city.getName().equals(cityName)) {
                return city;
            }
        }
        return null;
    }

    public String getBaseMapImage() {
        return mapFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuperRegion that = (SuperRegion) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

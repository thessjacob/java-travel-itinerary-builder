package destination;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class SuperRegion {

    private final String name;
    private final String mapURLFile;
    private final ArrayList<AbstractCity> cities;
    private final ArrayList<AbstractSite> sites;

    public SuperRegion(String name, String countryName) {
        this.name = name;
        this.cities = new ArrayList<>();
        this.sites = new ArrayList<>();
        this.mapURLFile = countryName + "/SuperRegions/" + name + ".png";
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

    public AbstractCity getCity(String cityName) {
        for (AbstractCity city : cities) {
            if (city.getName().equals(cityName)) {
                return city;
            }
        }
        return null;
    }

    public AbstractSite getSite(String siteName) {
        for (AbstractSite site : sites) {
            if (site.getName().equals(siteName)) {
                return site;
            }
        }
        return null;
    }

    public ArrayList<AbstractCity> getCities() {
        return cities;
    }

    public ArrayList<AbstractSite> getSites() {
        return sites;
    }

    public URL getMapImageFile() {
        return SuperRegion.class.getClassLoader().getResource(mapURLFile);
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

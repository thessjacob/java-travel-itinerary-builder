package destination;

import java.net.URL;
import java.util.ArrayList;

public class Country {
    private final String name;
    private final ArrayList<SuperRegion> superRegions;
    private final String baseMapImage;

    public Country(String name) {
        this.name = name;
        this.superRegions = new ArrayList<>();
        this.baseMapImage = name + "/" + name + ".png";
    }

    public String getName() {
        return name;
    }


    public URL getBaseMapImage() {
        return Country.class.getClassLoader().getResource(baseMapImage);
    }


    public void addSuperRegion(SuperRegion superRegion) {
        if (!superRegions.contains(superRegion)) {
            superRegions.add(superRegion);
        }
    }

    public SuperRegion getSuperRegion(String name) {
        for (SuperRegion superRegion : superRegions) {
            if (superRegion.getName().equals(name)) {
                return superRegion;
            }
        }
        return null;
    }

    public ArrayList<SuperRegion> getSuperRegions() {
        return superRegions;
    }

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

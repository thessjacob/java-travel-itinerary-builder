package plan;


import destination.AbstractCity;
import destination.AbstractSite;

import java.util.Objects;

public class ItineraryItem {
    private String name;
    private double time;
    private String description;

    ItineraryItem() {}

    ItineraryItem(String name, double time, String description) {
        this.name = name;
        this.time = time;
        this.description = description;
    }

    ItineraryItem(AbstractSite site) {
        this.name = site.getName();
        this.time = site.getDefaultTime();
        this.description = site.getDescription();
    }

    ItineraryItem(AbstractCity city) {
        this.name = city.getTitle();
        if (city.getTitle().contains("Free")) {
            time = 1.5;
        } else {
            time = 10.0;
        }
        this.description = city.getDescription();
    }

    String getName() {
        return name;
    }

    double getTime() {
        return time;
    }

    void addTime() {
        time += 1.5;
    }

    String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItineraryItem that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}

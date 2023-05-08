/**
 * ItineraryItem.java is a public class that represents an individual item on the Itinerary. Its methods are default
 * protected and so outside the plan package must be accessed through the ItineraryController. Each ItineraryItem
 * is a unifying abstraction of the AbstractCity and AbstractSite object concepts.
 */
package plan;

import destination.AbstractCity;
import destination.AbstractSite;

import java.util.Objects;

public class ItineraryItem {
    private final double FREE_TIME = 1.5;
    private String name;
    private double time;
    private String description;

    /**
     * 0-Arg constructor.
     */
    ItineraryItem() {}

    /**
     * 3-arg constructor accounting for the need to manually build an ItineraryItem with passed in parameters.
     * @param name String name of item.
     * @param time double value of time planned for item.
     * @param description String description of item.
     */
    ItineraryItem(String name, double time, String description) {
        this.name = name;
        this.time = time;
        this.description = description;
    }

    /**
     * 1-arg constructor for creating an ItineraryItem from an AbstractSite object.
     * @param site AbstractSite to build an ItineraryItem from.
     */
    ItineraryItem(AbstractSite site) {
        this.name = site.getName();
        this.time = site.getDefaultTime();
        this.description = site.getDescription();
    }

    /**
     * 2-arg constructor for creating an ItineraryItem from an AbstractCity object. This constructor distinguishes
     * ItineraryItems that are free time spent in an AbstractCity vs rest nights spent in a city.
     * @param city AbstractCity to build an ItineraryItem from.
     * @param time double time to initially assign to the item.
     */
    ItineraryItem(AbstractCity city, double time) {
        this.name = city.getTitle();
        if (city.getTitle().contains("Free")) {
            this.time = FREE_TIME;
        } else {
            this.time = time;
        }
        this.description = city.getDescription();
    }

    /**
     * Getter for ItineraryItem name.
     * @return String name.
     */
    String getName() {
        return name;
    }

    /**
     * Getter for ItineraryItem time.
     * @return double time.
     */
    double getTime() {
        return time;
    }

    /**
     * Adds more free time to the time instance variable. The amount of free time to add is controlled by the FREE_TIME
     * constant variable.
     */
    void addFreeTime() {
        time += FREE_TIME;
    }

    /**
     * Intellij generated equals method for comparison of ItineraryItems.
     * @param o Object to compare current object against.
     * @return true if objects are equal based on name and description variables.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItineraryItem that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    /**
     * Intellij generated Hashcode for comparison of ItineraryItems in HashSets.
     * @return int hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}

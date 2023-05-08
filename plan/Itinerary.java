/**
 * Itinerary.java is a default access class that contains all needed information for the itinerary and planning data
 * Model. It's methods are only accessible within the package and otherwise should be accessed through use of the
 * ItineraryController.
 */
package plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

class Itinerary {
    private final double FREE_TIME = 1.5;
    private ArrayList<LinkedHashSet<ItineraryItem>> allItineraryItems;
    private LinkedHashSet<ItineraryItem> dailyItineraryItems;
    private HashMap<Integer, Double> plannedHours;
    private String title;

    /**
     * 0-arg constructor
     */
    Itinerary() {}

    /**
     * 1-arg constructor
     * @param title String title of the Itinerary object.
     */
    Itinerary(String title) {
        this.title = title;
        dailyItineraryItems = new LinkedHashSet<>();
        allItineraryItems = new ArrayList<>();
    }

    /**
     * Setter for String instance variable title.
     * @param newTitle String value that will be set inside the instance variable title.
     */
    void setNewTitle(String newTitle) {
        title = newTitle;
    }

    /**
     * Adds an ItineraryItem to the dailyItineraryItems ArrayList. If the Itinerary stretches over more than one day, it
     * also correctly adds the updated dailyItineraryItems objects to the allItineraryItems variable.
     * @param itineraryItem item to add to the Itinerary.
     */
    void addItineraryItem(ItineraryItem itineraryItem) {
        dailyItineraryItems.add(itineraryItem);
        if (allItineraryItems.size() > 0) {
            allItineraryItems.remove(allItineraryItems.size() - 1);
        }

        allItineraryItems.add(dailyItineraryItems);
    }

    /**
     * Remove an ItineraryItem from the current dailyItineraryItems list. This method WILL NOT remove an ItineraryItem
     * that is scheduled on a different day than the current logical day.
     * @param itineraryItem item to remove from the Itinerary.
     */
    void removeItineraryItem(ItineraryItem itineraryItem) {
        dailyItineraryItems.remove(itineraryItem);
    }

    /**
     * Returns ItineraryItem object from the current dailyItineraryItems list based on the String variable itemName
     * passed into the method.
     * @param itemName String name of the ItineraryItem to get.
     * @return ItineraryItem with name itemName if one exists in current dailyItineraryItems list, otherwise return
     * null.
     */
    ItineraryItem getItineraryItem(String itemName) {
        if (dailyItineraryItems.size() > 0) {
            for (ItineraryItem item : dailyItineraryItems) {
                if (item.getName().equals(itemName)) {
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * Checks if an ItineraryItem object exists in the current day's list of ItineraryItems.
     * @param itemName String name of ItineraryItem to look for.
     * @return true if Itinerary contains an ItineraryItem with name itemName planned for the current day, otherwise
     * return false.
     */
    boolean hasItem(String itemName) {
        ItineraryItem item = getItineraryItem(itemName);
        return item != null;
    }

    /**
     * Adds a rest night to the Itinerary.
     * @param cityName String name of city to create an ItineraryItem from.
     * @param time double time to spend on the rest night.
     * @param description String description of the rest night.
     */
    void addItineraryRest(String cityName, double time, String description) {
        dailyItineraryItems.add(new ItineraryItem(cityName, time, description));  //create new ItineraryItem
        if (allItineraryItems.size() > 0) {
            allItineraryItems.remove(allItineraryItems.size() - 1);
        }
        allItineraryItems.add(dailyItineraryItems);
        dailyItineraryItems = new LinkedHashSet<>();  //create new dailyItineraryItems LinkedHashSet
        allItineraryItems.add(dailyItineraryItems);
    }

    /**
     * Removes a rest night from the Itinerary if it was the last night scheduled.
     * @param cityName String name of rest night location.
     */
    void removeItineraryRest(String cityName) {
        dailyItineraryItems = allItineraryItems.get(allItineraryItems.size() - 2);
        allItineraryItems.remove(allItineraryItems.size() - 1);
        for (ItineraryItem item : dailyItineraryItems) {
            if (item.getName().equals("Night in " + cityName)) {
                dailyItineraryItems.remove(item);
            }
        }
    }

    /**
     * Returns last rest night before the day currently being planned.
     * @return ItineraryItem representing the previous night's rest night if it exists, otherwise return a new, empty
     * ItineraryItem.
     */
    ItineraryItem getNightBefore() {
        if (allItineraryItems.size() > 1) {
            LinkedHashSet<ItineraryItem> set = allItineraryItems.get(allItineraryItems.size() - 2);
            return (ItineraryItem) set.toArray()[set.size() - 1];
        }

        return new ItineraryItem("", 0, "");
    }

    /**
     * Gets dailyItineraryItems instance variable.
     * @return LinkedHashSet dailyItineraryItems instance variable.
     */
    LinkedHashSet<ItineraryItem> getDailyItineraryItems() {
        return dailyItineraryItems;
    }

    /**
     * Gets allItineraryItems instance variable.
     * @return ArrayList of LinkedHashSets allItineraryItems variable.
     */
    ArrayList<LinkedHashSet<ItineraryItem>> getAllItineraryItems() {
        return allItineraryItems;
    }

    /**
     * Return FREE_TIME constant variable.
     * @return double FREE_TIME constant variable.
     */
    double getFREE_TIME() {
        return FREE_TIME;
    }

    /**
     * Clears the Itinerary by assigning new empty plannedHours, dailyItineraryItems, and allItineraryItems variables.
     * This method DOES NOT clear the title variable.
     */
    void reset() {
        dailyItineraryItems = new LinkedHashSet<>();
        allItineraryItems = new ArrayList<>();
        plannedHours = new HashMap<>();
    }

    /**
     * Sets plannedHours instance variable.
     * @param plannedHours HashMap variable to set in plannedHours.
     */
    void setPlannedHours(HashMap<Integer, Double> plannedHours) {
        this.plannedHours = plannedHours;
    }

    /**
     * Standard override of toString to print a nicely formatted Itinerary when the user attempts to save their
     * itinerary in a file.
     * @return String representation of the current Itinerary.
     */
    @Override
    public String toString() {
        int nightCount = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Current Schedule for ").append(title).append(":").append(System.lineSeparator());
        for (LinkedHashSet<ItineraryItem> itineraryItems : allItineraryItems) {
            if (itineraryItems.size() == 0) {
                break;
            }
            int count = 1;
            if (nightCount == plannedHours.size() + 1) {
                break;
            }
            sb.append("Day ").append(nightCount + 1).append("\t (").append(plannedHours.get(nightCount)).
                    append(" hours planned)").append(System.lineSeparator());
            for (ItineraryItem item : itineraryItems) {
                sb.append(count).append(". ").append(item.getName()).append(System.lineSeparator());
                sb.append("\t").append(item.getTime()).append(" hours").append(System.lineSeparator());
                count++;
                sb.append(System.lineSeparator());
            }
            nightCount++;
        }
        return sb.toString();
    }
}

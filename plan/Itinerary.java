package plan;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

class Itinerary {

    private LinkedHashSet<ItineraryItem> itineraryItems;
    private HashMap<Integer, Double> plannedHours;
    private String title;
    private double time;
    private final double FREETIME = 1.5;

    Itinerary() {}

    Itinerary(String title) {
        this.title = title;
        this.time = 0;
        itineraryItems = new LinkedHashSet<>();
    }

    Itinerary(String title, double time) {
        this.title = title;
        this.time = time;

    }

    void setNewTitle(String newTitle) {
        title = newTitle;
    }

    void addItineraryItem(ItineraryItem itineraryItem) {
        itineraryItems.add(itineraryItem);
        time += itineraryItem.getTime();
    }

    void removeItineraryItem(ItineraryItem itineraryItem) {
        time -= itineraryItem.getTime();
        itineraryItems.remove(itineraryItem);
    }

    ItineraryItem getItineraryItem(String itemName) {
        if (itineraryItems.size() > 0) {
            for (ItineraryItem item : itineraryItems) {
                if (item.getName().equals(itemName)) {
                    return item;
                }
            }
        }
        return null;
    }

    void addItineraryRest(String name, double time, String description) {
        itineraryItems.add(new ItineraryItem(name, time, description));
    }

    void removeItineraryRest(String name) {
        for (ItineraryItem item : itineraryItems) {
            if (item.getName().equals("Night in " + name)) {
                itineraryItems.remove(item);
            }
        }
    }
    HashSet<ItineraryItem> getItineraryItems() {
        return itineraryItems;
    }

    double getTime() {
        return time;
    }

    double getFREETIME() {
        return FREETIME;
    }

    void reset() {
        time = 0.0;
        itineraryItems = new LinkedHashSet<>();
    }

    void setPlannedHours(HashMap<Integer, Double> plannedHours) {
        this.plannedHours = plannedHours;
    }

    boolean hasItem(String itemName) {
        ItineraryItem item = getItineraryItem(itemName);

        return item != null;
    }

    @Override
    public String toString() {
        int count = 1;
        int dayCount = 1;
        StringBuilder sb = new StringBuilder();
        sb.append("Current Schedule for ").append(title).append(":").append(System.lineSeparator());
        sb.append("Day ").append(dayCount).append("\t (").append(plannedHours.get(dayCount)).append(" hours planned)").
                append(System.lineSeparator());
        for (ItineraryItem item : itineraryItems) {
            sb.append(count).append(". ").append(item.getName()).append(System.lineSeparator());
            sb.append("\t").append(item.getTime()).append(" hours").append(System.lineSeparator());
            if (item.getName().contains("Night in")) {
                dayCount++;
                sb.append(System.lineSeparator()).append("Day ").append(dayCount);
                count = 0;
            }
            count++;
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}

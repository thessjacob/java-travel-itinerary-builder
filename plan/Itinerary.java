package plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

class Itinerary {

    private ArrayList<LinkedHashSet<ItineraryItem>> allItineraryItems;
    private LinkedHashSet<ItineraryItem> dailyItineraryItems;
    private HashMap<Integer, Double> plannedHours;
    private String title;
    private double totalTime;
    private final double FREETIME = 1.5;

    Itinerary() {}

    Itinerary(String title) {
        this.title = title;
        this.totalTime = 0;
        dailyItineraryItems = new LinkedHashSet<>();
        allItineraryItems = new ArrayList<>();
    }

    Itinerary(String title, double totalTime) {
        this.title = title;
        this.totalTime = totalTime;

    }

    void setNewTitle(String newTitle) {
        title = newTitle;
    }

    void addItineraryItem(ItineraryItem itineraryItem) {
        dailyItineraryItems.add(itineraryItem);
        totalTime += itineraryItem.getTime();
        if (allItineraryItems.size() > 0) {
            allItineraryItems.remove(allItineraryItems.size() - 1);
        }

        allItineraryItems.add(dailyItineraryItems);
    }

    void removeItineraryItem(ItineraryItem itineraryItem) {
        dailyItineraryItems.remove(itineraryItem);
        totalTime -= itineraryItem.getTime();
    }

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

    void addItineraryRest(String name, double time, String description) {
        dailyItineraryItems.add(new ItineraryItem(name, time, description));
        if (allItineraryItems.size() > 0) {
            allItineraryItems.remove(allItineraryItems.size() - 1);
        }
        allItineraryItems.add(dailyItineraryItems);
        dailyItineraryItems = new LinkedHashSet<>();
        allItineraryItems.add(dailyItineraryItems);
    }

    void removeItineraryRest(String name) {
        dailyItineraryItems = allItineraryItems.get(allItineraryItems.size() - 2);
        allItineraryItems.remove(allItineraryItems.size() - 1);
        for (ItineraryItem item : dailyItineraryItems) {
            if (item.getName().equals("Night in " + name)) {
                dailyItineraryItems.remove(item);
            }
        }
    }

    ItineraryItem getNightBefore(String cityName) {
        if (allItineraryItems.size() > 1) {
            LinkedHashSet<ItineraryItem> set = allItineraryItems.get(allItineraryItems.size() - 2);
            return (ItineraryItem) set.toArray()[set.size() - 1];
        }

        return new ItineraryItem("", 0, "");
    }
    LinkedHashSet<ItineraryItem> getDailyItineraryItems() {
        return dailyItineraryItems;
    }

    ArrayList<LinkedHashSet<ItineraryItem>> getAllItineraryItems() {
        return allItineraryItems;
    }

    double getTotalTime() {
        return totalTime;
    }

    double getFREETIME() {
        return FREETIME;
    }

    void reset() {
        totalTime = 0.0;
        dailyItineraryItems = new LinkedHashSet<>();
        allItineraryItems = new ArrayList<>();
        plannedHours = new HashMap<>();
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
        int nightCount = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("Current Schedule for ").append(title).append(":").append(System.lineSeparator());
        for (LinkedHashSet<ItineraryItem> itineraryItems : allItineraryItems) {
            int count = 1;
            if (nightCount == plannedHours.size() + 1) {
                break;
            }
            sb.append("Day ").append(nightCount + 1).append("\t (").append(plannedHours.get(nightCount)).append(" hours planned)").
                    append(System.lineSeparator());
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

package plan;

import java.util.HashMap;
import java.util.HashSet;

enum SchedulingLogicController {

    INSTANCE;

    double totalTime;
    HashMap<Integer, Double> plannedHours;
    int nights;

    public void allocateDays(HashSet<ItineraryItem> itineraryItems) {
        plannedHours = new HashMap<>();
        double hours = 0.0;
        nights = 0;
        for (ItineraryItem item : itineraryItems) {
            if (item.getName().contains("Night in")) {
                nights++;
                totalTime += item.getTime();
                plannedHours.put(nights, hours + item.getTime());
                hours = 0.0;
            } else {
                hours += item.getTime();
            }
        }
    }

    public HashMap<Integer, Double> getPlannedHours() {
        return plannedHours;
    }

}

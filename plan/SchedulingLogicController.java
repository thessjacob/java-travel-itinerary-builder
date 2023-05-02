package plan;

import java.util.HashMap;
import java.util.HashSet;

enum SchedulingLogicController {

    INSTANCE;
    double dailyTime;
    double totalTime;
    HashMap<Integer, Double> plannedHours = new HashMap<>();
    int nights;
    private final double DAY_LENGTH = 24.0;
    int totalActivities = 0;

    public void allocateDays(HashSet<ItineraryItem> itineraryItems) {
        plannedHours = new HashMap<>();
        double hours = 0.0;
        nights = 0;
        for (ItineraryItem item : itineraryItems) {
            if (item.getName().contains("Night in")) {
                nights++;
                dailyTime += item.getTime();
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

    private void clearPlannedHours() {
        plannedHours = new HashMap<>();
    }

    private void clearNumberOfActivities() {
        totalActivities = 0;
    }

    public void addRestTime(double time) {
        totalActivities++;
        nights++;
        plannedHours.put(totalActivities, time);
        dailyTime = 0;
        totalTime += 24.0;
    }

    public boolean addActivityTime(double time, boolean newActivity) {
        if (newActivity) {
            if (checkTimeAvailable()) {
                totalActivities++;
                plannedHours.put(totalActivities, time);
                dailyTime += time;
                return true;
            }
        } else {
            if (checkTimeAvailable()) {
                plannedHours.put(totalActivities, plannedHours.get(totalActivities) + time);
                dailyTime += time;
                return true;
            }
        }
        return false;
    }

    public boolean checkTimeAvailable() {
        if (dailyTime > 12.0) {
            System.out.println("You need to leave time for sleep! Please add rest");
            return false;
        }
        return true;
    }

}

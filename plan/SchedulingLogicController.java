package plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

enum SchedulingLogicController {

    INSTANCE;
    double dailyTime;
    double totalTime;
    HashMap<Integer, Double> plannedHours = new HashMap<>();
    int nights;
    private final double DAY_LENGTH = 24.0;
    int totalActivities = 0;

    public void allocateDays(ArrayList<LinkedHashSet<ItineraryItem>> allItineraryItems) {
        plannedHours = new HashMap<>();
        for (LinkedHashSet<ItineraryItem> itineraryItems : allItineraryItems) {
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
    }

    public HashMap<Integer, Double> getPlannedHours() {
        return plannedHours;
    }

    public void clear() {
        clearPlannedHours();
        clearNumberOfActivities();
        clearTotalTime();
        clearTotalTime();
        clearNights();
    }
    private void clearPlannedHours() {
        plannedHours = new HashMap<>();
    }

    private void clearNumberOfActivities() {
        totalActivities = 0;
    }

    private void clearTotalTime() {
        totalTime = 0;
    }

    private void clearDailyTime() {
        dailyTime = 0;
    }

    private void clearNights() {
        nights = 0;
    }

    double getTotalTime() {
        return totalTime;
    }
    public void addRestTime(double time) {
        totalActivities++;
        nights++;
        plannedHours.put(totalActivities, time);
        totalTime += DAY_LENGTH - dailyTime;
        dailyTime = 0;
    }

    public boolean addActivityTime(double time, boolean newActivity) {
        if (newActivity) {
            if (checkTimeAvailable()) {
                totalActivities++;
                plannedHours.put(totalActivities, time);
                dailyTime += time;
                totalTime += time;
                return true;
            }
        } else {
            if (checkTimeAvailable()) {
                plannedHours.put(totalActivities, plannedHours.get(totalActivities) + time);
                dailyTime += time;
                totalTime += time;
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

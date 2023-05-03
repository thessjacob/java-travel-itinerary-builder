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
        nights = 0;
        for (LinkedHashSet<ItineraryItem> itineraryItems : allItineraryItems) {
            double hours = 0.0;
            for (ItineraryItem item : itineraryItems) {
                if (item.getName().contains("Night in")) {
                    dailyTime += item.getTime();
                    plannedHours.put(nights, hours);
                    hours = 0.0;
                    nights++;
                } else {
                    hours += item.getTime();
                    if (item == itineraryItems.toArray()[itineraryItems.size() - 1]) {
                        plannedHours.put(nights, hours);
                    }
                }
            }
        }
    }

    HashMap<Integer, Double> getPlannedHours() {
        return plannedHours;
    }

    void clear() {
        clearPlannedHours();
        clearNumberOfActivities();
        clearTotalTime();
        clearDailyTime();
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

    void addRestTime() {
        totalActivities++;
        plannedHours.put(nights, dailyTime);
        nights++;
        totalTime += DAY_LENGTH - dailyTime;
        dailyTime = 0;
    }

    void removeRestTime(double time) {

        totalActivities--;
        nights--;
        double tempTime = plannedHours.get(nights);
        plannedHours.remove(nights);
        totalTime -= time;
        plannedHours.put(nights, tempTime);
    }
    void removeTime(double time) {
        dailyTime -= time;
        totalTime -= time;
    }
    boolean addActivityTime(double time, boolean newActivity) {
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

    boolean checkTimeAvailable() {
        return !(dailyTime > 12.0);
    }

}

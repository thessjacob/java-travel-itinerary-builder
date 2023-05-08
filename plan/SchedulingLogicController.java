/**
 * SchedulingLogicController is a default access controller for the plan package. It is implemented as an enum style
 * singleton, so that all references to the ItineraryController return the same state. It contains the logic and
 * performs operations that ensure the Itinerary. It is interacted with mostly through getters, 0-argument public
 * methods that perform set tasks with no outside visibility, and through adding/removing amounts of time passed into
 * particular instance methods as double values.
 */

package plan;

import java.util.HashMap;
import java.util.LinkedHashSet;

enum SchedulingLogicController {
    INSTANCE;
    private final double DAY_LENGTH = 24.0;
    private final double MAX_DAILY_HOURS = 12.0;
    double dailyTime;
    double totalTime;
    HashMap<Integer, Double> plannedHours = new HashMap<>();
    int nights;
    int totalActivities = 0;

    /**
     * Takes an Itinerary object and records the hours planned per day in a hashmap. The day number (0-indexed) is the
     * key, with the value being the number of hours planned for that day.
     * @param itinerary Itinerary object to analyze.
     */
    public void allocateDays(Itinerary itinerary) {
        plannedHours = new HashMap<>();
        nights = 0;
        for (LinkedHashSet<ItineraryItem> itineraryItems : itinerary.getAllItineraryItems()) {
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

    /**
     * Return HashMap of planned hours in an itinerary.
     * @return HashMap variable plannedHours.
     */
    HashMap<Integer, Double> getPlannedHours() {
        return plannedHours;
    }

    /**
     * Clears the SchedulingLogicController and resets all state to default.
     */
    void clear() {
        clearPlannedHours();
        clearNumberOfActivities();
        clearTotalTime();
        clearDailyTime();
        clearNights();
    }

    /**
     * Clears the plannedHours HashMap by assigned a new, empty HashMap to instance variable plannedHours.
     */
    private void clearPlannedHours() {
        plannedHours = new HashMap<>();
    }

    /**
     * Sets the int totalActivities instance variable to 0.
     */
    private void clearNumberOfActivities() {
        totalActivities = 0;
    }

    /**
     * Sets the double instance variable totalTime to 0.
     */
    private void clearTotalTime() {
        totalTime = 0;
    }

    /**
     * Sets the double instance variable dailyTime to 0.
     */
    private void clearDailyTime() {
        dailyTime = 0;
    }

    /**
     * Clears the int instance variable nights to 0.
     */
    private void clearNights() {
        nights = 0;
    }

    /**
     * Getter for totalTime variable.
     * @return double value representing total time planned in the itinerary including rests.
     */
    double getTotalTime() {
        return totalTime;
    }

    /**
     * Alters instance variables for when an Itinerary adds a rest night. It must increment the total number of
     * activities (since resting is an activity), document the total dailyTime in the plannedHours HashMap, increment
     * nights variable by 1, round the totalTime variable to the next full day, and reset the dailyTime variable to 0.
     */
    void addRestTime() {
        totalActivities++;
        plannedHours.put(nights, dailyTime);
        nights++;
        totalTime += DAY_LENGTH - dailyTime;
        clearDailyTime();
    }

    /**
     * Alters instance variables for when an Itinerary removes a registered rest night. It must decrement the total
     * nubmer of activities, decrement the number of nights,
     * @param time double time to remove from totalTime.
     */
    void removeRestTime(double time) {
        totalActivities--;
        nights--;
        totalTime -= time;
        dailyTime = plannedHours.get(nights);
    }

    /**
     * Removes time from totalTime and dailyTime variables.
     * @param time double time to remove from dailyTime and totalTime.
     */
    void removeTime(double time) {
        dailyTime -= time;
        totalTime -= time;
    }

    /**
     * Adds time to plannedHours. First checks if the time should be added to an existing activity or to one that
     * already exists, then incrementing necessary values.
     * @param time double time to add to plannedTime and dailyTime.
     * @param newActivity boolean value for whether this time is part of a new activity or is addition to an existing
     *                    activity.
     * @return true if the time was successfully added, otherwise return false.
     */
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

    /**
     * Checks whether there is time in the day to add a new activity or additional free time. The threshold is greater
     * than the value set by the MAX_DAILY_HOURS final variable.
     * @return
     */
    boolean checkTimeAvailable() {
        return !(dailyTime > MAX_DAILY_HOURS);
    }

}

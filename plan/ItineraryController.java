/**
 * ItineraryController.java is a public controller for the program Model. It is implemented as an enum style singleton,
 * so that all references to the ItineraryController return the same state. It is a crucial intermediary for the
 * DataViewController, as only the ItineraryController can interact with the internal Itinerary object that represents
 * what a user has planned.
 */

package plan;

import destination.AbstractCity;
import destination.AbstractSite;
import gui.DataViewController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public enum ItineraryController {

    INSTANCE;
    private final DataViewController dvc = DataViewController.INSTANCE;
    private final SchedulingLogicController slc = SchedulingLogicController.INSTANCE;
    private Itinerary itinerary;
    public JList<String> list;


    /**
     * Inits a new itinerary object that will record the user's inputs. This method
     * @param title String name of the itinerary.
     */
    public void initItinerary(String title) {
        itinerary = new Itinerary(title);
        dvc.setPlanning(true);
    }

    /**
     * Resets the ItineraryController by clearing the JList text, resetting the itinerary, and
     * clearing the SchedulingLogicController.
     */
    public void clearController() {
        itinerary.reset();
        clearList();
        slc.clear();
        dvc.setPlanning(true);
    }

    /**
     * Returns JList object containing the itinerary information.
     * @return JList object containing the itinerary information.
     */
    public JList<String> getList() {
        return list;
    }

    /**
     * Clears the list inside the JList object, setting it to a new blank list.
     */
    public void clearList() {
        list.setListData(new String[0]);
    }

    /**
     * Add an ItineraryItem to the internal Itinerary object.
     * @param itemName String name of ItineraryItem to add.
     * @param type String name of type of item. Can be "AbstractCity" or "AbstractSite".
     */
    public void addItineraryItem(String itemName, String type) {
        //first, ensure there is enough time to add this object.
        boolean haveTime = slc.checkTimeAvailable();
        if (haveTime) {
            if (type.equals("AbstractSite")) {
                AbstractSite site = dvc.getCountry().getSite(itemName);
                slc.addActivityTime(site.getDefaultTime(), true);
                ItineraryItem itineraryItem = new ItineraryItem(site);
                if (!itinerary.hasItem(itemName)) {
                    itinerary.addItineraryItem(itineraryItem);
                    dvc.addTimeOnPanel();
                }
            } else if (type.equals("AbstractCity")) {
                AbstractCity city = dvc.getCountry().getCity(itemName);
                slc.addActivityTime(itinerary.getFREETIME(), true);
                ItineraryItem itineraryItem = new ItineraryItem(city, getRestTime());
                itinerary.addItineraryItem(itineraryItem);
                dvc.addTimeOnPanel();
            }
        } else {
            dvc.throwTimeWarning();
        }

    }

    /**
     * Calculates the amount of rest time available per day based on how much time is already planned.
     * @return double amount of rest time available.
     */
    private double getRestTime() {
        return 24 - (slc.getTotalTime() % 24);
    }

    /**
     * Adds additional time (representing additional free time) to an ItineraryItem that is a site.
     * @param itemName String name of the ItineraryItem.
     */
    public void addItineraryItemTimeSite(String itemName) {
        boolean canAdd = slc.addActivityTime(itinerary.getFREETIME(), false);
        if (canAdd) {
            itinerary.getItineraryItem(itemName).addTime();
            dvc.addTimeOnPanel();
        } else {
            dvc.throwTimeWarning();
        }
    }

    /**
     * Adds additional time (representing additional free time) to an ItineraryItem that is a city.
     * @param itemName String name of the ItineraryItem.
     */
    public void addItineraryItemTimeCity(String itemName) {
        boolean canAdd = slc.addActivityTime(itinerary.getFREETIME(), false);
        if (canAdd) {
            itinerary.getItineraryItem(itemName).addTime();
            dvc.addTimeOnPanel();
        } else {
            dvc.throwTimeWarning();
        }
    }

    /**
     * Removes an ItineraryItem from the Itinerary.
     * @param itemName String name of item to remove.
     * @param type String name of type of item. Can be "AbstractCity" or "AbstractSite".
     */
    public void removeItineraryItem(String itemName, String type) {
        ItineraryItem itineraryItem;
        if (type.equals("AbstractSite")) {
            itineraryItem = itinerary.getItineraryItem(itemName);
        } else {
            itineraryItem = itinerary.getItineraryItem(itemName + " Free Time");
        }

        itinerary.removeItineraryItem(itineraryItem);
        slc.removeTime(itineraryItem.getTime());
        dvc.addTimeOnPanel();
    }

    /**
     * Add rest to the Itinerary, representing one night in a location.
     * @param name name of the city to rest in.
     */
    public void addItineraryRest(String name) {
        double time = getRestTime();
        slc.addRestTime();
        itinerary.addItineraryRest("Night in " + name, time, "Rest up!");
        dvc.addTimeOnPanel();
    }

    /**
     * Remove rest from the itinerary, representing not spending a night in a location. This method is only able to
     * remove the last night scheduled. A night also cannot be removed until the day after the latest night is cleared
     * of activities.
     * @param name String name of the city rest to remove from the itinerary.
     */
    public void removeItineraryRest(String name) {
        if (itinerary.getDailyItineraryItems().size() == 0) {
            ItineraryItem item = itinerary.getNightBefore(name);
            slc.removeRestTime(item.getTime());
            itinerary.removeItineraryRest(name);
            dvc.addTimeOnPanel();
        }
    }

    /**
     * Checks if the Itinerary has an ItineraryItem based on the String itemName passed into the method. This method
     * only checks if an item is in the current day's itinerary. It will not check previous days.
     * @param itemName String name of ItineraryItem.
     * @return true if the Itinerary has an item in today's schedule, otherwise return false.
     */
    public boolean hasItem(String itemName) {
        if (! (itinerary.getDailyItineraryItems() == null)) {
            for (ItineraryItem item : itinerary.getDailyItineraryItems()) {
                if (item.getName().equals(itemName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the previous night is a night in a city determined by String itemName passed into the method.
     * @param itemName String name of place to check on whether it is the previous night's place of rest.
     * @return true if previous night's rest was in the location represented by String itemName, otherwise return false.=
     */
    public boolean hasNightIn(String itemName) {
        if (itinerary.getAllItineraryItems().size() > 1) {
            ItineraryItem item = itinerary.getNightBefore(itemName);
            return item.getName().equals(itemName);
        }
        return false;
    }

    /**
     * Return internal Itinerary object.
     * @return Itinerary object.
     */
    public Itinerary getItinerary() {
        return itinerary;
    }

    /**
     * Resets the ItineraryHours and then returns the String form of the Itinerary.
     * @return String representation of the internal Itinerary object.
     */
    public String getItineraryString() {
        setPlannedHours();
        return itinerary.toString();
    }

    /**
     * This method ensures that the internal Itinerary has the most up-to-date planned hours. It is used before printing
     * an Itinerary.
     */
    void setPlannedHours() {
        slc.allocateDays(itinerary.getAllItineraryItems());
        itinerary.setPlannedHours(slc.getPlannedHours());
    }

    /**
     * Returns an ArrayList of LinkedSets of ItineraryItems. This ArrayList represents all planned ItineraryItems, with
     * each set representing a single day and its accompanying night, if one is planned.
     * @return ArrayList representing all ItineraryItems.
     */
    public ArrayList<LinkedHashSet<ItineraryItem>> getAllItineraryItems() {
        return itinerary.getAllItineraryItems();
    }

    /**
     * Sets new title for the Itinerary.
     * @param newTitle String title for the Itinerary.
     */
    public void setItineraryTitle(String newTitle) {
        itinerary.setNewTitle(newTitle);
    }

    /**
     * Return name of an ItineraryItem. ItineraryItems' getters cannot be accessed from outside the plan package.
     * @param item ItineraryItem to get the name of.
     * @return String name of the ItineraryItem passed into the method.
     */
    public String getItineraryName(ItineraryItem item) {
        return item.getName();
    }

    /**
     * Return planned time for an ItineraryItem. ItineraryItems' getters cannot be accessed from outside the plan
     * package.
     * @param item ItineraryItem to get the name of.
     * @return double planned time for the ItineraryItem passed into the method.
     */
    public double getItineraryTime(ItineraryItem item) {
        return item.getTime();
    }

    /**
     * Clears the SchedulingLogicController. The SLC cannot be accessed from outside the plan package.
     */
    public void resetSLC() {
        slc.clear();
    }

    /**
     * Gets total amount of time planned in the Itinerary.
     * @return double representing total amount of time planned.
     */
    public double getTotalTime() {
        return slc.getTotalTime();
    }
}

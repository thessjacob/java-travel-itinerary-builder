package plan;

import destination.AbstractCity;
import destination.AbstractSite;
import gui.DataViewController;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public enum ItineraryController {

    INSTANCE;
    private DataViewController dvc;
    private Itinerary itinerary;
    public JList<String> list;

    final SchedulingLogicController slc = SchedulingLogicController.INSTANCE;

    public void initItinerary(String title) {
        itinerary = new Itinerary(title);
        dvc = DataViewController.INSTANCE;
        dvc.setPlanning(true);
    }

    public void clearController() {
        itinerary.reset();
        clearList();
        slc.clear();
        dvc.setPlanning(true);
    }

    public JList<String> getList() {
        return list;
    }
    public void clearList() {
        list.setListData(new String[0]);
    }

    public boolean addItineraryItem(String itemName, String type) {
        if (type.equals("AbstractSite")) {
            AbstractSite site = dvc.getCountry().getSite(itemName);
            slc.addActivityTime(site.getDefaultTime(), true);
            ItineraryItem itineraryItem = new ItineraryItem(site);
            if (!itinerary.hasItem(itemName)) {
                itinerary.addItineraryItem(itineraryItem);
                dvc.addTimeOnPanel(site.getDefaultTime());
                return true;
            } else {
                return false;
            }
        } else if (type.equals("AbstractCity")) {
            AbstractCity city = dvc.getCountry().getCity(itemName);
            slc.addActivityTime(itinerary.getFREETIME(), true);
            ItineraryItem itineraryItem = new ItineraryItem(city, getRestTime());
            itinerary.addItineraryItem(itineraryItem);
            dvc.addTimeOnPanel(itinerary.getFREETIME());
            return true;
        }
        return false;
    }

    private double getRestTime() {
        return 24 - (slc.getTotalTime() % 24);
    }

    public void addItineraryItemTimeSite(String itemName) {
        boolean canAdd = slc.addActivityTime(itinerary.getFREETIME(), false);
        if (canAdd) {
            itinerary.getItineraryItem(itemName).addTime();
            dvc.addTimeOnPanel(itinerary.getFREETIME());
        } else {
            dvc.throwTimeWarning();
        }
    }

    public void addItineraryItemTimeCity(String itemName) {
        boolean canAdd = slc.addActivityTime(itinerary.getFREETIME(), false);
        if (canAdd) {
            itinerary.getItineraryItem(itemName).addTime();
            dvc.addTimeOnPanel(itinerary.getFREETIME());
        } else {
            dvc.throwTimeWarning();
        }
    }

    public boolean removeItineraryItem(String itemName, String type) {
        ItineraryItem itineraryItem;
        if (type.equals("AbstractSite")) {
            itineraryItem = itinerary.getItineraryItem(itemName);
        } else {
            itineraryItem = itinerary.getItineraryItem(itemName + " Free Time");
        }

        itinerary.removeItineraryItem(itineraryItem);
        dvc.addTimeOnPanel(-itineraryItem.getTime());
        return true;
    }

    public void addItineraryRest(String name) {
        double time = getRestTime();
        slc.addRestTime(time);
        itinerary.addItineraryRest("Night in " + name, time, "Rest up!");
        dvc.addTimeOnPanel(getRestTime());
    }

    public void removeItineraryRest(String name) {
        itinerary.removeItineraryRest(name);
        dvc.addTimeOnPanel(-10.0);
    }

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

    public Itinerary getItinerary() {
        return itinerary;
    }

    public String getItineraryString() {
        setPlannedHours();
        return itinerary.toString();
    }

    public LinkedHashSet<ItineraryItem> getDailyItineraryItems() {
        return itinerary.getDailyItineraryItems();
    }

    public ArrayList<LinkedHashSet<ItineraryItem>> getAllItineraryItems() {
        return itinerary.getAllItineraryItems();
    }

    void setPlannedHours() {
        slc.allocateDays(itinerary.getAllItineraryItems());
        itinerary.setPlannedHours(slc.getPlannedHours());
    }

    public void setItineraryTitle(String newTitle) {
        itinerary.setNewTitle(newTitle);
    }

    public String getItineraryName(ItineraryItem item) {
        return item.getName();
    }

    public double getItineraryTime(ItineraryItem item) {
        return item.getTime();
    }

    public void resetSLC() {
        slc.clear();
    }

    public double getTotalTime() {
        return slc.getTotalTime();
    }
}

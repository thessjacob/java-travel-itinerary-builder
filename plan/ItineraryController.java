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

    public void addItineraryItem(String itemName, String type) {
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

    private double getRestTime() {
        return 24 - (slc.getTotalTime() % 24);
    }

    public void addItineraryItemTimeSite(String itemName) {
        boolean canAdd = slc.addActivityTime(itinerary.getFREETIME(), false);
        if (canAdd) {
            itinerary.getItineraryItem(itemName).addTime();
            dvc.addTimeOnPanel();
        } else {
            dvc.throwTimeWarning();
        }
    }

    public void addItineraryItemTimeCity(String itemName) {
        boolean canAdd = slc.addActivityTime(itinerary.getFREETIME(), false);
        if (canAdd) {
            itinerary.getItineraryItem(itemName).addTime();
            dvc.addTimeOnPanel();
        } else {
            dvc.throwTimeWarning();
        }
    }

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

    public void addItineraryRest(String name) {
        double time = getRestTime();
        slc.addRestTime();
        itinerary.addItineraryRest("Night in " + name, time, "Rest up!");
        dvc.addTimeOnPanel();
    }

    public void removeItineraryRest(String name) {
        if (itinerary.getDailyItineraryItems().size() == 0) {
            ItineraryItem item = itinerary.getNightBefore(name);
            slc.removeRestTime(item.getTime());
            itinerary.removeItineraryRest(name);
            dvc.addTimeOnPanel();
        }

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

    public boolean hasNightIn(String itemName) {
        if (itinerary.getAllItineraryItems().size() > 1) {
            ItineraryItem item = itinerary.getNightBefore(itemName);
            return item.getName().equals(itemName);
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

    void setPlannedHours() {
        slc.allocateDays(itinerary.getAllItineraryItems());
        itinerary.setPlannedHours(slc.getPlannedHours());
    }

    public LinkedHashSet<ItineraryItem> getDailyItineraryItems() {
        return itinerary.getDailyItineraryItems();
    }

    public ArrayList<LinkedHashSet<ItineraryItem>> getAllItineraryItems() {
        return itinerary.getAllItineraryItems();
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

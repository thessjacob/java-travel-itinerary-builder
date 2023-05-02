package plan;

import destination.AbstractCity;
import destination.AbstractSite;
import gui.DataViewController;

import javax.swing.*;
import java.util.HashSet;

public enum ItineraryController {

    INSTANCE;
    private DataViewController dvc;
    private Itinerary itinerary;
    public JList<String> list;

    final SchedulingLogicController sc = SchedulingLogicController.INSTANCE;

    public void initItinerary(String title) {
        itinerary = new Itinerary(title);
        dvc = DataViewController.INSTANCE;
        dvc.setPlanning(true);
    }

    public void clearController() {
        itinerary.reset();
        clearList();
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
            ItineraryItem itineraryItem = new ItineraryItem(site);
            boolean canAdd = hasEnoughTime(site);
            if (canAdd && !itinerary.hasItem(itemName)) {
                itinerary.addItineraryItem(itineraryItem);
                dvc.refreshItineraryPanel(site.getDefaultTime());
                return true;
            } else {
                return false;
            }
        } else if (type.equals("AbstractCity")) {
            AbstractCity city = dvc.getCountry().getCity(itemName);
            ItineraryItem itineraryItem = new ItineraryItem(city);
            itinerary.addItineraryItem(itineraryItem);
            dvc.refreshItineraryPanel(itinerary.getFREETIME());
            return true;
        }
        return false;
    }

    public void addItineraryItemTimeSite(String itemName) {
        double newTime = itinerary.getTime() + itinerary.getFREETIME();
        if (newTime < 12.0) {
            itinerary.getItineraryItem(itemName).addTime();
            dvc.refreshItineraryPanel(itinerary.getFREETIME());
        }
    }

    public void addItineraryItemTimeCity(String itemName) {
        itinerary.getItineraryItem(itemName).addTime();
        dvc.refreshItineraryPanel(itinerary.getFREETIME());
    }

    private boolean hasEnoughTime(AbstractSite site) {
        double adjustedTime = itinerary.getTime() + site.getDefaultTime();
        return !(adjustedTime > 12.0);
    }

    public boolean removeItineraryItem(String itemName, String type) {
        ItineraryItem itineraryItem;
        if (type.equals("AbstractSite")) {
            itineraryItem = itinerary.getItineraryItem(itemName);
        } else {
            itineraryItem = itinerary.getItineraryItem(itemName + " Free Time");
        }

        itinerary.removeItineraryItem(itineraryItem);
        dvc.refreshItineraryPanel(-itineraryItem.getTime());
        return true;
    }

    public void addItineraryRest(String name) {
        itinerary.addItineraryRest("Night in " + name, 10.0, "Rest up!");
        dvc.refreshItineraryPanel(10.0);
    }

    public void removeItineraryRest(String name) {
        itinerary.removeItineraryRest(name);
        dvc.refreshItineraryPanel(-10.0);
    }

    public boolean hasItem(String itemName) {
        if (! (getItinerary().getItineraryItems() == null)) {
            for (ItineraryItem item : getItinerary().getItineraryItems()) {
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

    public HashSet<ItineraryItem> getItineraryItems() {
        return itinerary.getItineraryItems();
    }

    void setPlannedHours() {
        sc.allocateDays(getItineraryItems());
        itinerary.setPlannedHours(sc.getPlannedHours());
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
}

/**
 * ButtonRevalidator.java exists as an extraction of code from the CityButton and SiteButton classes that is 100% shared.
 */
package gui;

import plan.ItineraryController;
import plan.ItineraryItem;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ButtonRevalidator {
    private final static  ItineraryController ic = ItineraryController.INSTANCE;

    /**
     * Adds or Removes an ItineraryItem from the Itinerary based on what is passed into the method. It then calls a
     * revalidate method to refresh the View.
     * @param itemName String name of item to add.
     * @param add boolean true if operation is add, false if operation is remove.
     * @param type String type of item. Type can be AbstractCity or AbstractSite.
     */
    static void itineraryItemOperation(String itemName, boolean add, String type) {
        if (add) {
            ic.addItineraryItem(itemName, type);
        } else {
            ic.removeItineraryItem(itemName, type);
        }
        revalidation();
    }

    /**
     * Refreshes the JList data in the ItineraryPanel display. This method is called whenever any change is made by
     * adding or removing time on the CityButtons or SiteButtons.
     */
    static void  revalidation() {
        //Create the new JList data
        int count = 0;
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<LinkedHashSet<ItineraryItem>> allItems = ic.getAllItineraryItems();
        for (LinkedHashSet<ItineraryItem> dailyItems : allItems) {
            for (ItineraryItem item : dailyItems) {
                lines.add(ic.getItineraryName(item) + " (" + ic.getItineraryTime(item) + " hours)");
                count++;
            }
        }

        //Set and revalidate
        ic.getList().setListData(lines.toArray(new String[count]));
        ic.getList().revalidate();
    }
}

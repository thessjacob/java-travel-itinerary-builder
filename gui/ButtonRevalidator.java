/**
 * ButtonRevalidator.java exists to extract code from the CityButton and SiteButton classes that is 100% shared.
 */
package gui;

import plan.ItineraryController;
import plan.ItineraryItem;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ButtonRevalidator {
    private final static  ItineraryController ic = ItineraryController.INSTANCE;

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

package gui;


import java.awt.*;

public class ItineraryWindow extends CountryWindow {

    ItineraryWindow() {
        super();
        this.add(new ItineraryPanel(), BorderLayout.EAST);
    }
}

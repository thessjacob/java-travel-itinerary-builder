package gui;


import java.awt.*;

class ItineraryWindow extends CountryWindow {

    ItineraryWindow() {
        super();
        this.add(new ItineraryPanel(), BorderLayout.EAST);
    }
}

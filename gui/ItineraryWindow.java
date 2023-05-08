/**
 * ItineraryWindow.java is an extension of CountryWindow that adds an ItineraryPanel on the right side of the
 * CountryWindow Jframe.
 */
package gui;

import java.awt.*;

class ItineraryWindow extends CountryWindow {
    ItineraryWindow() {
        super();
        this.add(new ItineraryPanel(), BorderLayout.EAST);
    }
}

/**
 * CountryWindow.java is the base class for displaying a country and allowing users to select options from it.
 */
package gui;

import destination.Country;
import destination.SuperRegion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class CountryWindow extends JFrame {
    private final DataViewController dvc = DataViewController.INSTANCE;
    private final Country country = dvc.getCountry();
    private final int numOfSuperRegions = dvc.getNumOfSuperRegions();
    protected static String superRegionName = "";
    protected final JLabel centerLabel;
    protected static boolean displayingSuperRegion = false;

    /**
     * 0-arg constructor.
     */
    CountryWindow() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //When window is exited, return to MainOptionsWindow.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                superRegionName = "";
                WindowController.showMainOptionsWindow();
            }
        });
        setLayout(new BorderLayout());
        setSize(1000, 700); //arbitrary size.
        setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon(country.getBaseMapImage());
        centerLabel = new JLabel(img, JLabel.CENTER);  //CenterLabel holds the map images.
        add(centerLabel, BorderLayout.CENTER);
        JPanel leftPanel = new JPanel(new GridBagLayout()); //leftPanel will hold SuperRegion buttons.
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        //populate leftPanel
        if (leftPanel.getComponents().length < numOfSuperRegions) {
            for (SuperRegion superRegion : country.getSuperRegions()) {
                leftPanel.add(new SuperRegionButton(superRegion.getName(), leftPanel, this), c);
            }
        }
        c.weighty = .25;
        //add left panel to the window.
        leftPanel.add(new JPanel(), c);
        add(leftPanel, BorderLayout.WEST);
        setVisible(true);
    }
}

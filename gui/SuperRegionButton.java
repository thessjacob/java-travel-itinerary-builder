/**
 * SuperRegion.java contains the JButton extension that modifies the view according to what SuperRegion button the user
 * presses.
 */
package gui;

import destination.AbstractCity;
import destination.AbstractSite;
import destination.SuperRegion;
import destination.Country;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SuperRegionButton extends JButton {
    private final DataViewController dvc = DataViewController.INSTANCE;
    private final SuperRegion superRegion;
    private final int numberOfSuperRegions = dvc.getNumOfSuperRegions();
    private final Country country = dvc.getCountry();
    private final JPanel leftPanel;
    private final CountryWindow countryWindow;

    /**
     * 0-arg constructor. Should not be used.
     */
    SuperRegionButton() {
        this("", new JPanel(), new CountryWindow());
    }

    /**
     * 3-arg constructor.
     * @param superRegionName String name to display on the button.
     * @param leftPanel JPanel the button has as a parent.
     * @param countryWindow CountryWindow object current open.
     */
    SuperRegionButton(String superRegionName, JPanel leftPanel, CountryWindow countryWindow) {
        this.superRegion = country.getSuperRegion(superRegionName);
        this.leftPanel = leftPanel;
        this.countryWindow = countryWindow;
        setText(superRegion.getName());
        setSize(100, 50);
        addActionListener(new ClickListener());
        addActionListener(new ImgListener());
        setVisible(true);
    }

    /**
     * This extended ActionListener inner class listens for the user to click on a SuperRegion button. It then adjusts
     * the view accordingly. If a SuperRegion button is clicked when it is not displaying its SuperRegion, that region
     * will then be shown, including generated CityButtons and SiteButtons. If a SuperRegionButton is clicked when it
     * is already being displayed, all child buttons will be removed and the base map image for the country will be
     * restored to the center of the CountryWindow.
     */
    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean changed = false;
            //removes components if the clicked when superregion is already being shown
            if (CountryWindow.displayingSuperRegion && CountryWindow.superRegionName.equals(e.getActionCommand())) {
                removeComponents();
                CountryWindow.displayingSuperRegion = false;
                changed = true;
            }

            //removes components and then builds a new set of components for display
            if (!CountryWindow.superRegionName.equals(e.getActionCommand())) {
                removeComponents();
                CountryWindow.displayingSuperRegion = true;
                CountryWindow.superRegionName = e.getActionCommand();
                GridBagConstraints c = new GridBagConstraints();
                c.weightx = 1;
                c.weighty = .5;
                c.gridwidth = GridBagConstraints.REMAINDER;
                c.fill = HORIZONTAL;
                c.insets = new Insets(5, 15, 5, 15);

                //Add CityButtons representing AbstractCities.
                leftPanel.add(new JLabel("Places to Stay"), c);
                for (AbstractCity city : superRegion.getCities()) {
                    leftPanel.add(new CityButton(city.getName()), c);
                }

                //c.weighty = .0;

                //Add SiteButtons representing AbstractButtons.
                leftPanel.add(new JLabel("Sites to visit"), c);
                for (AbstractSite site : superRegion.getSites()) {
                    leftPanel.add(new SiteButton(site.getName()), c);
                }

                c.weighty = .75;

                //Add the new panel of buttons.
                leftPanel.add(new JPanel(), c);
            }

            if (changed) {
                CountryWindow.superRegionName = "";
            }

            leftPanel.repaint();
            leftPanel.getParent().revalidate();
        }

        /**
         * Remove unwanted components on the leftPanel. It will not remove the base SuperRegionButton components, only
         * additional added components.
         */
        private void removeComponents() {
            while (leftPanel.getComponentCount() > numberOfSuperRegions + 1) {
                leftPanel.remove(leftPanel.getComponentCount() - 1);
            }
        }
    }

    /**
     * This extended ActionListener inner class listens for a click and decides whether to display the Country map image
     * or the specfic SuperRegion image.
     */
    private class ImgListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!CountryWindow.superRegionName.equals(e.getActionCommand())) {
                countryWindow.centerLabel.setIcon(new ImageIcon(superRegion.getMapImageFile()));
            } else {
                countryWindow.centerLabel.setIcon(new ImageIcon(country.getBaseMapImage()));
            }
            countryWindow.repaint();
            countryWindow.revalidate();
        }
    }
}

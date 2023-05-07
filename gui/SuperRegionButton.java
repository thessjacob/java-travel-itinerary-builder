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

    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean changed = false;
            if (CountryWindow.displayingSuperRegion && CountryWindow.superRegionName.equals(e.getActionCommand())) {
                removeComponents();
                CountryWindow.displayingSuperRegion = false;
                changed = true;
            }

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

                leftPanel.add(new JLabel("Places to Stay"), c);
                for (AbstractCity city : superRegion.getCities()) {
                    leftPanel.add(new CityButton(city.getName()), c);
                }

                c.weighty = 0;
                leftPanel.add(new JLabel("Sites to visit"), c);
                for (AbstractSite site : superRegion.getSites()) {
                    leftPanel.add(new SiteButton(site.getName()), c);
                }

                c.weighty = .75;
                leftPanel.add(new JPanel(), c);
            }

            if (changed) {
                CountryWindow.superRegionName = "";
            }

            leftPanel.repaint();
            leftPanel.getParent().revalidate();
        }

        private void removeComponents() {
            while (leftPanel.getComponentCount() > numberOfSuperRegions + 1) {
                leftPanel.remove(leftPanel.getComponentCount() - 1);
            }
        }
    }

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

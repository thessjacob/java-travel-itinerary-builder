/**
 * SiteButton.java is an extension of the JButton Swing component that represents a clickable button that allows a user
 * to select an AbstractSite.
 */
package gui;

import plan.ItineraryController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SiteButton extends JButton {
    private final String siteName;
    private final SiteButton siteButton;
    private final DataViewController dvc = DataViewController.INSTANCE;
    private final ItineraryController ic = ItineraryController.INSTANCE;

    /**
     * 0-arg constructor. Should not be used.
     */
    SiteButton() {
        this("");
    }

    /**
     * 1-arg constructor.
     * @param siteName String name of AbstractSite to display.
     */
    SiteButton(String siteName) {
        super(siteName);
        this.siteName = siteName;
        this.siteButton = this;
        if (!dvc.isPlanning()) {    //if not planning, program is displaying CountryWindow
            addActionListener(new ActionListener() {
                //This action listener shows an informational pop-up.
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextArea textArea = new JTextArea(dvc.getSiteDescription(siteName));
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(600, 450)); //arbitrary dimensions.
                    ImageIcon icon = new ImageIcon(dvc.getSiteImageURL(siteName));
                    JOptionPane.showMessageDialog(siteButton, scrollPane, siteName + " Info",
                            JOptionPane.INFORMATION_MESSAGE, icon);
                }
            });
        } else {                    //if planning, program is displaying ItineraryWindow
            addActionListener(new ActionListener() {
                //This action listener shows a pop-up that users can interact with to create an itinerary.
                @Override
                public void actionPerformed(ActionEvent e) {
                    JPanel selectionPanel = new JPanel();
                    selectionPanel.setLayout(new GridBagLayout());
                    GridBagConstraints c = new GridBagConstraints();
                    c.gridwidth = GridBagConstraints.REMAINDER;
                    c.weightx = 1;
                    c.fill = HORIZONTAL;
                    JLabel j0 = new JLabel(siteName);
                    JLabel j1 = new JLabel("Closest place to stay: " + dvc.getSiteNearestCityName(siteName));
                    JLabel j2 = new JLabel("Typical time to visit this site: " + dvc.getSiteDefaultTime(siteName) +
                            " hours.");
                    selectionPanel.add(j0, c);
                    selectionPanel.add(j1, c);
                    selectionPanel.add(j2, c);

                    //Now add the 3 buttons we need. Two adds, one remove.
                    //First
                    JButton addButton = new JButton("Add");
                    addButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!ic.hasItem(siteName)) {
                                ButtonRevalidator.itineraryItemOperation(siteName, true, "AbstractSite");
                            }
                        }
                    });
                    //Second
                    JButton freeButton = new JButton("+Free Time");
                    freeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (ic.hasItem(siteName)) {
                                ic.addItineraryItemTimeSite(siteName);
                                ButtonRevalidator.revalidation();
                            }
                        }
                    });
                    //Third
                    JButton removeButton = new JButton("Remove");
                    removeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (ic.hasItem(siteName)) {
                                ButtonRevalidator.itineraryItemOperation(siteName, false, "AbstractSite");
                            }
                        }
                    });

                    //Construct Pop-up Window
                    c.gridwidth = GridBagConstraints.HORIZONTAL;
                    c.gridx = selectionPanel.getComponentCount() + 1;
                    c.gridy = selectionPanel.getComponentCount() + 1;
                    selectionPanel.add(addButton, c);
                    c.gridy += 1;
                    selectionPanel.add(freeButton, c);
                    c.gridy += 1;
                    selectionPanel.add(removeButton, c);
                    selectionPanel.setPreferredSize(new Dimension(600, 450)); //again, arbitrary dimensions
                    ImageIcon icon = new ImageIcon(dvc.getSiteImageURL(siteName));
                    JOptionPane.showMessageDialog(siteButton, selectionPanel, siteName, JOptionPane.INFORMATION_MESSAGE, icon);
                }
            });
        }
    }
}

package gui;

import plan.ItineraryController;
import plan.ItineraryItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class SiteButton extends JButton {
    private final String siteName;
    private final SiteButton siteButton;
    private final DataViewController dvc = DataViewController.INSTANCE;
    private final ItineraryController ic = ItineraryController.INSTANCE;


    SiteButton(String siteName) {
        super(siteName);
        this.siteName = siteName;
        this.siteButton = this;
        if (!dvc.isPlanning()) {
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextArea textArea = new JTextArea(dvc.getSiteDescription(siteName));
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(600, 450));
                    ImageIcon icon = new ImageIcon(dvc.getSiteImageURL(siteName));
                    JOptionPane.showMessageDialog(siteButton, scrollPane, siteName + " Info",
                            JOptionPane.INFORMATION_MESSAGE, icon);
                }
            });
        } else {
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JPanel selectionPanel = new JPanel();
                    selectionPanel.setLayout(new GridBagLayout());
                    GridBagConstraints c = new GridBagConstraints();
                    c.gridwidth = GridBagConstraints.REMAINDER;
                    c.weightx = 1;
                    c.fill = HORIZONTAL;
                    JLabel j0 = new JLabel(dvc.getSiteName(siteName));
                    JLabel j1 = new JLabel("Closest place to stay: " + dvc.getSiteNearestCityName(siteName));
                    JLabel j2 = new JLabel("Typical time to visit this site: " + dvc.getSiteDefaultTime(siteName) +
                            " hours.");

                    selectionPanel.add(j0, c);
                    selectionPanel.add(j1, c);
                    selectionPanel.add(j2, c);

                    JButton addButton = new JButton("Add");
                    addButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!ic.hasItem(siteName)) {
                                itineraryItemOperation(true, siteButton);
                            }
                        }
                    });
                    JButton freeButton = new JButton("+Free Time");
                    freeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (ic.hasItem(siteName)) {
                                ic.addItineraryItemTimeSite(siteName);
                                revalidation();
                            }
                        }
                    });
                    JButton removeButton = new JButton("Remove");
                    removeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (ic.hasItem(siteName)) {
                                itineraryItemOperation(false, siteButton);
                            }
                        }
                    });

                    //c.gridwidth = GridBagConstraints.RELATIVE;
                    c.gridwidth = GridBagConstraints.HORIZONTAL;
                    c.gridx = selectionPanel.getComponentCount() + 1;
                    c.gridy = selectionPanel.getComponentCount() + 1;
                    selectionPanel.add(addButton, c);
                    c.gridy += 1;
                    selectionPanel.add(freeButton, c);
                    c.gridy += 1;
                    selectionPanel.add(removeButton, c);
                    selectionPanel.setPreferredSize(new Dimension(600, 450));
                    ImageIcon icon = new ImageIcon(dvc.getSiteImageURL(siteName));
                    JOptionPane.showMessageDialog(siteButton, selectionPanel, siteName, JOptionPane.INFORMATION_MESSAGE, icon);
                }
            });
        }
    }

    private void itineraryItemOperation(boolean add, JButton button) {
        if (add) {
            ic.addItineraryItem(siteName, "AbstractSite");
        } else {
            ic.removeItineraryItem(siteName, "AbstractSite");
        }
        revalidation();
    }

    private void revalidation() {
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
        ic.list.setListData(lines.toArray(new String[count]));
        ic.list.revalidate();
    }
}

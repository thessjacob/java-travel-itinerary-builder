package gui;

import destination.AbstractCity;
import plan.ItineraryController;
import plan.ItineraryItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

class CityButton extends JButton {
    private final DataViewController dvc = DataViewController.INSTANCE;
    private final ItineraryController ic = ItineraryController.INSTANCE;
    private final String cityName;
    private final CityButton cityButton;

    CityButton(String cityName) {
        super(cityName);
        this.cityName = cityName;
        this.cityButton = this;
        if (!dvc.isPlanning()) {
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextArea textArea = new JTextArea(dvc.getCityDescription(cityName));
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(600, 450));
                    ImageIcon icon = new ImageIcon(dvc.getCityImageURL(cityName));
                    JOptionPane.showMessageDialog(cityButton, scrollPane, cityName + " Info",
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
                    JLabel j0 = new JLabel(dvc.getCityName(cityName));
                    selectionPanel.add(j0, c);

                    JButton freeButton = new JButton("Spend Free Time");
                    freeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!ic.hasItem(cityName + " Free Time")) {
                                itineraryItemOperation(true, cityButton);
                            } else {
                                ic.addItineraryItemTimeCity(cityName + " Free Time");
                            }
                            revalidation();
                        }
                    });
                    JButton stayButton = new JButton("Spend the night");
                    stayButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!ic.hasItem("Night in " + cityName)) {
                                ic.addItineraryRest(cityName);
                                revalidation();
                            }
                        }
                    });
                    JButton removeButton = new JButton("Remove Free Time");
                    removeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (ic.hasItem(cityName + " Free Time")) {
                                itineraryItemOperation(false, cityButton);
                            }
                        }
                    });
                    JButton removeButton2 = new JButton("Remove Night");
                    removeButton2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (ic.hasItem("Night in " + cityName)) {
                                ic.removeItineraryRest(cityName);
                                revalidation();
                            }
                        }
                    });

                    //c.gridwidth = GridBagConstraints.RELATIVE;
                    c.gridwidth = GridBagConstraints.HORIZONTAL;
                    c.gridx = selectionPanel.getComponentCount() + 1;
                    c.gridy = selectionPanel.getComponentCount() + 1;
                    selectionPanel.add(freeButton, c);
                    c.gridy += 1;
                    selectionPanel.add(stayButton, c);
                    c.gridy += 1;
                    selectionPanel.add(removeButton, c);
                    c.gridy += 1;
                    selectionPanel.add(removeButton2, c);
                    selectionPanel.setPreferredSize(new Dimension(600, 450));
                    ImageIcon icon = new ImageIcon(dvc.getCityImageURL(cityName));
                    JOptionPane.showMessageDialog(cityButton, selectionPanel, cityName, JOptionPane.INFORMATION_MESSAGE, icon);
                }
            });
        }

    }


    private void itineraryItemOperation(boolean add, JButton button) {
        boolean success;
        if (add) {
            success = ic.addItineraryItem(cityName, "AbstractCity");
        } else {
            success = ic.removeItineraryItem(cityName, "AbstractCity");
        }

        if (success) {
            revalidation();
        }
    }

    private void revalidation() {
        //Create the new JList data
        ArrayList<String> lines = new ArrayList<>();
        HashSet<ItineraryItem> items = ic.getItineraryItems();
        for (ItineraryItem item : items) {
            lines.add(ic.getItineraryName(item) + " (" + ic.getItineraryTime(item) + " hours)");
        }

        //Set and revalidate
        ic.list.setListData(lines.toArray(new String[items.size()]));
        ic.list.revalidate();
    }
}

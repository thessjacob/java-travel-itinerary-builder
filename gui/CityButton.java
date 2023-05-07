package gui;

import plan.ItineraryController;
import plan.ItineraryItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;

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
                    JLabel j0 = new JLabel(cityName);
                    selectionPanel.add(j0, c);

                    JButton freeButton = new JButton("Spend Free Time");
                    freeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!ic.hasItem(cityName + " Free Time")) {
                                itineraryItemOperation(true);
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
                                itineraryItemOperation(false);
                            }
                        }
                    });
                    JButton removeButton2 = new JButton("Remove Night");
                    removeButton2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (ic.hasNightIn("Night in " + cityName)) {
                                ic.removeItineraryRest(cityName);
                                revalidation();
                            }
                        }
                    });

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


    private void itineraryItemOperation(boolean add) {
        if (add) {
            ic.addItineraryItem(cityName, "AbstractCity");
        } else {
            ic.removeItineraryItem(cityName, "AbstractCity");
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

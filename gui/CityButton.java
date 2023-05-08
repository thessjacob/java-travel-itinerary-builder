/**
 * CityButton.java is an extension of the JButton Swing component that represents a clickable button that allows a user
 * to select an AbstractCity.
 */
package gui;

import plan.ItineraryController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CityButton extends JButton {
    private final DataViewController dvc = DataViewController.INSTANCE;
    private final ItineraryController ic = ItineraryController.INSTANCE;
    private final String cityName;
    private final CityButton cityButton;

    /**
     * 0-arg constructor. Should not be used.
     */
    CityButton() {
        this("");
    }

    /**
     * 1-arg constructor.
     * @param cityName String name of the AbstractCity to display.
     */
    CityButton(String cityName) {
        super(cityName);
        this.cityName = cityName;
        this.cityButton = this;
        if (!dvc.isPlanning()) {    //if not planning, program is displaying CountryWindow
            addActionListener(new ActionListener() {
                //This action listener shows an informational pop-up.
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextArea textArea = new JTextArea(dvc.getCityDescription(cityName));
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(600, 450));  //arbitrary dimensions.
                    ImageIcon icon = new ImageIcon(dvc.getCityImageURL(cityName));
                    JOptionPane.showMessageDialog(cityButton, scrollPane, cityName + " Info",
                            JOptionPane.INFORMATION_MESSAGE, icon);
                }
            });
        } else {                    //if planning, program is displaying ItineraryWindow
            addActionListener(new ActionListener() {
                //This action listener shows a pop-up that users can interact with to create an itinerary.
                @Override
                public void actionPerformed(ActionEvent e) {
                    JPanel selectionPanel = new JPanel(); //Panel to hold the buttons
                    selectionPanel.setLayout(new GridBagLayout());
                    GridBagConstraints c = new GridBagConstraints();
                    c.gridwidth = GridBagConstraints.REMAINDER;
                    c.weightx = 1;
                    c.fill = HORIZONTAL;
                    JLabel j0 = new JLabel(cityName);  //city label over options.
                    selectionPanel.add(j0, c);

                    //Now add the 4 buttons we need. Two adds, two removes.
                    //First
                    JButton freeButton = new JButton("Spend Free Time");
                    freeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!ic.hasItem(cityName + " Free Time")) {
                                ButtonRevalidator.itineraryItemOperation(cityName, true, "AbstractCity");
                            } else {
                                ic.addItineraryItemTimeCity(cityName + " Free Time");
                            }
                            ButtonRevalidator.revalidation();
                        }
                    });
                    //Second
                    JButton stayButton = new JButton("Spend the night");
                    stayButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!ic.hasItem("Night in " + cityName)) {
                                ic.addItineraryRest(cityName);
                                ButtonRevalidator.revalidation();
                            }
                        }
                    });
                    //Third
                    JButton removeFreeTime = new JButton("Remove Free Time");
                    removeFreeTime.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (ic.hasItem(cityName + " Free Time")) {
                                ButtonRevalidator.itineraryItemOperation(cityName, false, "AbstractCity");
                            }
                        }
                    });
                    //Fourth
                    JButton removeNight = new JButton("Remove Night");
                    removeNight.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (ic.hasNightIn("Night in " + cityName)) {
                                ic.removeItineraryRest(cityName);
                                ButtonRevalidator.revalidation();
                            }
                        }
                    });

                    //Construct Pop-up Window
                    c.gridwidth = GridBagConstraints.HORIZONTAL;
                    c.gridx = selectionPanel.getComponentCount() + 1;
                    c.gridy = selectionPanel.getComponentCount() + 1;
                    selectionPanel.add(freeButton, c);
                    c.gridy += 1;
                    selectionPanel.add(stayButton, c);
                    c.gridy += 1;
                    selectionPanel.add(removeFreeTime, c);
                    c.gridy += 1;
                    selectionPanel.add(removeNight, c);
                    selectionPanel.setPreferredSize(new Dimension(600, 450)); //again, arbitrary dimensions
                    ImageIcon icon = new ImageIcon(dvc.getCityImageURL(cityName));
                    JOptionPane.showMessageDialog(cityButton, selectionPanel, cityName, JOptionPane.INFORMATION_MESSAGE, icon);
                }
            });
        }
    }
}

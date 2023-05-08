/**
 * ItineraryPanel is an extension of Jpanel that displays the Itinerary and its controls on the right side of
 * CountryWindow.
 */
package gui;

import plan.ItineraryController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class ItineraryPanel extends JPanel {
    private final JTextField titleField;
    private final String defaultTitle = "My Itinerary";
    private final ItineraryPanel panel;
    private final ItineraryController ic = ItineraryController.INSTANCE;
    private final DataViewController dvc = DataViewController.INSTANCE;
    private final JLabel tracker;
    public JList<String> itemList;

    /**
     * 0-arg constructor.
     */
    ItineraryPanel() {
        //init the controller
        ic.initItinerary(defaultTitle); //init a new Itinerary object.
        panel = this;

        dvc.setItineraryPanel(panel);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //Add title field.
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.ipadx = 75;
        titleField = new JTextField(defaultTitle);
        titleField.addFocusListener(new TitleFocusAdapter());
        add(titleField, c);

        //Add text field with Itinerary items.
        c.ipadx = 10;
        c.fill = GridBagConstraints.BOTH;
        c. weighty = 1;
        itemList = new JList<String>();
        itemList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        itemList.setLayoutOrientation(JList.VERTICAL);
        ic.setList(itemList);
        add(itemList, c);

        //Add bottom panel with tracker label
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.RELATIVE;
        JLabel lengthLabel = new JLabel("Current Trip Length: ");
        add(lengthLabel, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        tracker = new JLabel("0.0 days");
        add(tracker, c);

        //Add buttons for save and clear.
        c.gridwidth = GridBagConstraints.RELATIVE;
        JButton save = new JButton("save");
        save.addActionListener(new SaveActionListener());
        JButton clear = new JButton("clear");
        clear.addActionListener(new ClearActionListener());
        add(save, c);
        add(clear, c);
    }

    //Listeners

    /**
     * This extended FocusAdapter records changes in the Itinerary title based on user input in the title field.
     */
    class TitleFocusAdapter extends FocusAdapter {
        @Override
        public void focusLost(FocusEvent e) {
            String text = titleField.getText();
            if (text.isBlank() || text.isEmpty()) {
                titleField.setText(defaultTitle);
            } else {
                ic.setItineraryTitle(text);
            }
        }
    }

    /**
     * This extended ActionListener inner class allows the user to save their itinerary in a file of their choosing.
     */
    class SaveActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File(titleField.getText() + ".txt"));
            int returnVal = fc.showSaveDialog(panel);
            File file = fc.getSelectedFile();
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try (FileWriter fw = new FileWriter(file)) {
                    if (ic.getItinerary() != null) {
                        fw.write(ic.getItineraryString());
                    } else {
                        fw.write("Your itinerary is blank");
                    }

                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(panel, "Something went wrong with saving the file (" +
                            ioException.getMessage() + ").");
                }
            }
        }
    }

    /**
     * This extended ActionListener inner class calls methods to clear the ItineraryController, the
     * SchedulingLogicController, and to reset the View of the ItineraryPanel.
     */
    class ClearActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ic.clearController();
            ic.resetSLC();
            dvc.resetItineraryPanel();
        }
    }

    //Utility methods
    /**
     * Sets time to the latest time.
     * @param totalTime double value of current total time planned in the itinerary.
     */
    void setTime(double totalTime) {
        String text = String.format("%.2f days (%.1f hours)", (totalTime/ 24), totalTime);
        tracker.setText(text);
        this.revalidate();
    }

    /**
     * Resets the time displayed on the ItineraryPanel.
     */
    void resetTime() {
        String text = String.format("%d days (%d hours)", 0, 0);
        tracker.setText(text);
        this.revalidate();
    }
}

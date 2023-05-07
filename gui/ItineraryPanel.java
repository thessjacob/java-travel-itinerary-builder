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
    private double totalTime = 0.0;

    ItineraryPanel() {
        //init the controller
        ic.initItinerary(defaultTitle);
        panel = this;

        dvc.setItineraryPanel(panel);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.ipadx = 75;
        titleField = new JTextField(defaultTitle);
        titleField.addFocusListener(new TitleFocusAdapter());
        add(titleField, c);

        c.ipadx = 10;
        c.fill = GridBagConstraints.BOTH;
        c. weighty = 1;

        itemList = new JList<String>();
        itemList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        itemList.setLayoutOrientation(JList.VERTICAL);
        ic.list = itemList;
        add(itemList, c);
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.RELATIVE;
        JLabel lengthLabel = new JLabel("Current Trip Length: ");
        add(lengthLabel, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        tracker = new JLabel("0.0 days");
        add(tracker, c);

        c.gridwidth = GridBagConstraints.RELATIVE;
        JButton save = new JButton("save");
        save.addActionListener(new SaveActionListener());
        JButton clear = new JButton("clear");
        clear.addActionListener(new ClearActionListener());
        add(save, c);
        add(clear, c);
    }

    //Listeners
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

    class ClearActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ic.clearController();
            ic.resetSLC();
            dvc.resetItineraryPanel();
        }
    }

    //Utility methods
    void addTime() {
        setTime(ic.getTotalTime());
    }

    void setTime(double totalTime) {
        String text = String.format("%.2f days (%.1f hours)", (totalTime/ 24), totalTime);
        tracker.setText(text);
        this.revalidate();
    }
    void resetTime() {
        totalTime = 0;
        String text = String.format("%d days (%d hours)", 0, 0);
        tracker.setText(text);
        this.revalidate();
    }
}

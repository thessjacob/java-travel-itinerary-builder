package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class MainOptionsWindow extends JFrame {
    private final DataViewController dvc = DataViewController.INSTANCE;
    private final MainOptionsWindow window;

    private boolean shouldOpenMain;

    public MainOptionsWindow() {
        window = this;
        shouldOpenMain = true;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(500, 350);
        setLocationRelativeTo(null);
        setTitle(dvc.getCountry().getName());


        //Constructing window
        String countryName = dvc.getCountry().getName();
        String fileName = String.format("%s/experience-%s.png", countryName, countryName);
        ImageIcon img = new ImageIcon(MainOptionsWindow.class.getClassLoader().getResource(fileName));
        JLabel label = new JLabel(img, JLabel.CENTER);
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(label, c);
        c.gridwidth = GridBagConstraints.RELATIVE;
        MainMenuButton exploreButton = new MainMenuButton("Explore");
        exploreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shouldOpenMain = false;
                dvc.setPlanning(false);
                WindowController.showCountryWindow();
                WindowController.disposeWindow(window);

            }
        });
        add(exploreButton, c);
        c.gridwidth = GridBagConstraints.REMAINDER;
        MainMenuButton planButton = new MainMenuButton("Plan");
        planButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shouldOpenMain = false;
                dvc.setPlanning(true);
                WindowController.showItineraryWindow();
                WindowController.disposeWindow(window);
            }
        });
        add(planButton, c);
        MainMenuButton surpriseButton = new MainMenuButton("Surprise me");
        surpriseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "Sorry! The functionality to generate a random itinerary is not yet implemented! :(";
                JOptionPane.showMessageDialog(window, message);
            }
        });
        add(surpriseButton, c);
        MainMenuButton helpButton = new MainMenuButton("Help");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message =String.format("""
                This is a Travel Itinerary Planner. Press the exit button at any time to return to the previous menu.
                
                The "Explore" button will let you explore the regions, cities, and sites of %s.
                
                The "Plan" button will let you plan an itinerary. There a few things to keep in mind:
                    1. You cannot add the same activity twice to the same day. However, feel free to add additional free
                    time at any location or to have the same activity on different days.
                    
                    2. You need to rest on your vacation. There is a maximum planned hours per day, after which the program 
                    will warn you you need to rest. You can select to rest in any "place to stay" location, after which 
                    you can begin to plan the next day.
                    
                    3. You will not be able to remove a night in a city ("remove rest") if you've planned something the
                    following day. First remove all of that day's planned activities, then you will be able to remove
                    the "night in" item.
                """, dvc.getCountry().getName());
                JOptionPane.showMessageDialog(window, message);
            }
        });
        add(helpButton, c);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (shouldOpenMain) {
                    WindowController.showMainWindow();
                }
            }
        });

        setVisible(true);
    }
}

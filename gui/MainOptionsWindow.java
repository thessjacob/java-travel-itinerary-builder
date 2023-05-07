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
        setSize(500, 300);
        setLocationRelativeTo(null);
        setTitle("Trial");


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
        add(new MainMenuButton("Surprise me"), c);

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

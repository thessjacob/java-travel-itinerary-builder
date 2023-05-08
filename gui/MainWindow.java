/**
 * MainWindow.java presents users with the initial window where they can pick what country to visit.
 */
package gui;

import destination.Country;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainWindow extends JFrame {
    private final MainWindow window;
    private final DataViewController dvc = DataViewController.INSTANCE;

    /**
     * 0-arg constructor.
     */
    MainWindow() {
        window = this;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(450, 250);
        setLocationRelativeTo(null);
        setTitle("Travel Planner");

        //Restrictions
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = .5;
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(new JLabel("Where would you like to visit?"), c);
        c.weighty = .2;
        //Add CountryButtons for all database countries.
        for (Country country : dvc.getCountries()) {
            add(new CountryButton(country));
        }

        setVisible(true);
    }

    /**
     * Inner class extending JButton and adding a new anonymous ActionListener. When clicked, the CountryButton inits
     * the DataViewController, opens the MainOptionsWindow, and disposes of the parent MainWindow.
     */
    class CountryButton extends JButton {
        CountryButton(Country country) {
            setText(country.getName());
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dvc.initController(e.getActionCommand());
                    WindowController.showMainOptionsWindow();
                    WindowController.disposeWindow(window);
                }
            });
        }
    }
}

package gui;

import destination.Country;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private final MainWindow window;
    private final DataViewController dvc = DataViewController.INSTANCE;

    MainWindow() {
        window = this;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(450, 250);
        setLocationRelativeTo(null);

        //Restrictions
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = .5;
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(new JLabel("Where would you like to visit?"), c);
        c.weighty = .2;
        for (Country country : dvc.getCountries()) {
            add(new CountryButton(country));
        }

        setVisible(true);
    }

    class CountryButton extends JButton {

        CountryButton(Country country) {
            setText(country.getName());
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dvc.initController(e.getActionCommand());
                    WindowController.showWindow(new MainOptionsWindow());
                    WindowController.disposeWindow(window);
                }
            });
        }
    }
}

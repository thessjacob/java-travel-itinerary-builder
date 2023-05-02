package gui;

import destination.Country;
import destination.SuperRegion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class CountryWindow extends JFrame {
    private final DataViewController dvc = DataViewController.INSTANCE;
    private final Country country = dvc.getCountry();
    private final int numOfSuperRegions = dvc.getNumOfSuperRegions();
    private final CountryWindow window;
    protected static String superRegionName = "";
    protected JLabel centerLabel;

    protected static boolean displayingSuperRegion = false;

    CountryWindow() {
        window = this;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                superRegionName = "";
                WindowController.showMainOptionsWindow();
            }
        });
        setLayout(new BorderLayout());
        setSize(1000, 700);
        setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon(country.getBaseMapImage());
        centerLabel = new JLabel(img, JLabel.CENTER);
        add(centerLabel, BorderLayout.CENTER);
        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = GridBagConstraints.REMAINDER;
        if (leftPanel.getComponents().length < numOfSuperRegions) {
            for (SuperRegion superRegion : country.getSuperRegions()) {
                leftPanel.add(new SuperRegionButton(superRegion.getName(), leftPanel, this), c);
            }
        }
        c.weighty = .25;
        leftPanel.add(new JPanel(), c);
        add(leftPanel, BorderLayout.WEST);

        setVisible(true);
    }
}

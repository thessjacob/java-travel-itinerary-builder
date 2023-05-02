package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainMenuButton extends JButton {

    MainMenuButton() {
        this("");
    }

    MainMenuButton(String text) {
        setText(text);
        setVisible(true);
        setSize(100, 50);
    }
}

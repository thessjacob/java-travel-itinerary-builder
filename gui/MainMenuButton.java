package gui;

import javax.swing.*;

class MainMenuButton extends JButton {

    /**
     * 0-arg constructor.
     */
    MainMenuButton() {
        this("");
    }

    /**
     * 1-arg constructor that sets the text on the button as well as size.
     * @param text String text to put on button.
     */
    MainMenuButton(String text) {
        setText(text);
        setVisible(true);
        setSize(100, 50);
    }
}

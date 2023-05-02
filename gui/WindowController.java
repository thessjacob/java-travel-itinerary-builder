package gui;

import javax.swing.*;

public enum WindowController {

    INSTANCE;

    public static void hideWindow(JFrame window) {
        window.setVisible(false);
        window.setFocusable(false);
    }

    public static void showWindow(JFrame window) {
        window.setVisible(true);
        window.setFocusable(true);
    }

    public static void showMainOptionsWindow() {
        new MainOptionsWindow();
    }

    public static void showMainWindow() {
        new MainWindow();
    }


    public static void disposeWindow(JFrame window) {
        window.dispose();
    }

}

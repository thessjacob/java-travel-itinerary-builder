import database.DatabaseController;
import gui.WindowController;

public class App {
    //private final String[] countryNames = {"Serbia", "Bosnia"};
    private final String[] countryNames = {"Serbia"};

    private App() {
        DatabaseController.initDatabaseObject(countryNames);
        WindowController.showMainWindow();
    }

    private static void run() {
        new App();
    }

    public static void main(String[] args) {
        run();
    }
}

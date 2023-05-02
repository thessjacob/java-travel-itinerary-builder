package destination;

public class CulinarySite extends AbstractSite implements Visitable {

    public CulinarySite(String siteName, double length) {
        super(siteName, length);
    }

    @Override
    public void spendFreeTime() {

    }

    @Override
    public void doActivity() {
        String activity = "Enjoy a bit of culture!";
    }
}

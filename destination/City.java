package destination;

public class City extends AbstractCity implements CanStay {

    public City() {}

    public City(String name) {
        this(name, 0);
    }

    public City(String name, int size) {
        super(name, size);
    }

    @Override
    public void rest() {

    }

    @Override
    public void spendFreeTime() {

    }
}

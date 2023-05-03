package destination;

public class Village extends AbstractCity implements CanStay {

    public Village(String name) {
        this(name, 0);
    }

    public Village(String name, int size) {
        super(name, size);
    }

    @Override
    public void rest() {

    }

    @Override
    public void spendFreeTime() {

    }
}

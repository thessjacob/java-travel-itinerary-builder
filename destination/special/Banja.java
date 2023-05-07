package destination.special;

import destination.AbstractCity;
import destination.CanStay;

public class Banja extends AbstractCity implements CanStay {

    public Banja(){}

    public Banja(String name) {
        super(name);
    }

    public Banja(String name, int size) {
        super(name, size);
    }

    @Override
    public void rest() {

    }

    @Override
    public void spendFreeTime() {

    }
}

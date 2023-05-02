package destination.special;

import destination.AbstractCity;
import destination.CanStay;

public class Banja extends AbstractCity implements CanStay {
    private final String countryName = "Serbia";

    public Banja(){}

    public Banja(String name) {
        super(name);
    }

    @Override
    public void rest() {

    }

    @Override
    public void spendFreeTime() {

    }
}

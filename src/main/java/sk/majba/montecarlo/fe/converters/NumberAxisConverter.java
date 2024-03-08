package sk.majba.montecarlo.fe.converters;

import javafx.util.StringConverter;

import java.text.DecimalFormat;

public class NumberAxisConverter extends StringConverter<Number> {
    final DecimalFormat formatter;

    public NumberAxisConverter(String decimalFormat) {
        this.formatter = new DecimalFormat(decimalFormat);
    }

    @Override
    public Number fromString(String string) {
        return null;
    }

    @Override
    public String toString(Number value) {
        double doubleValue = value.doubleValue();
        if (doubleValue < 1_000) {
            return formatter.format(doubleValue);
        } else if (doubleValue < 1_000_000) {
            return formatter.format(doubleValue / 1_000) + "K";
        } else {
            return formatter.format(doubleValue / 1_000_000) + "M";
        }
    }
}

package sk.majba.montecarlo.be.generators;

public class DeterministicGenerator extends Generator{
    private final double generatedNumber;
    public DeterministicGenerator(double generatedNumber) {
        this.generatedNumber = generatedNumber;
    }
    @Override
    public double generate() {
        return generatedNumber;
    }
}

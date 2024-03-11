package sk.majba.montecarlo.be.generators;

import java.util.Random;

public class DiscreteUniformGenerator extends Generator {
    private final int lowerBound;
    private final int upperBound;
    private final Random seedGenerator;

    /**
     * Creates a discrete uniform generator instance with an initialized seed and bounds
     * @param lowerBound the lower bound of the generation interval (inclusive)
     * @param upperBound the upper bound of the generation interval (inclusive)
     * @param seedGenerator the generator used to initialize the see od the generator
     */
    public DiscreteUniformGenerator(int lowerBound, int upperBound, Random seedGenerator) {
        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException("Lower bound must be less than upper bound");
        }
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.seedGenerator = seedGenerator;
        super.setSeed(this.seedGenerator.nextInt());
    }

    public double generate() {
        return this.lowerBound + (double) super.nextInt(this.upperBound - this.lowerBound + 1);
    }
}

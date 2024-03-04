package sk.majba.montecarlo.be.generators;

import java.util.Random;

import static sk.majba.montecarlo.HelloApplication.DEBUG;
import static sk.majba.montecarlo.HelloApplication.STATIC_SEED;

public class DiscreteUniformGenerator extends Generator {
    private final int lowerBound;
    private final int upperBound;
    private final Random seedGenerator;

    public DiscreteUniformGenerator(int lowerBound, int upperBound, Random seedGenerator) {
        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException("Lower bound must be less than upper bound");
        }
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.seedGenerator = seedGenerator;
    }

    public double generate() {
        if (!DEBUG) {
            super.setSeed(seedGenerator.nextLong());
        } else {
            super.setSeed(STATIC_SEED);
        }

        return lowerBound + super.nextInt(upperBound - lowerBound + 1);
    }
}

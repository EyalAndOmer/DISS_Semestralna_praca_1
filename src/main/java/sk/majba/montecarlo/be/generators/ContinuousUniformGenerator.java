package sk.majba.montecarlo.be.generators;

import java.util.Random;

import static sk.majba.montecarlo.HelloApplication.DEBUG;
import static sk.majba.montecarlo.HelloApplication.STATIC_SEED;

public class ContinuousUniformGenerator extends Generator {
    private final double lowerBound;
    private final double upperBound;
    private final Random seedGenerator;

    public ContinuousUniformGenerator(double lowerBound, double upperBound) {
        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException("Lower bound must be less than upper bound");
        }
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.seedGenerator = new Random();
    }

    public double generate() {
        if (!DEBUG) {
            super.setSeed(seedGenerator.nextLong());
        } else {
            super.setSeed(STATIC_SEED);
        }

        return lowerBound + (upperBound - lowerBound) * super.nextDouble();
    }
}

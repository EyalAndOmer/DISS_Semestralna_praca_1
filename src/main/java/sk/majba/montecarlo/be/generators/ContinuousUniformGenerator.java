package sk.majba.montecarlo.be.generators;

import java.util.Random;

import static sk.majba.montecarlo.HelloApplication.DEBUG;
import static sk.majba.montecarlo.HelloApplication.STATIC_SEED;

public class ContinuousUniformGenerator extends Generator {
    private final double lowerBound;
    private final double upperBound;
    private final Random seedGenerator;
    private final double generationProbability;

    public ContinuousUniformGenerator(double lowerBound, double upperBound, Random seedGenerator) {
        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException("Lower bound must be less than upper bound");
        }

        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.seedGenerator = seedGenerator;
        this.generationProbability = 1;
    }

    public ContinuousUniformGenerator(double lowerBound, double upperBound, Random seedGenerator, double generationProbability) {
        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException("Lower bound must be less than upper bound");
        }

        if (generationProbability <= 0 || generationProbability > 1) {
            throw new IllegalArgumentException("Generation probability should be larger than 0 or lower or equal to 1.");
        }

        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.seedGenerator = seedGenerator;
        this.generationProbability = generationProbability;
    }

    @Override
    public double generate() {
        if (!DEBUG) {
            super.setSeed(seedGenerator.nextInt());
        } else {
            super.setSeed(STATIC_SEED);
        }

        return lowerBound + (upperBound - lowerBound) * super.nextDouble();
    }
    @Override
    public double getGenerationProbability() {
        return generationProbability;
    }
}

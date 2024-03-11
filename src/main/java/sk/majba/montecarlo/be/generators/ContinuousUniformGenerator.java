package sk.majba.montecarlo.be.generators;

import java.util.Random;

public class ContinuousUniformGenerator extends Generator {
    private final double lowerBound;
    private final double upperBound;
    private final Random seedGenerator;
    private final double generationProbability;

    /**
     * Creates a continuous uniform generator instance with an initialized seed and bounds
     * @param lowerBound the lower bound of the generation interval (inclusive)
     * @param upperBound the upper bound of the generation interval (exclusive)
     * @param seedGenerator the generator used to initialize the see od the generator
     */
    public ContinuousUniformGenerator(double lowerBound, double upperBound, Random seedGenerator) {
        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException("Lower bound must be less than upper bound");
        }

        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.seedGenerator = seedGenerator;
        this.generationProbability = 1;
        super.setSeed(this.seedGenerator.nextInt());
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
        return this.lowerBound + (this.upperBound - this.lowerBound) * super.nextDouble();
    }

    @Override
    public double getGenerationProbability() {
        return generationProbability;
    }
}

package sk.majba.montecarlo.be.generators;

import sk.majba.montecarlo.be.ContinuousEmpiricGeneratorConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sk.majba.montecarlo.be.Constants.DELTA;

public class ContinuousEmpiricGenerator extends Generator {
    private final List<Double> generationIntervals;
    private final List<Generator> empiricGenerators;
    private final Random propabilityRandom;
    private final Random seedGenerator;

    public ContinuousEmpiricGenerator(List<ContinuousEmpiricGeneratorConfiguration> configurations, Random seedGenerator) {
        this.generationIntervals = new ArrayList<>();
        this.propabilityRandom = new Random();
        this.empiricGenerators = new ArrayList<>();
        this.seedGenerator = seedGenerator;
        this.propabilityRandom.setSeed(this.seedGenerator.nextInt());

        this.initGenerators(configurations);
        this.checkProbabilitySum();
        this.createProbabilityMap(empiricGenerators);
    }

    private void initGenerators(List<ContinuousEmpiricGeneratorConfiguration> configurations) {
        for (ContinuousEmpiricGeneratorConfiguration configuration : configurations) {
            this.empiricGenerators.add(new ContinuousUniformGenerator(
                    configuration.getLowerBound(),
                    configuration.getUpperBound(), this.seedGenerator,
                    configuration.getGenerationProbability())
            );
        }
    }

    private void createProbabilityMap(List<Generator> probabilityMap) {
        double sum = 0.0;
        double lastKey = 0.0;
        for (Generator generator : probabilityMap) {
            this.generationIntervals.add(sum);
            sum += generator.getGenerationProbability();
            lastKey = sum;
        }

        // To keep the interval from 0 to 1, if the last key is not 1.0 explicitly, then add it as the last element in the interval
        if (lastKey != 1.0) {
            this.generationIntervals.removeLast();
            this.generationIntervals.add(1.0);
        }
    }

    private void checkProbabilitySum() {
        double totalSum = 0;

        for (Generator generator : this.empiricGenerators) {
            totalSum += generator.getGenerationProbability();
        }

        if (totalSum != 1.0) {
            throw new IllegalStateException("The total sum of probabilities in an empiric distribution must be equal to 1.");
        }
    }

    private Generator findProbabilityRandom(double probability) {
        for (int i = 0; i < this.generationIntervals.size() - 1; i++) {
            if (Math.abs(probability - this.generationIntervals.get(i)) < DELTA ||
                    (probability >= this.generationIntervals.get(i) && probability < this.generationIntervals.get(i + 1))) {
                return this.empiricGenerators.get(i);
            }
        }

        if (probability == 0) {
            throw new IllegalArgumentException("Probability is 0");
        }

        if (Math.abs(probability - this.generationIntervals.getLast()) < DELTA ||
                (probability >= this.generationIntervals.getLast() && probability <= 1.00)) {
            return this.empiricGenerators.getLast();
        }

        throw new IllegalArgumentException(String.format("Probability not found %f", probability));
    }

    @Override
    public double generate() {
        double probability = this.propabilityRandom.nextDouble();
        Generator pickedGenerator = this.findProbabilityRandom(probability);

        return pickedGenerator.generate();
    }
}

package sk.majba.montecarlo.be.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sk.majba.montecarlo.HelloApplication.DELTA;

public class ContinuousEmpiricGenerator extends Generator {
    private final List<Double> generationIntervals;
    private final List<Generator> empiricGenerators;
    private final Random propabilityRandom;

    public ContinuousEmpiricGenerator(List<Generator> empiricGenerators) {
        this.generationIntervals = new ArrayList<>();
        this.propabilityRandom = new Random();
        this.empiricGenerators = empiricGenerators;

        this.checkProbabilitySum();
        this.createProbabilityMap(empiricGenerators);
    }

    private void createProbabilityMap(List<Generator> probabilityMap) {
        double sum = 0.0;
        double lastKey = 0.0;
        for (Generator generator : probabilityMap) {
            this.generationIntervals.add(sum);
            sum += generator.getGenerationProbability();
            lastKey = sum;
        }

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
            throw new IllegalArgumentException("The total sum of probabilities in an empiric distribution must be equal to 1.");
        }
    }

    private Generator findProbabilityRandom(double probability) {
        for (int i = 0; i < this.generationIntervals.size() - 1; i++) {
            if (Math.abs(probability - this.generationIntervals.get(i)) < DELTA || Math.abs(probability - this.generationIntervals.get(i + 1)) < DELTA ||
                    (probability >= this.generationIntervals.get(i) && probability < this.generationIntervals.get(i + 1))) {
                return this.empiricGenerators.get(i);
            }
        }

        if (Math.abs(probability - this.generationIntervals.getLast()) < DELTA ||
                (probability >= this.generationIntervals.getLast() && probability < 1.00)) {
            return this.empiricGenerators.getLast();
        }

        throw new IllegalArgumentException("Probability not found");
    }

    @Override
    public double generate() {
        double probability = this.propabilityRandom.nextDouble();
        Generator pickedGenerator = this.findProbabilityRandom(probability);

        return pickedGenerator.generate();
    }
}

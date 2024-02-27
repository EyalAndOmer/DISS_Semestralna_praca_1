package sk.majba.montecarlo.be.generators;

import java.util.*;

public class ContinuousEmpiricGenerator extends Generator {
    private final Map<Double, Generator> empiricRandoms;
    private final Random propabilityRandom;

    public ContinuousEmpiricGenerator(Map<Double, Generator> empiricRandoms) {
        this.empiricRandoms = empiricRandoms;
        this.propabilityRandom = new Random();

        this.checkProbabilitySum();
    }

    private void checkProbabilitySum() {
        double totalSum = 0;

        for (double propability: this.empiricRandoms.keySet()) {
            totalSum += propability;
        }

        if (totalSum != 1) {
            throw new IllegalArgumentException("The total sum of probabilities in an empiric distribution must be equal to 1.");
        }
    }

    private Generator findProbabilityRandom(double probability) {
        ArrayList<Double> probabilities = new ArrayList<>(this.empiricRandoms.keySet());

        for (int i = 0; i < probabilities.size() - 1; i++) {
            if (probability >= probabilities.get(i) && probability < probabilities.get(i + 1)) {
                return this.empiricRandoms.get(probabilities.get(i));
            }
        }

        throw new IllegalArgumentException("Probability not found");
    }

    public double generate() {
        double probability = this.propabilityRandom.nextDouble();
        Generator pickedRandom = this.findProbabilityRandom(probability);

        return pickedRandom.generate();
    }
}

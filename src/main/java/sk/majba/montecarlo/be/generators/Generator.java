package sk.majba.montecarlo.be.generators;

import java.util.Random;

public abstract class Generator extends Random {
    private final double generationProbability;

    protected Generator() {
        this.generationProbability = 1;
    }

    public double getGenerationProbability() {
        return generationProbability;
    }

    public abstract double generate();

}

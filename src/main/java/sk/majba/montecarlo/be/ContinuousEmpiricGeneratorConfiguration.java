package sk.majba.montecarlo.be;

public class ContinuousEmpiricGeneratorConfiguration {
    final double lowerBound;
    final double upperBound;
    final double generationProbability;

    public ContinuousEmpiricGeneratorConfiguration(double lowerBound, double upperBound, double generationProbability) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.generationProbability = generationProbability;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public double getGenerationProbability() {
        return generationProbability;
    }
}

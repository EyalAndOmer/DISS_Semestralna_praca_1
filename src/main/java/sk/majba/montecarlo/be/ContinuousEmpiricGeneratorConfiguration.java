package sk.majba.montecarlo.be;

/**
 * Configuration for a generator that will be a part of an empiric generator
 * @param lowerBound loweBound generation number (inclusive)
 * @param upperBound upperBound generation number (exclusive)
 * @param generationProbability the probability of picking this generator, a real number from 0 to 1
 */
public record ContinuousEmpiricGeneratorConfiguration(double lowerBound, double upperBound,
                                                      double generationProbability) {
}

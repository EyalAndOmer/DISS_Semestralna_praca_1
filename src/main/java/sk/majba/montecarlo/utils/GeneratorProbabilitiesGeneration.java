package sk.majba.montecarlo.utils;

import sk.majba.montecarlo.be.ContinuousEmpiricGeneratorConfiguration;
import sk.majba.montecarlo.be.generators.ContinuousEmpiricGenerator;
import sk.majba.montecarlo.be.generators.ContinuousUniformGenerator;
import sk.majba.montecarlo.be.generators.DiscreteUniformGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Util class used to output files with generated values to verify the functionality of my generators
 */
public class GeneratorProbabilitiesGeneration {
    private static List<String> generateEmpiricGeneratorData(Random seedGenerator) {
        ArrayList<ContinuousEmpiricGeneratorConfiguration> empiricGenerators =  new ArrayList<>();
        empiricGenerators.add(new ContinuousEmpiricGeneratorConfiguration(0.1, 0.3, 0.1));
        empiricGenerators.add(new ContinuousEmpiricGeneratorConfiguration(0.3, 0.8, 0.35));
        empiricGenerators.add(new ContinuousEmpiricGeneratorConfiguration(0.8, 1.2, 0.2));
        empiricGenerators.add(new ContinuousEmpiricGeneratorConfiguration(1.2, 2.5, 0.15));
        empiricGenerators.add(new ContinuousEmpiricGeneratorConfiguration(2.5, 3.8, 0.15));
        empiricGenerators.add(new ContinuousEmpiricGeneratorConfiguration(3.8, 4.8, 0.05));

        ContinuousEmpiricGenerator empiricGenerator = new ContinuousEmpiricGenerator(empiricGenerators, seedGenerator);

        List<String> values = new ArrayList<>();

        for (int i = 0; i < 10_000_000; i++) {
            values.add(String.valueOf(empiricGenerator.generate()));
        }
        return values;
    }

    private static List<String> generateUniformDiscreteData(Random seedGenerator) {
        DiscreteUniformGenerator discreteUniformGenerator = new DiscreteUniformGenerator(1, 4, seedGenerator);
        List<String> values = new ArrayList<>();

        for (int i = 0; i < 10_000_000; i++) {
            values.add(String.valueOf(discreteUniformGenerator.generate()));
        }

        return values;
    }

    private static List<String> generateUniformContinuousData(Random seedGenerator) {
        ContinuousUniformGenerator continuousUniformGenerator = new ContinuousUniformGenerator(0.3, 5, seedGenerator);
        List<String> values = new ArrayList<>();

        for (int i = 0; i < 10_000_000; i++) {
            values.add(String.valueOf(continuousUniformGenerator.generate()));
        }

        return values;
    }
    public static void main(String[] args) {
        Random seedGenerator = new Random();

        List<String> empiricData = generateEmpiricGeneratorData(seedGenerator);
        List<String> uniformDiscreteData = generateUniformDiscreteData(seedGenerator);
        List<String> uniformContinuousData = generateUniformContinuousData(seedGenerator);

        FileUtils.writeDataToFile(empiricData, "empiric_generator_data.txt");
        FileUtils.writeDataToFile(uniformDiscreteData, "uniform_discrete_generator_data.txt");
        FileUtils.writeDataToFile(uniformContinuousData, "uniform_continuous_data.txt");
    }
}

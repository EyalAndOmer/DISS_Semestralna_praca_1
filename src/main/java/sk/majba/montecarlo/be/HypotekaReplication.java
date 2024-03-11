package sk.majba.montecarlo.be;

import javafx.application.Platform;
import sk.majba.montecarlo.be.generators.*;
import sk.majba.montecarlo.be.simulation_core.Replication;
import sk.majba.montecarlo.fe.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sk.majba.montecarlo.be.Constants.*;

/**
 * Class that encapsulates the logic behind the replications of the Monte Carlo simulation
 */
public class HypotekaReplication implements Replication {
    Random seedGenerator = new Random();

    List<Integer> strategyOneFixationChangeYears;
    List<Integer> strategyTwoFixationChangeYears;
    List<Integer> strategyThreeFixationChangeYears;

    HypotekaStrategy strategy1;
    HypotekaStrategy strategy2;
    HypotekaStrategy strategy3;

    double strategy1Sum;
    double strategy2Sum;
    double strategy3Sum;
    int numReplications;
    Controller controller;

    public HypotekaReplication(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void beforeAllReplications() {
        if (DEBUG) {
            seedGenerator.setSeed(STATIC_SEED);
        }

        ArrayList<ContinuousEmpiricGeneratorConfiguration> empiricGenerators = getContinuousEmpiricGeneratorConfigurations();

        RangeMap<Generator> generators = new RangeMap<>();
        generators.put(2024, 2025, new DiscreteUniformGenerator(1, 4, seedGenerator));
        generators.put(2026, 2027, new ContinuousUniformGenerator(0.3, 5, seedGenerator));
        generators.put(2028, 2029, new ContinuousEmpiricGenerator(empiricGenerators, seedGenerator));
        generators.put(2030, 2031, new DeterministicGenerator(1.3));
        generators.put(2032, 2033, new ContinuousUniformGenerator(0.9, 2.2, seedGenerator));


        this.strategyOneFixationChangeYears = new ArrayList<>(List.of(5, 3, 1, 1));
        this.strategyTwoFixationChangeYears = new ArrayList<>(List.of(3, 3, 3, 1));
        this.strategyThreeFixationChangeYears = new ArrayList<>(List.of(3, 1, 5, 1));

        this.strategy1 = new HypotekaStrategy(strategyOneFixationChangeYears, generators, this.controller.getMortgageSum(), POCET_ROKOV_SPLACANIA);
        this.strategy2 = new HypotekaStrategy(strategyTwoFixationChangeYears, generators, this.controller.getMortgageSum(), POCET_ROKOV_SPLACANIA);
        this.strategy3 = new HypotekaStrategy(strategyThreeFixationChangeYears, generators, this.controller.getMortgageSum(), POCET_ROKOV_SPLACANIA);

        this.strategy1Sum = 0;
        this.strategy2Sum = 0;
        this.strategy3Sum = 0;

        this.numReplications = 0;
    }

    private static ArrayList<ContinuousEmpiricGeneratorConfiguration> getContinuousEmpiricGeneratorConfigurations() {
        ArrayList<ContinuousEmpiricGeneratorConfiguration> empiricGenerators =  new ArrayList<>();
        empiricGenerators.add(new ContinuousEmpiricGeneratorConfiguration(0.1, 0.3, 0.1));
        empiricGenerators.add(new ContinuousEmpiricGeneratorConfiguration(0.3, 0.8, 0.35));
        empiricGenerators.add(new ContinuousEmpiricGeneratorConfiguration(0.8, 1.2, 0.2));
        empiricGenerators.add(new ContinuousEmpiricGeneratorConfiguration(1.2, 2.5, 0.15));
        empiricGenerators.add(new ContinuousEmpiricGeneratorConfiguration(2.5, 3.8, 0.15));
        empiricGenerators.add(new ContinuousEmpiricGeneratorConfiguration(3.8, 4.8, 0.05));
        return empiricGenerators;
    }

    @Override
    public void afterAllReplications() {
        System.out.printf("%f, %f, %f%n", this.strategy1Sum / this.numReplications, this.strategy2Sum / this.numReplications, this.strategy3Sum / this.numReplications);
    }

    @Override
    public void beforeReplication() {
        this.strategy1.resetStrategy();
        this.strategy2.resetStrategy();
        this.strategy3.resetStrategy();
    }

    @Override
    public void afterReplication() {
        this.strategy1Sum += this.strategy1.getResult();
        this.strategy2Sum += this.strategy2.getResult();
        this.strategy3Sum += this.strategy3.getResult();

        this.numReplications++;

        if (this.numReplications % this.controller.getSkipEachNValuesSize() == 0 && this.numReplications > this.controller.getSkipFirstValuesSize()) {
            final int finalI = this.numReplications;
            // Output data to chart
            Platform.runLater(() -> {
                this.controller.getAllDatasets().get(0).add(finalI, this.strategy1Sum / this.numReplications);
                this.controller.getAllDatasets().get(1).add(finalI, this.strategy2Sum / this.numReplications);
                this.controller.getAllDatasets().get(2).add(finalI, this.strategy3Sum / this.numReplications);
            });
        }
    }

    @Override
    public void execute() {
        this.strategy1.executeStrategy();
        this.strategy2.executeStrategy();
        this.strategy3.executeStrategy();
    }
}

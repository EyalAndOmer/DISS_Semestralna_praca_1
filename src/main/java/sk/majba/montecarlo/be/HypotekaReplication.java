package sk.majba.montecarlo.be;

import sk.majba.montecarlo.be.generators.*;
import sk.majba.montecarlo.be.simulation_core.Replication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sk.majba.montecarlo.HelloApplication.DEBUG;
import static sk.majba.montecarlo.HelloApplication.STATIC_SEED;

public class HypotekaReplication implements Replication {
    public static final double STARTOVACIA_VYSKA_HYPOTEKARNEHO_UVERU = 100_000;
    public static final double POCET_ROKOV_SPLACANIA = 10;
    public static final int START_YEAR = 2024;
    public static final int END_YEAR = 2033;

    DiscreteUniformGenerator generatorFrom2024To2025;
    ContinuousUniformGenerator generatorFrom2026To2027;
    ContinuousEmpiricGenerator generatorFrom2028To2029;
    DeterministicGenerator generatorFrom2030To2031;
    ContinuousUniformGenerator generatorFrom2032To2033;

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



    @Override
    public void beforeAllReplications() {
        if (DEBUG) {
            seedGenerator.setSeed(STATIC_SEED);
        }

        // Init generators
        this.generatorFrom2024To2025 = new DiscreteUniformGenerator(1, 4, seedGenerator);
        this.generatorFrom2026To2027 = new ContinuousUniformGenerator(0.3, 5, seedGenerator);

        ArrayList<Generator> empiricGenerators =  new ArrayList<>();
        empiricGenerators.add(new ContinuousUniformGenerator(0.1, 0.3, seedGenerator, 0.1));
        empiricGenerators.add(new ContinuousUniformGenerator(0.3, 0.8, seedGenerator, 0.35));
        empiricGenerators.add(new ContinuousUniformGenerator(0.8, 1.2, seedGenerator, 0.2));
        empiricGenerators.add(new ContinuousUniformGenerator(1.2, 2.5, seedGenerator, 0.15));
        empiricGenerators.add(new ContinuousUniformGenerator(2.5, 3.8, seedGenerator, 0.15));
        empiricGenerators.add(new ContinuousUniformGenerator(3.8, 4.8, seedGenerator, 0.05));

        this.generatorFrom2028To2029 = new ContinuousEmpiricGenerator(empiricGenerators, seedGenerator);
        this.generatorFrom2030To2031 = new DeterministicGenerator(1.3);
        this.generatorFrom2032To2033 = new ContinuousUniformGenerator(0.9, 2.2, seedGenerator);

        this.strategyOneFixationChangeYears = new ArrayList<>(List.of(5, 3, 1, 1));
        this.strategyTwoFixationChangeYears = new ArrayList<>(List.of(3, 3, 3, 1));
        this.strategyThreeFixationChangeYears = new ArrayList<>(List.of(3, 1, 5, 1));

        this.strategy1 = new HypotekaStrategy(strategyOneFixationChangeYears);
        this.strategy2 = new HypotekaStrategy(strategyTwoFixationChangeYears);
        this.strategy3 = new HypotekaStrategy(strategyThreeFixationChangeYears);

        this.strategy1Sum = 0;
        this.strategy2Sum = 0;
        this.strategy3Sum = 0;

        this.numReplications = 0;
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
    }

    // TODO refactor
    @Override
    public void execute() {
        this.strategy1.getNewMesacnaUrokovaSadzba(getYearGenerator(START_YEAR));
        this.strategy2.getNewMesacnaUrokovaSadzba(getYearGenerator(START_YEAR));
        this.strategy3.getNewMesacnaUrokovaSadzba(getYearGenerator(START_YEAR));

        for (int i = START_YEAR; i <= END_YEAR; i++) {
            this.strategy1.processYear(i, getYearGenerator(i));
            this.strategy2.processYear(i, getYearGenerator(i));
            this.strategy3.processYear(i, getYearGenerator(i));
        }
    }

    private Generator getYearGenerator(int year) {
        if (isBetween(year, 2024, 2025)) {
            return this.generatorFrom2024To2025;
        } else if (isBetween(year, 2026, 2027)) {
            return this.generatorFrom2026To2027;
        } else if (isBetween(year, 2028, 2029)) {
            return this.generatorFrom2028To2029;
        } else if (isBetween(year, 2030, 2031)) {
            return this.generatorFrom2030To2031;
        } else if (isBetween(year, 2032, 2033)) {
            return this.generatorFrom2032To2033;
        }
        throw new IllegalArgumentException("Given year is outside of the scope of the simulated time frame");
    }

    private static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }
}

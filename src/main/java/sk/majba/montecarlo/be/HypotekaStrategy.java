package sk.majba.montecarlo.be;

import sk.majba.montecarlo.be.generators.Generator;

import java.util.List;

import static sk.majba.montecarlo.be.Constants.*;

public class HypotekaStrategy {
    private final List<Integer> strategyFixationChangeYears;
    private double result;
    private final int initialVyskaHypotekarnehoUveru;
    private final int initialPocetRokovSplacania;
    private double zostavajuciPocetRokovSplacania;
    private double vyskaHypotekarnehoUveru;
    private final RangeMap<Generator> generators;

    public HypotekaStrategy(List<Integer> strategyFixationChangeYears, RangeMap<Generator> generators,int vyskaHypotekarnehoUveru, int pocetRokovSplacania) {
        this.strategyFixationChangeYears = strategyFixationChangeYears;
        this.zostavajuciPocetRokovSplacania = pocetRokovSplacania;
        this.vyskaHypotekarnehoUveru = vyskaHypotekarnehoUveru;

        this.initialPocetRokovSplacania = pocetRokovSplacania;
        this.initialVyskaHypotekarnehoUveru = vyskaHypotekarnehoUveru;

        this.generators = generators;
    }

    public void resetStrategy() {
        this.zostavajuciPocetRokovSplacania = this.initialPocetRokovSplacania;
        this.vyskaHypotekarnehoUveru =  this.initialVyskaHypotekarnehoUveru;
        this.result = 0;
    }

    public double getResult() {
        return result;
    }

    public void executeStrategy() {
        int currentYear = START_YEAR;
        double mesacnaUrokovaSadzba;
        double mesacnaSplatka;

        for (Integer fixation : this.strategyFixationChangeYears) {
            Generator currentGenerator = this.generators.get(currentYear);

            mesacnaUrokovaSadzba = (currentGenerator.generate() / 100) / 12;
            mesacnaSplatka = (this.vyskaHypotekarnehoUveru * mesacnaUrokovaSadzba *
                    Math.pow(1 + mesacnaUrokovaSadzba, 12 * this.zostavajuciPocetRokovSplacania)) /
                    (Math.pow(1 + mesacnaUrokovaSadzba, 12 * this.zostavajuciPocetRokovSplacania) - 1);

            if (mesacnaSplatka < 0) {
                throw new IllegalStateException("mesacnaSplatka cannot be negative");
            }

            this.result += 12 * fixation * mesacnaSplatka;
            currentYear += fixation;

            this.vyskaHypotekarnehoUveru = this.vyskaHypotekarnehoUveru * (
                    (Math.pow((1 + mesacnaUrokovaSadzba), 12 * this.zostavajuciPocetRokovSplacania) -
                            Math.pow((1 + mesacnaUrokovaSadzba), 12 * (double) fixation)) /
                            (Math.pow((1 + mesacnaUrokovaSadzba), 12 * this.zostavajuciPocetRokovSplacania) - 1));

            if (this.vyskaHypotekarnehoUveru < 0 || (this.vyskaHypotekarnehoUveru == 0 && currentYear != END_YEAR + 1)) {
                throw new IllegalStateException("vyskaHypotekarnehoUveru cannot be negative");
            }

            this.zostavajuciPocetRokovSplacania -= fixation;
        }
    }
}

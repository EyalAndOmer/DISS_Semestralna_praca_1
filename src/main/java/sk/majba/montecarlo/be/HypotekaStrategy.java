package sk.majba.montecarlo.be;

import sk.majba.montecarlo.be.generators.Generator;

import java.util.List;

import static sk.majba.montecarlo.be.HypotekaReplication.*;

public class HypotekaStrategy {
    private final List<Integer> strategyFixationChangeYears;
    private double result;
    private double zostavajuciPocetRokovSplacania;
    private double vyskaHypotekarnehoUveru;
    private double rocnaUrokovaSadzba;
    private int currentFixationIndex;
    private int yearsPassed;
    private double mesacnaSplatka;

    private double mesacnaUrokovaSadzba;
    public HypotekaStrategy(List<Integer> strategyFixationChangeYears) {
        this.strategyFixationChangeYears = strategyFixationChangeYears;
        this.zostavajuciPocetRokovSplacania = POCET_ROKOV_SPLACANIA;
        this.vyskaHypotekarnehoUveru = STARTOVACIA_VYSKA_HYPOTEKARNEHO_UVERU;
        this.currentFixationIndex = 0;
        this.yearsPassed = 0;
        this.result = 0;
        this.rocnaUrokovaSadzba = 0;
        this.mesacnaSplatka = 0;
        this.mesacnaUrokovaSadzba = 0;
    }

    public void resetStrategy() {
        this.zostavajuciPocetRokovSplacania = POCET_ROKOV_SPLACANIA;
        this.vyskaHypotekarnehoUveru = STARTOVACIA_VYSKA_HYPOTEKARNEHO_UVERU;
        this.currentFixationIndex = 0;
        this.yearsPassed = 0;
        this.result = 0;
        this.rocnaUrokovaSadzba = 0;
        this.mesacnaSplatka = 0;
        this.mesacnaUrokovaSadzba = 0;
    }

    // TODO refactor
    public void processYear(int year, Generator currentYearGenerator) {
        if (this.strategyFixationChangeYears.get(currentFixationIndex) == year - START_YEAR - this.yearsPassed) {
            // recalculate the fixation rate
            this.yearsPassed += this.strategyFixationChangeYears.get(currentFixationIndex);

            this.vyskaHypotekarnehoUveru = this.vyskaHypotekarnehoUveru *
                    (
                    (
                            Math.pow((1 + mesacnaUrokovaSadzba), 12 * this.zostavajuciPocetRokovSplacania)
                    -
                            Math.pow((1 + mesacnaUrokovaSadzba), 12 * (double) this.strategyFixationChangeYears.get(currentFixationIndex))
                    ) /
                    (
                            Math.pow((1 + mesacnaUrokovaSadzba), 12 * this.zostavajuciPocetRokovSplacania) - 1
                    )
                    );

            this.zostavajuciPocetRokovSplacania -= this.strategyFixationChangeYears.get(currentFixationIndex);

            this.currentFixationIndex++;

            getNewMesacnaUrokovaSadzba(currentYearGenerator);
        }

        if (this.vyskaHypotekarnehoUveru <= 0) {
            throw new IllegalStateException("vyskaHypotekarnehoUveru cannot be negative");
        }

        if (this.mesacnaSplatka < 0) {
            throw new IllegalStateException("mesacnaSplatka cannot be negative");
        }

        this.result += 12 * this.mesacnaSplatka;
    }

    public void getNewMesacnaUrokovaSadzba(Generator currentYearGenerator) {
        this.rocnaUrokovaSadzba = currentYearGenerator.generate() / 100;
        this.mesacnaUrokovaSadzba = this.rocnaUrokovaSadzba / 12;

        this.mesacnaSplatka = (
                this.vyskaHypotekarnehoUveru * mesacnaUrokovaSadzba *
                Math.pow(1 + mesacnaUrokovaSadzba, 12 * this.zostavajuciPocetRokovSplacania)
        ) /
        (
                Math.pow(1 + mesacnaUrokovaSadzba, 12 * this.zostavajuciPocetRokovSplacania) - 1
        );
    }

    public double getResult() {
        return result;
    }
}

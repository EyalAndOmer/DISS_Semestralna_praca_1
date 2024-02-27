package sk.majba.montecarlo.be.generators;

import java.util.Random;

public abstract class Generator extends Random {
    public abstract double generate();
}

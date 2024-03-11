package sk.majba.montecarlo.be;

public class Constants {
    // Hide implicit public constructor by creating a private one
    private Constants() {}
    public static final int STARTOVACIA_VYSKA_HYPOTEKARNEHO_UVERU = 100_000;
    public static final int POCET_ROKOV_SPLACANIA = 10;
    public static final int START_YEAR = 2024;
    public static final int END_YEAR = 2033;
    public static final boolean DEBUG = true;
    public static final int STATIC_SEED = 1;
    public static final double DELTA = 1.0E-12;
    // Number of points to skip after the points will be displayed on the chart
    public static final int SKIP_FIRST_N_POINTS = 1_000_000;
    public static final int INITIAL_NUMBER_OF_REPLICATIONS = 100_000_000;
    // The number of points that will be skipped before another point will be outputted on the chart
    public static final int SKIP_EVERY_N_POINTS = 100;
}

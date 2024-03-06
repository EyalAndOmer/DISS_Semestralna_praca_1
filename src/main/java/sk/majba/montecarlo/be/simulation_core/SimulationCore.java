package sk.majba.montecarlo.be.simulation_core;


public class SimulationCore {
    private final Replication replication;

    public SimulationCore(Replication replication) {
        this.replication = replication;
    }

    public void simulate(int numberOfReplications) {
        this.replication.beforeAllReplications();

        for (int i = 0; i < numberOfReplications; i++) {
            if (i % 10_000_000 == 0 && i > 0) {
                System.out.printf("Replication %d done.%n", i);
            }

            this.replication.beforeReplication();
            this.replication.execute();
            this.replication.afterReplication();
        }

        this.replication.afterAllReplications();
    }
}

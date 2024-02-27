package sk.majba.montecarlo.be.simulation_core;

public class SimulationCore {
    private final Replication replication;
    public SimulationCore(Replication replication) {
        this.replication = replication;
    }

    public void simulate(int numberOfReplications) {
        this.replication.beforeAllReplications();

        for (int i = 0; i < numberOfReplications; i++) {
            this.replication.beforeReplications();
            this.replication.execute();
            this.replication.afterReplication();
        }

        this.replication.afterAllReplications();
    }
}

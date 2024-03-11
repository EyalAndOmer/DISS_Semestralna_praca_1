package sk.majba.montecarlo.be.simulation_core;


import java.util.concurrent.atomic.AtomicBoolean;

public class SimulationCore {
    private final Replication replication;
    private boolean isPaused = false;
    private final AtomicBoolean pauseRequested = new AtomicBoolean(false);

    public SimulationCore(Replication replication) {
        this.replication = replication;
    }

    public void simulate(int numberOfReplications) {
        this.replication.beforeAllReplications();

        // Pause the simulation on user action
        for (int i = 0; i < numberOfReplications; i++) {
            while (pauseRequested.get()) {
                this.isPaused = true;
            }
            this.isPaused = false;

            this.replication.beforeReplication();
            this.replication.execute();
            this.replication.afterReplication();
        }

        this.replication.afterAllReplications();
    }

    public void pauseSimulation() {
        pauseRequested.set(true);
    }

    public void resumeSimulation() {
        if (isPaused) {
            pauseRequested.set(false);
        }
    }
}

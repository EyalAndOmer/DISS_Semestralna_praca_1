package sk.majba.montecarlo.be.simulation_core;

public interface Replication {
    void beforeAllReplications();
    void afterAllReplications();
    void beforeReplication();
    void afterReplication();
    void execute();
}

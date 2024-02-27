package sk.majba.montecarlo.be.simulation_core;

public interface Replication {
    void beforeAllReplications();
    void afterAllReplications();
    void beforeReplications();
    void afterReplication();
    void execute();
}

package sk.majba.montecarlo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.majba.montecarlo.be.HypotekaReplication;
import sk.majba.montecarlo.be.generators.ContinuousEmpiricGenerator;
import sk.majba.montecarlo.be.generators.ContinuousUniformGenerator;
import sk.majba.montecarlo.be.generators.DiscreteUniformGenerator;
import sk.majba.montecarlo.be.generators.Generator;
import sk.majba.montecarlo.be.simulation_core.SimulationCore;
import sk.majba.montecarlo.utils.FileUtils;

import java.io.IOException;
import java.util.*;

public class HelloApplication {
    public static final boolean DEBUG = true;
    public static final int STATIC_SEED = 1;
    public static final double DELTA = 1.0E-12;
//    @Override
//    public void start(Stage stage) throws IOException {
////        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
////        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
////        stage.setTitle("Hello!");
////        stage.setScene(scene);
////        stage.show();
//
//    }

    public static void main(String[] args) {
//        launch();
//
        HypotekaReplication replication = new HypotekaReplication();

        SimulationCore simulationCore = new SimulationCore(replication);

        simulationCore.simulate(100_000_000);
//        Random seedGenerator = new Random();
//        ArrayList<Generator> empiricGenerators =  new ArrayList<>();
//        empiricGenerators.add(new ContinuousUniformGenerator(0.1, 0.3, seedGenerator, 0.1));
//        empiricGenerators.add(new ContinuousUniformGenerator(0.3, 0.8, seedGenerator, 0.35));
//        empiricGenerators.add(new ContinuousUniformGenerator(0.8, 1.2, seedGenerator, 0.2));
//        empiricGenerators.add(new ContinuousUniformGenerator(1.2, 2.5, seedGenerator, 0.15));
//        empiricGenerators.add(new ContinuousUniformGenerator(2.5, 3.8, seedGenerator, 0.15));
//        empiricGenerators.add(new ContinuousUniformGenerator(3.8, 4.8, seedGenerator, 0.05));
//
//        Random seedGenerator = new Random();
//        seedGenerator.setSeed(1);
//
//        DiscreteUniformGenerator empiricGenerator = new DiscreteUniformGenerator(1, 4, seedGenerator);
//
//        List<String> values = new ArrayList<>();
//
//        for (int i = 0; i < 10_000_000; i++) {
//            values.add(String.valueOf(empiricGenerator.generate()));
//        }
//
//        FileUtils.writeDataToFile(values, "C:\\personal\\ING\\2. Semester\\Diskretna Simulacia\\sem1\\MonteCarlo\\src\\main\\resources\\discrete_uniform_data.txt");
    }
}

package sk.majba.montecarlo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.majba.montecarlo.be.HypotekaReplication;
import sk.majba.montecarlo.be.simulation_core.SimulationCore;

import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_window.fxml"));
        Scene scene = new Scene(loader.load(), 640, 480);

        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
//
//        HypotekaReplication replication = new HypotekaReplication();
//
//        SimulationCore simulationCore = new SimulationCore(replication);
//
//        simulationCore.simulate(10_000_000);
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

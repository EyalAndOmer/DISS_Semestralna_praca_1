package sk.majba.montecarlo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.majba.montecarlo.fe.Controller;

import java.io.IOException;


public class MainGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_window.fxml"));
        Scene scene = new Scene(loader.load(), 640, 480);
        Controller controller = loader.getController();
        controller.changeOnExitBehavior(stage);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

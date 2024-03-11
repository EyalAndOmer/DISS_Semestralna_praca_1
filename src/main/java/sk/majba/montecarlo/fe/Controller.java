package sk.majba.montecarlo.fe;

import io.fair_acc.chartfx.XYChart;
import io.fair_acc.chartfx.axes.spi.DefaultNumericAxis;
import io.fair_acc.chartfx.renderer.ErrorStyle;
import io.fair_acc.chartfx.renderer.spi.ErrorDataSetRenderer;
import io.fair_acc.chartfx.ui.geometry.Side;
import io.fair_acc.dataset.spi.DoubleDataSet;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sk.majba.montecarlo.be.HypotekaReplication;
import sk.majba.montecarlo.fe.converters.NumberAxisConverter;
import sk.majba.montecarlo.be.simulation_core.SimulationCore;

import java.util.ArrayList;
import java.util.List;

import static sk.majba.montecarlo.be.Constants.*;

public class Controller {
    @FXML
    public XYChart lineChartStrategy1;
    @FXML
    public XYChart lineChartStrategy2;
    @FXML
    public XYChart lineChartStrategy3;
    @FXML
    public TextField txtNumberOfReplications;
    @FXML
    public Button btnResumeSimulation;
    @FXML
    public Button btnStopAnimation;
    @FXML
    public Button btnStartSimulation;
    @FXML
    public TextField txtMortgageSum;
    @FXML
    public TextField txtSkipFirstValuesSize;
    @FXML
    public TextField txtSkipEachNValuesSize;
    private SimulationCore simulationCore;
    private HypotekaReplication hypotekaReplication;
    private Thread simulationThread;
    List<DoubleDataSet> allDatasets;

    public void initialize() {
        // Chart initialization
        final DefaultNumericAxis xAxis = new DefaultNumericAxis("Počet replikácií", "počet");
        final DefaultNumericAxis yAxis = new DefaultNumericAxis("Zaplatená suma", "euro");
        yAxis.setSide(Side.LEFT);

        final DefaultNumericAxis xAxis2 = new DefaultNumericAxis("Počet replikácií", "počet");
        final DefaultNumericAxis yAxis2 = new DefaultNumericAxis("Zaplatená suma", "euro");
        yAxis2.setSide(Side.LEFT);

        final DefaultNumericAxis xAxis3 = new DefaultNumericAxis("Počet replikácií", "počet");
        final DefaultNumericAxis yAxis3 = new DefaultNumericAxis("Zaplatená suma", "euro");
        yAxis3.setSide(Side.LEFT);

        xAxis.setTickLabelFormatter(new NumberAxisConverter("#,##0.00"));
        yAxis.setTickLabelFormatter(new NumberAxisConverter("#,####0.0000"));

        xAxis2.setTickLabelFormatter(new NumberAxisConverter("#,##0.00"));
        yAxis2.setTickLabelFormatter(new NumberAxisConverter("#,####0.0000"));

        xAxis3.setTickLabelFormatter(new NumberAxisConverter("#,##0.00"));
        yAxis3.setTickLabelFormatter(new NumberAxisConverter("#,####0.0000"));

        this.lineChartStrategy1.getAxes().addAll(xAxis, yAxis);
        this.lineChartStrategy2.getAxes().addAll(xAxis2, yAxis2);
        this.lineChartStrategy3.getAxes().addAll(xAxis3, yAxis3);

// TODO check the pom.xml file and fix dependencies to enable this functionality in semestralka 2
//        lineChartStrategy1.getPlugins().add(new Zoomer());
//        lineChartStrategy2.getPlugins().add(new Zoomer());
//        lineChartStrategy3.getPlugins().add(new Zoomer());

        initDatasets();
        this.hypotekaReplication = new HypotekaReplication(this);
        this.simulationCore = new SimulationCore(hypotekaReplication);

        // Buttons and labels initialization
        this.btnResumeSimulation.setDisable(true);
        this.btnStopAnimation.setDisable(true);

        this.txtSkipFirstValuesSize.setText(String.valueOf(SKIP_FIRST_N_POINTS));
        this.txtSkipEachNValuesSize.setText(String.valueOf(SKIP_EVERY_N_POINTS));
        this.txtMortgageSum.setText(String.valueOf(STARTOVACIA_VYSKA_HYPOTEKARNEHO_UVERU));
        this.txtNumberOfReplications.setText(String.valueOf(INITIAL_NUMBER_OF_REPLICATIONS));
    }

    public void launchSimulation() {
        // If the simulation is started after the first run, then init the datasets again
        if (this.lineChartStrategy1.getRenderers().get(1).getDatasets().get(0).getDataCount() > 0) {
            this.lineChartStrategy1.getRenderers().remove(1);
            this.lineChartStrategy2.getRenderers().remove(1);
            this.lineChartStrategy3.getRenderers().remove(1);

            initDatasets();
        }

        this.btnStopAnimation.setDisable(false);
        this.btnStartSimulation.setDisable(true);
        this.simulationThread = new Thread(() -> {
            try {
                this.simulationCore.simulate(Integer.parseInt(this.txtNumberOfReplications.getText()));
            } finally {
                this.btnStartSimulation.setDisable(false);
                this.btnStopAnimation.setDisable(true);
                Platform.runLater(() -> this.showAlert(Alert.AlertType.INFORMATION, "Ukončenie simulácie", "Simulácia sa ukončila úspešne."));
            }
        });
        this.simulationThread.start();
    }

    private void initDatasets() {
        final DoubleDataSet datasetStrategy1 = new DoubleDataSet("Vyvoj splatenej ciastky");
        final DoubleDataSet datasetStrategy2 = new DoubleDataSet("Vyvoj splatenej ciastky");
        final DoubleDataSet datasetStrategy3 = new DoubleDataSet("Vyvoj splatenej ciastky");

        var lineChart1Renderer = new ErrorDataSetRenderer();
        var lineChart2Renderer = new ErrorDataSetRenderer();
        var lineChart3Renderer = new ErrorDataSetRenderer();

        lineChart1Renderer.setDrawMarker(false);
        lineChart2Renderer.setDrawMarker(false);
        lineChart3Renderer.setDrawMarker(false);

        lineChart1Renderer.setErrorStyle(ErrorStyle.NONE);
        lineChart2Renderer.setErrorStyle(ErrorStyle.NONE);
        lineChart3Renderer.setErrorStyle(ErrorStyle.NONE);

        lineChart1Renderer.getDatasets().addAll(datasetStrategy1);
        lineChart2Renderer.getDatasets().addAll(datasetStrategy2);
        lineChart3Renderer.getDatasets().addAll(datasetStrategy3);

        lineChartStrategy1.getRenderers().add(lineChart1Renderer);
        lineChartStrategy2.getRenderers().add(lineChart2Renderer);
        lineChartStrategy3.getRenderers().add(lineChart3Renderer);

        this.allDatasets = new ArrayList<>(List.of(datasetStrategy1, datasetStrategy2, datasetStrategy3));
    }

    public void stopSimulation() {
        this.simulationCore.pauseSimulation();
        this.btnResumeSimulation.setDisable(false);
        this.btnStopAnimation.setDisable(true);
    }

    public void resumeSimulation() {
        this.btnResumeSimulation.setDisable(true);
        this.btnStopAnimation.setDisable(false);
        this.simulationCore.resumeSimulation();
    }

    public List<DoubleDataSet> getAllDatasets() {
        return allDatasets;
    }

    public int getMortgageSum() {
        return Integer.parseInt(this.txtMortgageSum.getText());
    }

    public int getSkipFirstValuesSize() {
        return Integer.parseInt(this.txtSkipFirstValuesSize.getText());
    }

    public int getSkipEachNValuesSize() {
        return Integer.parseInt(this.txtSkipEachNValuesSize.getText());
    }

    public void changeOnExitBehavior(Stage primaryStage) {
        // When the user exists the app during the simulation, then kill the thread the simulation is running on
        primaryStage.setOnCloseRequest(event -> {
            if (this.simulationThread != null) {
                this.simulationThread.interrupt();
            }

            Platform.runLater(() -> System.exit(0));
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

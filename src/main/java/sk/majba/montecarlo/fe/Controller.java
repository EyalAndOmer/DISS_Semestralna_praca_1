package sk.majba.montecarlo.fe;

import io.fair_acc.chartfx.XYChart;
import io.fair_acc.chartfx.axes.spi.DefaultNumericAxis;
import io.fair_acc.chartfx.plugins.Zoomer;
import io.fair_acc.chartfx.renderer.ErrorStyle;
import io.fair_acc.chartfx.renderer.spi.ErrorDataSetRenderer;
import io.fair_acc.chartfx.ui.geometry.Side;
import io.fair_acc.dataset.spi.DoubleDataSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sk.majba.montecarlo.be.HypotekaReplication;
import sk.majba.montecarlo.fe.converters.NumberAxisConverter;
import sk.majba.montecarlo.be.simulation_core.SimulationCore;

import java.util.ArrayList;
import java.util.List;

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
    private SimulationCore simulationCore;

    public void initialize() {
        final DefaultNumericAxis xAxis = new DefaultNumericAxis("Počet replikácií", "pocet");
        final DefaultNumericAxis yAxis = new DefaultNumericAxis("Zaplatená suma", "euro");
        yAxis.setSide(Side.LEFT);

        final DefaultNumericAxis xAxis2 = new DefaultNumericAxis("Počet replikácií", "pocet");
        final DefaultNumericAxis yAxis2 = new DefaultNumericAxis("Zaplatená suma", "euro");
        yAxis2.setSide(Side.LEFT);

        final DefaultNumericAxis xAxis3 = new DefaultNumericAxis("Počet replikácií", "pocet");
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


        lineChartStrategy1.getPlugins().add(new Zoomer());
        lineChartStrategy2.getPlugins().add(new Zoomer());
        lineChartStrategy3.getPlugins().add(new Zoomer());

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

        List<DoubleDataSet> allDatasets = new ArrayList<>(List.of(datasetStrategy1, datasetStrategy2, datasetStrategy3));
        HypotekaReplication hypotekaReplication = new HypotekaReplication(allDatasets);
        this.simulationCore = new SimulationCore(hypotekaReplication);

        this.btnResumeSimulation.setDisable(true);
        this.btnStopAnimation.setDisable(true);
    }

    public void launchSimulation() {
        this.btnStopAnimation.setDisable(false);
        this.btnStartSimulation.setDisable(true);
        new Thread(() -> {
            try {
                this.simulationCore.simulate(Integer.parseInt(this.txtNumberOfReplications.getText()));
            } finally {
                this.btnStartSimulation.setDisable(false);
            }
        }).start();
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
}

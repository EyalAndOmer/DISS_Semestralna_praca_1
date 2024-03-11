module sk.majba {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires io.fair_acc.chartfx;
    requires io.fair_acc.dataset;

    opens sk.majba.montecarlo to javafx.fxml;
    opens sk.majba.montecarlo.fe to javafx.fxml;
    exports sk.majba.montecarlo.fe to javafx.fxml, javafx.graphics;
    exports sk.majba.montecarlo to javafx.fxml, javafx.graphics;
}

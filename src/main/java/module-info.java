module com.example.montecarlo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens sk.majba.montecarlo to javafx.fxml;
    exports sk.majba.montecarlo;
    exports sk.majba.montecarlo.fe;
    opens sk.majba.montecarlo.fe to javafx.fxml;
}

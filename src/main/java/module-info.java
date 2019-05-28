module gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;

    opens de.hdm_stuttgart.sd2.battleshipproject to javafx.fxml;
    exports de.hdm_stuttgart.mi.sd2;
}
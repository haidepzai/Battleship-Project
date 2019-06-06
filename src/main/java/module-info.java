module de.hdm_stuttgart.mi.sd2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires kotlin.stdlib;

    opens de.hdm_stuttgart.mi.sd2.Gui to javafx.fxml, org.apache.logging.log4j;
    exports de.hdm_stuttgart.mi.sd2.Gui;
}

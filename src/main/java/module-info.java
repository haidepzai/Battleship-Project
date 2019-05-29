module de.hdm_stuttgart.mi.sd2 {                     //package-Name
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;

    opens de.hdm_stuttgart.mi.sd2 to javafx.fxml, org.apache.logging.log4j;
    exports de.hdm_stuttgart.mi.sd2;
}

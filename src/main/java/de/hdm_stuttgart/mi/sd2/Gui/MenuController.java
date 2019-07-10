package de.hdm_stuttgart.mi.sd2.Gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;

public class MenuController {
    private static Logger log = LogManager.getLogger(MenuController.class);

    @FXML
    Button playButton;
    @FXML
    Button exitButton;

    @FXML
    public void goToShipPlacement() throws IOException {
        GuiDriver.getApplication().setScene("/fxml/ShipPlacement.fxml", "Ship-Placement", 600, 450);
    }

    @FXML
    public void exit() {
        log.debug("Application terminated.");
        System.exit(0);
    }
}

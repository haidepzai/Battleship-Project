package de.hdm_stuttgart.mi.sd2.Gui;

import javafx.fxml.FXML;

import java.io.IOException;

public class MenuController {

    @FXML
    public void goToShipPlacement() throws IOException {
        GuiDriver.getApplication().setScene("/fxml/ShipPlacement.fxml", "Ship-Placement", 600, 450);
    }

    @FXML
    public void exit() {
        GuiDriver.log.debug("Application terminated.");
        System.exit(0);
    }
}

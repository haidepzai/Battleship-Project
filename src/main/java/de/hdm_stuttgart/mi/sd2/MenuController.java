package de.hdm_stuttgart.mi.sd2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MenuController {

    @FXML
    private void goToShipPlacement(ActionEvent event) throws IOException {
        GuiDriver.getApplication().setScene("/fxml/ShipPlacement.fxml", "Ship-Placement", 600, 450);
    }

    @FXML
    private void exit(ActionEvent event) {
        System.exit(0);
    }
}

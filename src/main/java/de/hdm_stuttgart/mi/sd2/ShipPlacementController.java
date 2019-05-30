package de.hdm_stuttgart.mi.sd2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

public class ShipPlacementController {

    @FXML
    public GridPane playerGrid;

    @FXML
    public Label infoLabel;


    @FXML
    public void initialize() {
        //Filling the tables at one to nine
        for (int i = 1; i <= 9; i++) {
            Label l = new Label();
            l.setId("oneToNine");
            l.setText(Integer.toString(i));

            playerGrid.add(l, 0, i);
        }


        //filling the tables at a to i
        int e = 65;
        for (int i = 1; i <= 9; i++) {
            if (e == 73) {
                Label l = new Label();
                l.setId("aToIjustI");
                l.setText(Character.toString((char) e));
                playerGrid.add(l, i, 0);
            } else {
                Label l = new Label();
                Label l2 = new Label();
                l.setId("aToI");
                l.setText(Character.toString((char) e));
                playerGrid.add(l, i, 0);
                e++;
            }
        }

        for (int i = 1; i <= 9; i++) {

            for (int j = 1; j <= 9; j++) {

                Button b = new Button();
                b.setMaxSize(100, 100);
                b.setId("water");
                playerGrid.add(b, i, j);

            }
        }

        infoLabel.setText("Please set your ships!");

    }

    //todo: wenn alle Schiffe gesetzt => GuiDriver.getApplication().setScene("/fxml/BattlePhase.fxml", "BattlePhase", 1001, 559);
    /*@FXML
    private void goToBattlePhase(ActionEvent event) throws IOException {
        GuiDriver.getApplication().setScene("/fxml/BattlePhase.fxml", "BattlePhase", 1001, 559);
    }
    */

}


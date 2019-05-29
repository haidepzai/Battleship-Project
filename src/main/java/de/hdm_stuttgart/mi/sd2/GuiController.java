package de.hdm_stuttgart.mi.sd2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


public class GuiController {

    @FXML
    private GridPane playerGrid;
    @FXML
    private GridPane enemyGrid;



    @FXML
    public void initialize() {

        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                Button b = new Button();
                b.setMaxSize(100,100);
                playerGrid.add(b, i, j);
                Button b2 = new Button();

                b2.setMaxSize(100,100);
                b2.setId(Integer.toBinaryString(i + j));
                enemyGrid.add(b2, i, j);
            }
        }
    }
    public void changeColorShot(ActionEvent event) {


    }
}
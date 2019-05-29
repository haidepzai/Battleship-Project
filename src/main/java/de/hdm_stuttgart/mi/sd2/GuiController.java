package de.hdm_stuttgart.mi.sd2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class GuiController {

    @FXML
    private GridPane playerGrid;
    @FXML
    private GridPane enemyGrid;

    private Button b = new Button();
    private Button b2 = new Button();

    @FXML
    public void initialize() {
        //Filling the tables at one to nine
        for (int i = 1; i <= 9; i++) {
            Label l = new Label();
            l.setId("oneToNine");
            l.setText(Integer.toString(i));

            playerGrid.add(l, 0, i);
            Label l2 = new Label();
            l2.setId("oneToNine");
            l2.setText(Integer.toString(i));
            enemyGrid.add(l2, 0, i);
        }




        //filling the tables at a to i
            int e = 65;
            for (int i = 1; i <= 9; i++) {
                if(e == 73) {
                    Label l = new Label();
                    l.setId("aToIjustI");
                    l.setText(Character.toString((char) e));
                    playerGrid.add(l, i, 0);
                    Label l2 = new Label();
                    l2.setId("aToIjustI");
                    l2.setText(Character.toString((char) e));
                    enemyGrid.add(l2, i, 0);
                } else {
                Label l = new Label();
                Label l2 = new Label();
                l.setId("aToI");
                l2.setId("aToI");
                l.setText(Character.toString((char) e));
                l2.setText(Character.toString((char) e));
                playerGrid.add(l, i, 0);
                enemyGrid.add(l2, i, 0);
                e++;
                }
            }

        for (int i = 1; i <= 9; i++) {

            for (int j = 1; j <= 9; j++) {

                b.setMaxSize(100, 100);
                playerGrid.add(b, i, j);

                b.setId("water");
                b2.setId("water");

                b2.setMaxSize(100, 100);
                b2.setId(Integer.toBinaryString(i + j));
                enemyGrid.add(b2, i, j);
            }
        }
    }

    public void changeColorShot(ActionEvent event) {
        event.getSource();


    }
}
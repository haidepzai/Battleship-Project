package de.hdm_stuttgart.mi.sd2;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

@SuppressWarnings("Duplicates")
public class BattlePhaseController {

    //ShipPlacementController spc = new ShipPlacementController();

    final int MAPSIZE = ShipPlacementController.MAPSIZE;

    @FXML
    GridPane playerGrid;
    @FXML
    GridPane enemyGrid;

//todo: easier way to transfer playerGrid from ShipPlacementController??!?!?!?!?!?!?


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
            if (e == 73) {
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

                Button b = new Button();
                b.setMaxSize(100, 100);
                b.getStyleClass().add("waterButton");
                playerGrid.add(b, i, j);
                Button b2 = new Button();

                b2.setMaxSize(100, 100);
                b2.getStyleClass().add("waterButton");
                enemyGrid.add(b2, i, j);
            }
        }


        for (int r = 1; r <= MAPSIZE; r++) {
            for (int c = 1; c <= MAPSIZE; c++) {
                //List all children of GridPane => all Nodes (BUTTONS, labels, etc.)
                ObservableList<Node> children = playerGrid.getChildren();
                //begin at i=1 because first child causes NullPointerException => has no Row-/ColumnIndex
                for (int i = 1; i < children.size(); i++) {

                    if (ShipPlacementController.playerMap.getStatus(r, c) == Field.SHIP && GridPane.getRowIndex(children.get(i)) == r && GridPane.getColumnIndex(children.get(i)) == c) {
                        children.get(i).setStyle("-fx-background-color: black");
                    }


                }
            }

        }

    }

}
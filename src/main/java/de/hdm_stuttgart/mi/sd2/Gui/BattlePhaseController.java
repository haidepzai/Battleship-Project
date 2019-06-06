package de.hdm_stuttgart.mi.sd2.Gui;

import de.hdm_stuttgart.mi.sd2.Field;
import de.hdm_stuttgart.mi.sd2.aiRandom;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

@SuppressWarnings("Duplicates")
public class BattlePhaseController {

    final int MAPSIZE = ShipPlacementController.MAPSIZE;



    @FXML
    GridPane playerGrid;
    @FXML
    GridPane enemyGrid;
    @FXML
    Label infoLabelCF;
    @FXML
    Label infoLabelPF;
    @FXML
    Pane popUp;
    @FXML
    Label gameWin;
    @FXML
    Pane backPane;
    @FXML
    Button nextTurnB;


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

                Button bP = new Button();
                bP.setMaxSize(100, 100);
                bP.getStyleClass().add("waterButton");
                playerGrid.add(bP, i, j);

                Button bC = new Button();
                bC.setMaxSize(100, 100);
                bC.getStyleClass().add("waterButton");
                enemyGrid.add(bC, i, j);
                bC.setOnAction(event -> {
                    int row = GridPane.getRowIndex(bC);
                    int col = GridPane.getColumnIndex(bC);

                    GuiDriver.log.debug("Player's attack phase");
                    if (ShipPlacementController.computerMap.getStatus(row, col) == Field.SHOT || ShipPlacementController.computerMap.getStatus(row, col) == Field.HIT) {
                        infoLabelCF.setText("Position has already been shot! Try again!");
                    } else {
                        ShipPlacementController.computerMap.attack(row, col);
                        if (ShipPlacementController.computerMap.getStatus(row, col) == Field.HIT) {
                            bC.setStyle("-fx-background-color: green");
                            if (ShipPlacementController.computerMap.checkShipState(row, col)) {
                                if (ShipPlacementController.computerFleet > 1) {
                                    ShipPlacementController.computerFleet--;
                                    infoLabelCF.setText("You have destroyed a ship! Computer has " + (ShipPlacementController.computerFleet) + " left.");
                                } else {
                                    infoLabelCF.setText("You destroyed every ship of the enemy!!!");
                                    playerGrid.setDisable(true);
                                    enemyGrid.setDisable(true);
                                    popUp.setDisable(false);
                                    popUp.setVisible(true);
                                }
                            } else {
                                infoLabelCF.setText("You hit a ship! You have another shot!");
                            }
                        } else {
                            bC.setStyle("-fx-background-color: red");
                            infoLabelCF.setText("Missed! Your turn is finished.");
                            enemyGrid.setDisable(true);
                            playerGrid.setDisable(false);
                            setAIShoot();
                        }
                    }
                });
            }
        }

        //Color ships from ShipPlacementController
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

        playerGrid.setDisable(true);

//        //Color ships from AI - to TEST
//        for (int r = 1; r <= MAPSIZE; r++) {
//            for (int c = 1; c <= MAPSIZE; c++) {
//                //List all children of GridPane => all Nodes (BUTTONS, labels, etc.)
//                ObservableList<Node> children = enemyGrid.getChildren();
//                //begin at i=1 because first child causes NullPointerException => has no Row-/ColumnIndex
//                for (int i = 1; i < children.size(); i++) {
//
//                    if (ShipPlacementController.computerMap.getStatus(r, c) == Field.SHIP && GridPane.getRowIndex(children.get(i)) == r && GridPane.getColumnIndex(children.get(i)) == c) {
//                        children.get(i).setStyle("-fx-background-color: black");
//                    }
//
//
//                }
//            }
//
//        }
    }

    /**
     * Manages the shoot-events of the computer -> Random actions
     */
    @FXML
    public void setAIShoot() {
        //count: for later print out, that clarifies how often computer shot
        int count = 1;
        GuiDriver.log.debug("Computer's attack phase");
        while (true) {
            int ranRow = aiRandom.randNumber(MAPSIZE);
            int ranCol = aiRandom.randNumber(MAPSIZE);
            if (ShipPlacementController.playerMap.getStatus(ranRow, ranCol) == Field.SHOT || ShipPlacementController.playerMap.getStatus(ranRow, ranCol) == Field.HIT) {
                GuiDriver.log.trace("Computer has already shot position (" + ranRow + ", " + ranCol + ")");
            } else {
                ShipPlacementController.playerMap.attack(ranRow, ranCol);
                if (ShipPlacementController.playerMap.getStatus(ranRow, ranCol) == Field.HIT) {
                    GuiDriver.log.trace("Computer has hit a ship at (" + ranRow + ", " + ranCol + ")");
                    ObservableList<Node> children = playerGrid.getChildren();
                    //begin at i=1 because first child causes NullPointerException => has no Row-/ColumnIndex
                    for (int i = 1; i < children.size(); i++) {
                        if (GridPane.getRowIndex(children.get(i)) == ranRow && GridPane.getColumnIndex(children.get(i)) == ranCol) {
                            children.get(i).setStyle("-fx-background-color: green");
                        }
                    }
                    if (ShipPlacementController.playerMap.checkShipState(ranRow, ranCol)) {

                        if (ShipPlacementController.playerFleet == 0) {
                            infoLabelPF.setText("The computer destroyed all of your ships!!!");
                            gameWin.setText("The computer won the game!");

                            playerGrid.setDisable(true);
                            enemyGrid.setDisable(true);
                            backPane.setStyle("-fx-opacity: 0.3");
                            popUp.setDisable(false);
                            popUp.setVisible(true);
                        } else {
                            infoLabelPF.setText("Computer has destroyed a ship! You have " + ShipPlacementController.playerFleet + " left.");

                        }
                        ShipPlacementController.playerFleet--;
                    } else {
                        infoLabelPF.setText("Computer hit a ship and could shoot again!");
                        count++;
                    }
                } else {
                    GuiDriver.log.trace("Computer missed! Position: (" + ranRow + ", " + ranCol + "). Turn finished.");
                    if (count > 1) {
                        infoLabelPF.setText("Computer hit a ship and shot " + count + " times! Round finished!");
                        ObservableList<Node> children = playerGrid.getChildren();
                        //begin at i=1 because first child causes NullPointerException => has no Row-/ColumnIndex
                        for (int i = 1; i < children.size(); i++) {

                            if (GridPane.getRowIndex(children.get(i)) == ranRow && GridPane.getColumnIndex(children.get(i)) == ranCol) {
                                children.get(i).setStyle("-fx-background-color: red");
                            }

                        }
                        break;
                    } else {
                        infoLabelPF.setText("Computer missed! Round finished!");
                        ObservableList<Node> children = playerGrid.getChildren();
                        //begin at i=1 because first child causes NullPointerException => has no Row-/ColumnIndex
                        for (int i = 1; i < children.size(); i++) {

                            if (GridPane.getRowIndex(children.get(i)) == ranRow && GridPane.getColumnIndex(children.get(i)) == ranCol) {
                                children.get(i).setStyle("-fx-background-color: red");
                            }

                        }
                        break;
                    }
                }
            }
        }
        nextTurnB.setDisable(false);
    }

    /**
     * Handles next-turn-event
     *
     * @param event ActionEvent: ButtonClick
     */
    @FXML
    public void nextTurn(ActionEvent event) {
        nextTurnB.setDisable(true);
        enemyGrid.setDisable(false);
        playerGrid.setDisable(true);
        infoLabelPF.setText("");
        infoLabelCF.setText("It's your turn! Set your shot!");
    }

    /**
     * Go back to menu after game is finished and clears the fields
     *
     * @param event ActionEvent: ButtonClick
     * @throws Exception for setScene-event
     */
    @FXML
    public void goToMenu(ActionEvent event) throws IOException {
        ShipPlacementController.playerMap.clearField();
        ShipPlacementController.computerMap.clearField();
        GuiDriver.getApplication().setScene("/fxml/Menu.fxml", "Menu", 600, 400);
    }


}



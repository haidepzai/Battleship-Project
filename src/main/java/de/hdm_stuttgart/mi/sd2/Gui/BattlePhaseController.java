package de.hdm_stuttgart.mi.sd2.Gui;

import de.hdm_stuttgart.mi.sd2.Field;
import de.hdm_stuttgart.mi.sd2.aiRandom;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@SuppressWarnings("Duplicates")
public class BattlePhaseController {

    private static final Logger log = LogManager.getLogger(BattlePhaseController.class);

    @FXML
    private GridPane playerGrid;
    @FXML
    private GridPane enemyGrid;
    @FXML
    private Label infoLabelCF;
    @FXML
    private Label infoLabelPF;
    @FXML
    private Pane popUp;
    @FXML
    private Label gameWin;
    @FXML
    private Pane backPane;
    @FXML
    private Button nextTurnB;
    @FXML
    private ImageView winLostImage;


    public void initialize() {
        //Filling the Table-Labels from 1-9
        for (int i = 1; i <= 9; i++) {
            Label l = new Label();
            l.setId("coordinatesB");
            l.setText(Integer.toString(i));
            playerGrid.add(l, 0, i);
            log.debug(l + " added to \"playerGrid\"");
        }
        for (int i = 1; i <= 9; i++) {
            Label l2 = new Label();
            l2.setId("coordinatesB");
            l2.setText(Integer.toString(i));
            enemyGrid.add(l2, 0, i);
            log.debug(l2 + " added to \"enemyGrid\"");
        }

        //filling the Table-Labels from A-I
        int e = 65;
        for (int i = 1; i <= 9; i++) {
            Label l = new Label();
            Label l2 = new Label();
            l.setId("coordinatesB");
            l2.setId("coordinatesB");
            l.setText(Character.toString((char) e));
            l2.setText(Character.toString((char) e));
            playerGrid.add(l, i, 0);
            log.debug(l + " added to \"playerGrid\"");
            enemyGrid.add(l2, i, 0);
            log.debug(l2 + " added to \"enemyGrid\"");
            e++;
        }
        for (int i = 1; i <= 9; i++) {

            for (int j = 1; j <= 9; j++) {

                Button bP = new Button();
                bP.setMaxSize(100, 100);
                bP.getStyleClass().add("waterButton");
                bP.setId(i + ", " + j);
                playerGrid.add(bP, i, j);
                log.debug(bP + " added to \"playerGrid\"");

                Button bC = new Button();
                bC.setMaxSize(100, 100);
                bC.getStyleClass().add("waterButton");
                bC.setId(i + ", " + j);
                enemyGrid.add(bC, i, j);
                log.debug(bC + " added to \"enemyGrid\"");
                log.info("Players turn to attack!");
                bC.setOnAction(event -> {
                    int row = GridPane.getRowIndex(bC);
                    int col = GridPane.getColumnIndex(bC);

                    if (ShipPlacementController.computerMap.getStatus(row, col) == Field.SHOT || ShipPlacementController.computerMap.getStatus(row, col) == Field.HIT) {
                        infoLabelCF.setText("Position has already been shot! Try again!");
                        log.debug("Player already shot at [" + row + "][" + col + "] (Status: " + Field.SHOT + " )");
                    } else {
                        ShipPlacementController.computerMap.attack(row, col);
                        if (ShipPlacementController.computerMap.getStatus(row, col) == Field.HIT) {
                            bC.setStyle("-fx-background-color: green");
                            log.debug("Player hit a ship at [" + row + "][" + col + "] (Status: " + Field.HIT + ")");
                            if (ShipPlacementController.computerMap.checkShipState(row, col)) {
                                log.debug("Player destroyed a ship by shooting at [" + row + "][" + col + "] (Status: " + Field.HIT + ")");
                                if (ShipPlacementController.computerFleet > 1) {
                                    ShipPlacementController.computerFleet--;
                                    log.debug("Player destroyed an enemies ship. \"computerFleet\" reduced by 1 -> New state: " + ShipPlacementController.computerFleet);
                                    infoLabelCF.setText("You have destroyed a ship! Computer has " + (ShipPlacementController.computerFleet) + " left.");
                                } else {
                                    playerWins();
                                }
                            } else {
                                infoLabelCF.setText("You hit a ship! You have another Shot!");
                            }
                        } else {
                            bC.setStyle("-fx-background-color: red");
                            infoLabelCF.setText("Missed! Your turn is finished.");
                            enemyGrid.setDisable(true);
                            playerGrid.setDisable(false);
                            log.debug("Player shot at [" + row + "][" + col + "] (Status: " + Field.WATER + ")");
                            setAIShoot();
                        }
                    }
                });
            }
        }
        //Color placed ships from ShipPlacementController
        ObservableList<Node> children = playerGrid.getChildren();
        children
                .stream()
                .filter(child -> child instanceof Button)
                .forEach(child -> {
                    for (int row = 1; row <= ShipPlacementController.MAPSIZE; row++) {
                        for (int col = 1; col <= ShipPlacementController.MAPSIZE; col++) {
                            if (ShipPlacementController.playerMap.getStatus(row, col) == Field.SHIP && GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
                                child.setStyle("-fx-background-color: black");
                            }
                        }
                    }
                });

        playerGrid.setDisable(true);
    }

    /**
     * Manages the shoot-events of the computer -> Random actions
     */

    private void setAIShoot() {
        //count: for later print out, that clarifies how often computer shot
        int count = 1;
        log.info("Computer's attack phase");
        while (true) {
            int ranRow = aiRandom.randNumber(ShipPlacementController.MAPSIZE);
            int ranCol = aiRandom.randNumber(ShipPlacementController.MAPSIZE);
            if (ShipPlacementController.playerMap.getStatus(ranRow, ranCol) == Field.SHOT || ShipPlacementController.playerMap.getStatus(ranRow, ranCol) == Field.HIT) {
                log.debug("Computer has already shot at [" + ranRow + "][" + ranCol + "] (Status: " + Field.SHOT + ") and shoots again.");
            } else {
                ShipPlacementController.playerMap.attack(ranRow, ranCol);
                if (ShipPlacementController.playerMap.getStatus(ranRow, ranCol) == Field.HIT) {
                    log.debug("Computer has hit a ship at [" + ranRow + "][" + ranCol + "] (Status: " + Field.HIT + ")");
                    ObservableList<Node> children = playerGrid.getChildren();
                    children
                            .stream()
                            .filter(child -> child instanceof Button)
                            .forEach(child -> {
                                if (GridPane.getRowIndex(child) == ranRow && GridPane.getColumnIndex(child) == ranCol) {
                                    child.setStyle("-fx-background-color: green");
                                }
                            });

                    if (ShipPlacementController.playerMap.checkShipState(ranRow, ranCol)) {

                        ShipPlacementController.playerFleet--;
                        log.debug("Computer destroyed a ship. \"playerFleet\" reduced by 1 -> New state: " + ShipPlacementController.playerFleet);

                        if (ShipPlacementController.playerFleet == 0) {
                            computerWins();
                        } else {
                            infoLabelPF.setText("Computer has destroyed a ship! You have " + ShipPlacementController.playerFleet + " left.");
                        }

                    } else {
                        infoLabelPF.setText("Computer hit a ship and could shoot again!");
                        count++;
                    }
                } else {
                    log.debug("Computer missed! Position: [" + ranRow + "][" + ranCol + "] (Status: " + Field.WATER + ")");
                    if (count > 1) {
                        infoLabelPF.setText("Computer missed after shooting " + count + " times! Round finished!");
                    } else {
                        infoLabelPF.setText("Computer missed! Round finished!");
                    }
                    ObservableList<Node> children = playerGrid.getChildren();
                    children
                            .stream()
                            .filter(child -> child instanceof Button)
                            .forEach(child -> {
                                if (GridPane.getRowIndex(child) == ranRow && GridPane.getColumnIndex(child) == ranCol) {
                                    child.setStyle("-fx-background-color: red");
                                }
                            });
                    log.info("Players turn to attack!");
                    break;
                }
            }
        }
        nextTurnB.setDisable(false);
    }

    private void playerWins() {
        log.info("Player won the game");
        infoLabelCF.setText("You destroyed every ship of the enemy!!!");
        playerGrid.setDisable(true);
        enemyGrid.setDisable(true);
        gameWin.setText("You won the game! Game finished.");
        Image image = new Image("file:src/main/resources/pictures/trophy.png");
        winLostImage.setImage(image);
        winLostImage.maxHeight(100);
        winLostImage.maxWidth(100);
        popUp.setDisable(false);
        popUp.setVisible(true);
    }

    private void computerWins() {
        log.info("Computer won the game");
        infoLabelPF.setText("The computer destroyed all of your ships!!!");
        playerGrid.setDisable(true);
        enemyGrid.setDisable(true);
        backPane.setStyle("-fx-opacity: 0.3");
        gameWin.setText("You lost, the computer won the game! Game finished.");
        Image image = new Image("file:src/main/resources/pictures/lost.png");
        winLostImage.setImage(image);
        winLostImage.maxHeight(100);
        winLostImage.maxWidth(100);
        popUp.setDisable(false);
        popUp.setVisible(true);
    }

    /**
     * Handles next-turn-event
     */

    public void nextTurn() {
        nextTurnB.setDisable(true);
        enemyGrid.setDisable(false);
        playerGrid.setDisable(true);
        infoLabelPF.setText("");
        infoLabelCF.setText("It's your turn! Set your shot!");
    }

    /**
     * Go back to menu after game is finished and clears the fields
     *
     * @throws IOException for setScene-event
     */
    @FXML
    public void goToMenu() throws IOException {
        GuiDriver.getApplication().setScene("/fxml/Menu.fxml", "Menu", 600, 400);
        log.info("Returned to Menu, after game finished.");
    }


}



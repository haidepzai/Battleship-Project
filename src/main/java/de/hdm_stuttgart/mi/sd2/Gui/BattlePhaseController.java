package de.hdm_stuttgart.mi.sd2.Gui;

import de.hdm_stuttgart.mi.sd2.Field;
import de.hdm_stuttgart.mi.sd2.aiRandom;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static de.hdm_stuttgart.mi.sd2.Gui.ShipPlacementController.MAPSIZE;

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

    private static int playerFleet;
    private static int computerFleet;

    private static Field playerMap = new Field(MAPSIZE);
    private static Field computerMap = new Field(MAPSIZE);

    static void setPlayerFleet(int playerFleet) {
        BattlePhaseController.playerFleet = playerFleet;
    }

    static void setComputerFleet(int computerFleet) {
        BattlePhaseController.computerFleet = computerFleet;
    }

    static void setPlayerMap(Field playerMap) {
        BattlePhaseController.playerMap = playerMap;
    }

    static void setComputerMap(Field computerMap) {
        BattlePhaseController.computerMap = computerMap;
    }

    public void initialize() {

        fillLabels();

        createFieldButtons();

        colorPlacedShips();

        playerGrid.setDisable(true);
    }

    /**
     * Filling coordinate labels for game-fields
     */
    private void fillLabels() {
        //column coordinates
        for (int i = 1; i <= 9; i++) {
            Label l = new Label();
            Label l2 = new Label();
            l.setId("coordinatesB");
            l2.setId("coordinatesB");
            l.setText(Integer.toString(i));
            l2.setText(Integer.toString(i));
            playerGrid.add(l, 0, i);
            log.trace(l + " added to \"playerGrid\"");
            enemyGrid.add(l2, 0, i);
            log.trace(l2 + " added to \"enemyGrid\"");
        }

        //row coordinates
        for (int i = 1; i <= 9; i++) {
            Label l = new Label();
            Label l2 = new Label();
            l.setId("coordinatesB");
            l2.setId("coordinatesB");
            l.setText(Integer.toString(i));
            l2.setText(Integer.toString(i));
            playerGrid.add(l, i, 0);
            log.trace(l + " added to \"playerGrid\"");
            enemyGrid.add(l2, i, 0);
            log.trace(l2 + " added to \"enemyGrid\"");
        }
    }

    /**
     * Create the interactive game-field by filling buttons into GridPane
     */
    private void createFieldButtons() {
        for (int i = 1; i <= 9; i++) {

            for (int j = 1; j <= 9; j++) {

                Button bP = new Button();
                bP.setMaxSize(100, 100);
                bP.getStyleClass().add("waterButton");
                bP.setId(i + ", " + j);
                playerGrid.add(bP, i, j);
                log.trace(bP + " added to \"playerGrid\"");

                Button bC = new Button();
                bC.setMaxSize(100, 100);
                bC.getStyleClass().add("waterButton");
                bC.setId(i + ", " + j);
                enemyGrid.add(bC, i, j);
                log.trace(bC + " added to \"enemyGrid\"");
                bC.setOnAction(event -> {
                    int row = GridPane.getRowIndex(bC);
                    int col = GridPane.getColumnIndex(bC);

                    if (computerMap.getStatus(row, col) == Field.SHOT || computerMap.getStatus(row, col) == Field.HIT) {
                        infoLabelCF.setText("Position has already been shot! Try again!");
                        log.debug("Player already shot at [" + row + "][" + col + "] (Status: " + Field.SHOT + " )");
                    } else {
                        computerMap.attack(row, col);
                        if (computerMap.getStatus(row, col) == Field.HIT) {
                            bC.setStyle("-fx-background-color: green");
                            log.debug("Player hit a ship at [" + row + "][" + col + "] (Status: " + Field.HIT + ")");
                            if (computerMap.checkShipState(row, col)) {
                                log.debug("Player destroyed a ship by shooting at [" + row + "][" + col + "] (Status: " + Field.HIT + ")");
                                if (computerFleet > 1) {
                                    computerFleet--;
                                    log.debug("Player destroyed an enemies ship. \"computerFleet\" reduced by 1 -> New state: " + computerFleet);
                                    infoLabelCF.setText("You have destroyed a ship! Computer has " + (computerFleet) + " left.");
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
    }

    /**
     * Color placed ships from ShipPlacementController, by using ParallelStream
     */
    private void colorPlacedShips() {
        log.debug("Coloring placed ships from ship-placement-scene");
        playerGrid.getChildren()
                .parallelStream()
                .filter(child -> child instanceof Button)
                .forEach(child -> {
                    for (int row = 1; row <= MAPSIZE; row++) {
                        for (int col = 1; col <= MAPSIZE; col++) {
                            if (playerMap.getStatus(row, col) == Field.SHIP && GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
                                child.setStyle("-fx-background-color: black");
                            }
                        }
                    }
                });
    }

    /**
     * Colors buttons of player's field depending on whether the computer has hit a ship or missed
     * @param stateColor Green: Hit, Red: Missed
     * @param row Row of shot position
     * @param col Column of shot position
     */
    private void colorAIShots(String stateColor, int row, int col) {
        playerGrid.getChildren()
                .stream()
                .filter(child -> child instanceof Button)
                .forEach(child -> {
                    if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col) {
                        child.setStyle(stateColor);
                    }
                });
    }

    /**
     * Manages the shoot-events of the computer -> Random actions
     */
    private void setAIShoot() {
        //count: for later print out, that clarifies how often computer has shot
        int count = 0;
        log.info("Computer's attack phase");
        while (true) {
            int ranRow = aiRandom.randNumber(MAPSIZE);
            int ranCol = aiRandom.randNumber(MAPSIZE);
            if (playerMap.getStatus(ranRow, ranCol) == Field.SHOT || playerMap.getStatus(ranRow, ranCol) == Field.HIT) {
                log.debug("Computer has already shot at [" + ranRow + "][" + ranCol + "] (Status: " + Field.SHOT + ") and shoots again.");
            } else {
                playerMap.attack(ranRow, ranCol);
                if (playerMap.getStatus(ranRow, ranCol) == Field.HIT) {
                    log.debug("Computer has hit a ship at [" + ranRow + "][" + ranCol + "] (Status: " + Field.HIT + ")");
                    colorAIShots("-fx-background-color: green", ranRow, ranCol);
                    if (playerMap.checkShipState(ranRow, ranCol)) {
                        playerFleet--;
                        log.debug("Computer destroyed a ship. \"playerFleet\" reduced by 1 -> New state: " + playerFleet);
                        if (playerFleet == 0) {
                            computerWins();
                        } else {
                            infoLabelPF.setText("Computer has destroyed a ship! You have " + playerFleet + " left.");
                        }
                    } else {
                        log.debug("Computer shots again!");
                        count++;
                    }
                } else {
                    log.debug("Computer missed! Position: [" + ranRow + "][" + ranCol + "] (Status: " + Field.WATER + ") Shots this round: " + count);
                    if (count >= 1) {
                        infoLabelPF.setText("Computer missed after shooting " + count + " time(s)! Round finished!");
                    } else {
                        infoLabelPF.setText("Computer missed! Round finished!");
                    }
                    colorAIShots("-fx-background-color: red", ranRow, ranCol);
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
        gameWin.setText("You lost the game! Game finished.");
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



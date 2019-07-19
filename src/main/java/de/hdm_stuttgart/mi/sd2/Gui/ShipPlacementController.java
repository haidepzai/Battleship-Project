package de.hdm_stuttgart.mi.sd2.Gui;

import de.hdm_stuttgart.mi.sd2.Field;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Threads.ShipListAICreator;
import de.hdm_stuttgart.mi.sd2.Threads.ShipListCreator;
import de.hdm_stuttgart.mi.sd2.aiRandom;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("Duplicates")
public class ShipPlacementController {

    private static final Logger log = LogManager.getLogger(ShipPlacementController.class);

    private static List<IShip> shipList = new ArrayList<>();
    private static List<IShip> shipListAI = new ArrayList<>();

    public static final int MAPSIZE = 9;

    private static int playerFleet;
    private static int computerFleet;

    private static Field playerMap = new Field(MAPSIZE);
    private static Field computerMap = new Field(MAPSIZE);

    @FXML
    private GridPane playerGrid;
    @FXML
    private Label infoLabel;
    @FXML
    private RadioButton horizontal;
    @FXML
    private RadioButton vertical;
    @FXML
    private Pane popUp;
    @FXML
    private Pane radioPane;
    @FXML
    private Pane backPane;

    final private ToggleGroup group = new ToggleGroup();

    public static void setShipList(List<IShip> shipList) {
        ShipPlacementController.shipList = shipList;
    }

    public static void setShipListAI(List<IShip> shipListAI) {
        ShipPlacementController.shipListAI = shipListAI;
    }

    public static List<IShip> getShipList() {
        List<IShip> shipList;
        shipList = ShipPlacementController.shipList;
        return shipList;
    }

    public static List<IShip> getShipListAI() {
        List<IShip> shipListAI;
        shipListAI = ShipPlacementController.shipListAI;
        return shipListAI;
    }

    public void initialize() throws InterruptedException {
        playerMap.clearField();
        computerMap.clearField();
        //Adding all needed ships to the specific lists by using two threads (player, computer)
        Thread shipListCreator = new Thread(new ShipListCreator());
        log.debug("Thread \"shipListCreator\" created.");
        Thread shipListAICreator = new Thread(new ShipListAICreator());
        log.debug("Thread \"shipListAICreator\" created.");
        shipListCreator.start();
        shipListAICreator.start();
        shipListCreator.join();
        shipListAICreator.join();
        Field.setShipLists(shipList, shipListAI);
        playerFleet = shipList.size();
        computerFleet = shipListAI.size();

        horizontal.setToggleGroup(group);
        vertical.setToggleGroup(group);
        horizontal.setSelected(true);

        setLabels();

        createFieldButtons();

        infoLabel.setText("Set your " + shipList.get(0).getName().toUpperCase());
    }

    /**
     * Set coordinate lables for game-fields
     */
    private void setLabels() {
        //Column coordinates
        for (int i = 1; i <= MAPSIZE; i++) {
            Label l = new Label();
            l.setId("coordinates");
            l.setText(Integer.toString(i));
            playerGrid.add(l, 0, i);
            log.trace(l + " added to \"playerGrid\"");
        }

        //Row coordinates
        for (int i = 1; i <= MAPSIZE; i++) {
            Label l = new Label();
            l.setId("coordinates");
            l.setText(Integer.toString(i));
            playerGrid.add(l, i, 0);
            log.trace(l + " added to \"playerGrid\"");
        }
    }

    /**
     * Create buttons for game-field
     */
    private void createFieldButtons() {
        for (int r = 1; r <= MAPSIZE; r++) {

            for (int c = 1; c <= MAPSIZE; c++) {

                Button b = new Button();
                b.setMaxSize(100, 100);
                b.setId(r + "," + c);
                b.getStyleClass().add("waterButton");
                playerGrid.add(b, r, c);
                log.trace(b + " added to \"playerGrid\"");

                b.setOnMouseClicked(event -> {
                    log.debug(b + " clicked.");
                    int rowIndex = GridPane.getRowIndex(b);
                    int colIndex = GridPane.getColumnIndex(b);
                    placeShip(rowIndex, colIndex);
                });

                b.setOnMouseEntered(event -> {
                    if (shipList.size() > 0) {
                        int rowIndex = GridPane.getRowIndex(b);
                        int colIndex = GridPane.getColumnIndex(b);
                        showShip(rowIndex, colIndex, true);
                    }
                });
                b.setOnMouseExited(event -> {
                    if (shipList.size() > 0) {
                        int rowIndex = GridPane.getRowIndex(b);
                        int colIndex = GridPane.getColumnIndex(b);
                        showShip(rowIndex, colIndex, false);
                    }
                });
            }
        }
    }

    /**
     * Colors cells by hovering with the size of the selected ship and signalize whether a ship-placement is possible there
     *
     * @param rowIndex Row of hovered button
     * @param colIndex Column of hovered button
     * @param entered  Boolean: Is the hovered button entered OR exited ?
     */
    private void showShip(int rowIndex, int colIndex, boolean entered) {
        try {
            boolean dir = (group.getSelectedToggle() == horizontal);
            int shipLength = shipList.get(0).getLength();
            //Button entered
            if (entered) {
                if (!playerMap.checkShip(shipList.get(0), rowIndex, colIndex, dir)) {
                    colorPlacedShip(shipLength, rowIndex, colIndex, dir, "-fx-background-color: grey");
                } else {
                    colorPlacedShip(shipLength, rowIndex, colIndex, dir, "-fx-background-color: red");
                }
                //Button exited
            } else {
                colorCellsIndividually(shipLength, rowIndex, colIndex, dir);
            }
            //Catch if place-to-ship would cross the map-border
        } catch (ArrayIndexOutOfBoundsException l) {

            boolean dir = (group.getSelectedToggle() == horizontal);
            if (dir) {
                if (entered) {
                    colorPlacedShip(shipList.get(0).getLength() - 1, rowIndex, colIndex, true, "-fx-background-color: red");
                    //Button exited
                } else {
                    colorPlacedShip(shipList.get(0).getLength() - 1, rowIndex, colIndex, true, "-fx-background-color: #008ae6");
                    colorCellsIndividually(shipList.get(0).getLength() - 1, rowIndex, colIndex, true);
                }
            } else {
                if (entered) {
                    colorPlacedShip(shipList.get(0).getLength() - 1, rowIndex, colIndex, false, "-fx-background-color: red");
                    //Button exited
                } else {
                    colorPlacedShip(shipList.get(0).getLength() - 1, rowIndex, colIndex, false, "-fx-background-color: #008ae6");
                    colorCellsIndividually(shipList.get(0).getLength() - 1, rowIndex, colIndex, false);
                }
            }
        }
    }

    /**
     * Places a ship on the selected position
     *
     * @param rowIndex Row of clicked button
     * @param colIndex Column of clicked button
     */
    private void placeShip(int rowIndex, int colIndex) {
        try {
            boolean dir = (group.getSelectedToggle() == horizontal);
            int shipLength = shipList.get(0).getLength();
            log.debug("Actual length of \"shipList\" (" + shipList.get(0).getLength() + ") assigned to \"shipLength\" (" + shipLength + ")");
            if (shipList.size() > 1) {
                if (playerMap.setShip(shipList.get(0), rowIndex, colIndex, dir)) {
                    colorPlacedShip(shipLength, rowIndex, colIndex, dir, "-fx-background-color: black");
                    log.debug("Equivalent buttons of placed ship colored. (shipLength: " + shipLength + ", row/col: " + rowIndex + "/" + colIndex + ", dir(horizontal): " + dir + ", color: black)");
                    //Count whether there are still two ships of the same type in the list
                    long shipCount = countShipsInList(shipList);
                    log.debug("Actual number of equivalent ship types in \"shipList\" (" + shipCount + ") assigned to \"count\" by stream.");
                    if (shipCount == 2) {
                        infoLabel.setText("Now set your first " + shipList.get(0).getName().toUpperCase());
                    } else {
                        infoLabel.setText("Now set your second " + shipList.get(0).getName().toUpperCase());
                    }

                } else {
                    infoLabel.setText("FAIL: There is another ship. Try again!");
                }
            } else {

                if (playerMap.setShip(shipList.get(0), rowIndex, colIndex, dir)) {
                    colorPlacedShip(shipLength, rowIndex, colIndex, dir, "-fx-background-color: black");
                    log.debug("Equivalent buttons of placed ship colored. (shipLength: " + shipLength + ", row/col: " + rowIndex + "/" + colIndex + ", dir(horizontal): " + dir + ", color: black)");
                    infoLabel.setText("All ships set. Computer is setting..");
                    placeShipAI();
                    log.info("Ship placement finished. All ships set.");
                    playerGrid.setDisable(true);
                    radioPane.setDisable(true);
                    backPane.setStyle("-fx-opacity: 0.3");
                    popUp.setDisable(false);
                    popUp.setVisible(true);
                } else {
                    infoLabel.setText("FAIL: There is another ship. Try again!");
                }
            }
        } catch (ArrayIndexOutOfBoundsException err) {
            log.error("Entered position is too low, too high or the ship doesn't fit at this position!", err);
            infoLabel.setText("Placement not possible here!");
        }
    }

    /**
     * Places ships for Computer-Player randomly
     */
    private void placeShipAI() {
        while (true) {
            try {
                while (shipListAI.size() > 0) {
                    computerMap.setShipAI(shipListAI.get(0), aiRandom.randNumber(MAPSIZE), aiRandom.randNumber(MAPSIZE), aiRandom.randDir());
                }
                break;
            } catch (ArrayIndexOutOfBoundsException err) {
                log.error("Computer's try to place ship crossing the border of the gamefield", err);
            }
        }
    }

    /**
     * Counts how much ships are equivalent to the shipType of the first ship in a list
     * @param shipList shipList that is searched through
     * @return Number of equivalent shipTypes referred on first ship in list
     */
    public long countShipsInList(List<IShip> shipList) {
        String comparableShip = shipList.get(0).getName();
        final long count;
        count = shipList
                .stream()
                .filter(s -> comparableShip.equals(s.getName()))
                .count();
        return count;
    }

    /**
     * Colors the position of the (placed ship/ship-to-place)
     *
     * @param shipLength Length of placed ship
     * @param row        Row where ship was placed
     * @param column     Column where ship was placed
     * @param dir        Direction of placed ship: horizontal/vertical
     * @param color      Black: Placed ship
     *                   Red: No ship-placement on hovered position possible!
     *                   Blue: Water
     *                   Grey: Ship-placement on hovered position possible
     */
    private void colorPlacedShip(int shipLength, int row, int column, boolean dir, String color) {

        //List all children of GridPane => all Nodes (BUTTONS, labels, etc.)
        playerGrid.getChildren()
                .stream()
                .filter(child -> child instanceof Button)
                .forEach(child -> {
                    for (int l = 0; l < shipLength; l++) {
                        if (dir) {
                            if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == column + l) {
                                child.setStyle(color);
                            }
                        } else {
                            if (GridPane.getRowIndex(child) == row + l && GridPane.getColumnIndex(child) == column) {
                                child.setStyle(color);
                            }
                        }
                    }
                });
    }

    /**
     * Coloring individual cells of the theoretic ship-position
     *
     * @param shipLength Length of ship-to-placed
     * @param row        Row of hovered button
     * @param column     Column of hovered button
     * @param dir        Direction of ship-to-placed: horizontal/vertical
     */
    private void colorCellsIndividually(int shipLength, int row, int column, boolean dir) {
        playerGrid.getChildren()
                .stream()
                .filter(child -> child instanceof Button)
                .forEach(child -> {
                    for (int l = 0; l < shipLength; l++) {
                        //Horizontal
                        if (dir) {
                            if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == column + l) {
                                if (playerMap.getStatus(row, column + l) == Field.SHIP) {
                                    child.setStyle("-fx-background-color: black");
                                } else {
                                    child.setStyle("-fx-background-color: #008ae6");
                                }
                            }
                            //Vertical
                        } else {
                            if (GridPane.getRowIndex(child) == row + l && GridPane.getColumnIndex(child) == column) {
                                if (playerMap.getStatus(row + l, column) == Field.SHIP) {
                                    child.setStyle("-fx-background-color: black");
                                } else {

                                    child.setStyle("-fx-background-color: #008ae6");
                                }
                            }
                        }
                    }
                });
    }

      /**
     * Jump to next scene : Battle-Phase
     *
     * @throws IOException for setScene
     */
    @FXML
    public void goToBattlePhase() throws IOException {
        BattlePhaseController.setPlayerFleet(playerFleet);
        BattlePhaseController.setComputerFleet(computerFleet);
        BattlePhaseController.setPlayerMap(playerMap);
        BattlePhaseController.setComputerMap(computerMap);
        GuiDriver.getApplication().setScene("/fxml/BattlePhase.fxml", "Battle-Phase", 1001, 559);
    }
}


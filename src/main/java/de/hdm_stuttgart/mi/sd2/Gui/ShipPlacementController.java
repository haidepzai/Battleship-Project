package de.hdm_stuttgart.mi.sd2.Gui;

import de.hdm_stuttgart.mi.sd2.Field;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Threads.ShipListAICreator;
import de.hdm_stuttgart.mi.sd2.Threads.ShipListCreator;
import de.hdm_stuttgart.mi.sd2.aiRandom;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    public static List<IShip> shipList = new ArrayList<>();
    public static List<IShip> shipListAI = new ArrayList<>();
    static final int MAPSIZE = 9;

    public static int playerFleet;
    public static int computerFleet;

    static Field playerMap = new Field(MAPSIZE);
    static Field computerMap = new Field(MAPSIZE);

    @FXML
    GridPane playerGrid;
    @FXML
    Label infoLabel;
    @FXML
    RadioButton horizontal;
    @FXML
    RadioButton vertical;
    @FXML
    Pane popUp;
    @FXML
    Pane radioPane;
    @FXML
    Pane backPane;

    final private ToggleGroup group = new ToggleGroup();


    public void initialize() {

        //Adding all needed ships to the specific lists by using two threads (player, computer)
        Thread shipListCreator = new Thread(new ShipListCreator());
        Thread shipListAICreator = new Thread(new ShipListAICreator());
        shipListCreator.start();
        shipListAICreator.start();

        horizontal.setToggleGroup(group);
        vertical.setToggleGroup(group);
        horizontal.setSelected(true);

        //Filling the tables at one to nine
        for (int i = 1; i <= MAPSIZE; i++) {
            Label l = new Label();
            l.setId("coordinates");
            l.setText(Integer.toString(i));
            playerGrid.add(l, 0, i);
        }
        log.debug("Vertical coordinates of field (1-9) added");


        //filling the tables at a to i
        int e = 65;
        for (int i = 1; i <= MAPSIZE; i++) {
                Label l = new Label();
                l.setId("coordinates");
                l.setText(Character.toString((char) e));
                playerGrid.add(l, i, 0);
                e++;
        }

        log.debug("Horizontal coordinates of field (A-I) added");

        for (int r = 1; r <= MAPSIZE; r++) {

            for (int c = 1; c <= MAPSIZE; c++) {

                Button b = new Button();
                b.setMaxSize(100, 100);
                b.setId(r + "," + c);
                b.getStyleClass().add("waterButton");
                playerGrid.add(b, r, c);

                b.setOnMouseClicked(event -> {
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

        infoLabel.setText("Set your " + shipList.get(0).getName().toUpperCase());
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

            if (shipList.size() > 1) {

                if (playerMap.setShip(shipList.get(0), rowIndex, colIndex, dir)) {
                    colorPlacedShip(shipLength, rowIndex, colIndex, dir, "-fx-background-color: black");

                    final List<IShip> placeList = shipList;

                    //Count whether there are still two ships of the same type in the list
                    long count = placeList
                            .stream()
                            .filter(s -> placeList.get(0).getName().equals(s.getName()))
                            .count();

                    if (count == 2) {
                        infoLabel.setText("Now set your first " + shipList.get(0).getName().toUpperCase());
                        log.debug("First " + shipList.get(0).getName().toUpperCase() + " set!");
                    } else {
                        infoLabel.setText("Now set your second " + shipList.get(0).getName().toUpperCase());
                        log.debug("Second " + shipList.get(0).getName().toUpperCase() + " set!");
                    }

                } else {
                    infoLabel.setText("FAIL: There is another ship. Try again!");
                }

            } else {

                if (playerMap.setShip(shipList.get(0), rowIndex, colIndex, dir)) {
                    colorPlacedShip(shipLength, rowIndex, colIndex, dir, "-fx-background-color: black");
                    log.debug("All ships of the player set.");
                    infoLabel.setText("All ships set. Computer is setting..");

                    while (true) {

                        try {

                            while (shipListAI.size() > 0) {

                                computerMap.setShipAI(shipListAI.get(0), aiRandom.randNumber(MAPSIZE), aiRandom.randNumber(MAPSIZE), aiRandom.randDir());

                            }

                            log.debug("All ships of the computer were set.");
                            break;

                        } catch (ArrayIndexOutOfBoundsException ignore) {
                            //catches Exception but ignores it to continue after not possible ship-placements by computer
                        }
                    }

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
        ObservableList<Node> children = playerGrid.getChildren();
        children
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
        ObservableList<Node> children = playerGrid.getChildren();
        children
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
        GuiDriver.getApplication().setScene("/fxml/BattlePhase.fxml", "Battle-Phase", 1001, 559);
    }
}


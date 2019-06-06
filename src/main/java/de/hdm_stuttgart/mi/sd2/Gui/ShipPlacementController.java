package de.hdm_stuttgart.mi.sd2.Gui;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Field;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.ShipFactory;
import de.hdm_stuttgart.mi.sd2.aiRandom;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("Duplicates")
public class ShipPlacementController {

    public static List<IShip> shipList = new ArrayList<>();
    public static List<IShip> shipListAI = new ArrayList<>();
    static final int MAPSIZE = 9;

    static int playerFleet;
    static int computerFleet;

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

    @FXML
    public void initialize() {

        IShip Battleship;
        IShip Cruiser1;
        IShip Cruiser2;
        IShip Destroyer1;
        IShip Destroyer2;
        IShip Submarine1;
        IShip Submarine2;


        //Adding all needed ships for the game (2 Cruiser, 2 Destroyer, 2 Submarines and one Battleship)
        try {
            Cruiser1 = ShipFactory.createShip(IShip.ShipType.CRUISER);
            Cruiser2 = ShipFactory.createShip(IShip.ShipType.CRUISER);
            Destroyer1 = ShipFactory.createShip(IShip.ShipType.DESTROYER);
            Destroyer2 = ShipFactory.createShip(IShip.ShipType.DESTROYER);
            Submarine1 = ShipFactory.createShip(IShip.ShipType.SUBMARINE);
            Submarine2 = ShipFactory.createShip(IShip.ShipType.SUBMARINE);
            Battleship = ShipFactory.createShip(IShip.ShipType.BATTLESHIP);

            shipList.add(Battleship);
            shipList.add(Cruiser1);
            shipList.add(Cruiser2);
            shipList.add(Destroyer1);
            shipList.add(Destroyer2);
            shipList.add(Submarine1);
            shipList.add(Submarine2);

            shipListAI.add(Battleship);
            shipListAI.add(Cruiser1);
            shipListAI.add(Cruiser2);
            shipListAI.add(Destroyer1);
            shipListAI.add(Destroyer2);
            shipListAI.add(Submarine1);
            shipListAI.add(Submarine2);

            playerFleet = shipList.size();
            computerFleet = shipListAI.size();

        } catch (IllegalFactoryArgument i) {
            GuiDriver.log.error(i);
            System.exit(0);
        }

        horizontal.setToggleGroup(group);
        vertical.setToggleGroup(group);
        horizontal.setSelected(true);

        //Filling the tables at one to nine
        for (int i = 1; i <= MAPSIZE; i++) {
            Label l = new Label();
            l.setId("oneToNine");
            l.setText(Integer.toString(i));

            playerGrid.add(l, 0, i);
        }


        //filling the tables at a to i
        int e = 65;
        for (int i = 1; i <= MAPSIZE; i++) {
            if (e == 73) {
                Label l = new Label();
                l.setId("aToIjustI");
                l.setText(Character.toString((char) e));
                playerGrid.add(l, i, 0);
            } else {
                Label l = new Label();
                l.setId("aToI");
                l.setText(Character.toString((char) e));
                playerGrid.add(l, i, 0);
                e++;
            }
        }

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
    @FXML
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
                if (checkColoredCells(shipList.get(0), rowIndex, colIndex, dir).equals("ship")) {
                    colorPlacedShip(shipLength, rowIndex, colIndex, dir, "-fx-background-color: black");
                } else if (checkColoredCells(shipList.get(0), rowIndex, colIndex, dir).equals("water")) {
                    colorPlacedShip(shipLength, rowIndex, colIndex, dir, "-fx-background-color: #008ae6");
                } else {
                    colorCellsIndividually(shipLength, rowIndex, colIndex, dir);
                }
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
     * Check the water-/other-ship-state on the theoretic ship-position
     *
     * @param i   Ship type
     * @param row Row of hovered button
     * @param col Column of hovered button
     * @param dir Horizontal/Vertical direction of the ship to place
     * @return "water": Only water on the ship-to-place-postion
     * "ship": Only a ship on the ship-to-place-position
     * "ship+water": Water AND a ship on the ship-to-place-position
     */
    @FXML
    private String checkColoredCells(IShip i, int row, int col, boolean dir) {
        int size = i.getLength();
        String status = "";
        //Vertical
        if (!dir) {
            for (int v = 0; v < size; v++) {
                if (playerMap.getStatus(row + v, col) == Field.SHIP) {
                    if (status.equals("water")) {
                        status = "ship+water";
                        break;
                    } else {
                        status = "ship";
                    }
                } else {
                    if (status.equals("ship")) {
                        status = "ship+water";
                        break;
                    } else {
                        status = "water";
                    }
                }
            }
            return status;

            //Horizontal
        } else {
            for (int h = 0; h < size; h++) {
                if (playerMap.getStatus(row, col + h) == Field.SHIP) {
                    if (status.equals("water")) {
                        status = "ship+water";
                        break;
                    } else {
                        status = "ship";
                    }
                } else {
                    if (status.equals("ship")) {
                        status = "ship+water";
                        break;
                    } else {
                        status = "water";
                    }
                }
            }
            return status;
        }
    }

    /**
     * Places a ship on the selected position
     *
     * @param rowIndex Row of clicked button
     * @param colIndex Column of clicked button
     */
    @FXML
    private void placeShip(int rowIndex, int colIndex) {

        try {

            boolean dir = (group.getSelectedToggle() == horizontal);
            int shipLength = shipList.get(0).getLength();

            if (shipList.size() > 1) {

                if (playerMap.setShip(shipList.get(0), rowIndex, colIndex, dir)) {
                    colorPlacedShip(shipLength, rowIndex, colIndex, dir, "-fx-background-color: black");
                    int counter = 0;
                    for (IShip ship : shipList) {
                        if (shipList.get(0).getName().equals(ship.getName())) {
                            counter++;
                        }
                    }
                    if (counter == 2) {
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

                    GuiDriver.log.debug("Player's map created. All ships set.");
                    infoLabel.setText("All ships set. Computer is setting..");

                    while (true) {

                        try {

                            while (shipListAI.size() > 0) {

                                computerMap.setShipAI(shipListAI.get(0), aiRandom.randNumber(MAPSIZE), aiRandom.randNumber(MAPSIZE), aiRandom.randDir());

                            }

                            GuiDriver.log.debug("Computer's map created. All ships set.");
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
            GuiDriver.log.error("Entered position is too low, too high or the ship doesn't fit at this position!", err);
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
    @FXML
    private void colorPlacedShip(int shipLength, int row, int column, boolean dir, String color) {

        //List all children of GridPane => all Nodes (BUTTONS, labels, etc.)
        ObservableList<Node> children = playerGrid.getChildren();
        //begin at i=1 because first child causes NullPointerException => has no Row-/ColumnIndex
        for (int i = 1; i < children.size(); i++) {
            for (int l = 0; l < shipLength; l++) {
                if (dir) {
                    if (GridPane.getRowIndex(children.get(i)) == row && GridPane.getColumnIndex(children.get(i)) == column + l) {
                        children.get(i).setStyle(color);
                    }
                } else {
                    if (GridPane.getRowIndex(children.get(i)) == row + l && GridPane.getColumnIndex(children.get(i)) == column) {
                        children.get(i).setStyle(color);
                    }
                }
            }
        }
    }

    /**
     * Coloring individual cells of the theoretic ship-position
     *
     * @param shipLength Length of ship-to-placed
     * @param row        Row of hovered button
     * @param column     Column of hovered button
     * @param dir        Direction of ship-to-placed: horizontal/vertical
     */
    @FXML
    private void colorCellsIndividually(int shipLength, int row, int column, boolean dir) {
        ObservableList<Node> children = playerGrid.getChildren();
        for (int i = 1; i < children.size(); i++) {
            for (int l = 0; l < shipLength; l++) {
                //Horizontal
                if (dir) {
                    if (GridPane.getRowIndex(children.get(i)) == row && GridPane.getColumnIndex(children.get(i)) == column + l) {
                        if (playerMap.getStatus(row, column + l) == Field.SHIP) {
                            children.get(i).setStyle("-fx-background-color: black");
                        } else {
                            children.get(i).setStyle("-fx-background-color: #008ae6");
                        }
                    }
                    //Vertical
                } else {
                    if (GridPane.getRowIndex(children.get(i)) == row + l && GridPane.getColumnIndex(children.get(i)) == column) {
                        if (playerMap.getStatus(row + l, column) == Field.SHIP) {
                            children.get(i).setStyle("-fx-background-color: black");
                        } else {
                            children.get(i).setStyle("-fx-background-color: #008ae6");
                        }
                    }
                }
            }
        }
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


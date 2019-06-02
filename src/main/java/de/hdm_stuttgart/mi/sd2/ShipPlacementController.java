package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.ShipFactory;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


@SuppressWarnings("Duplicates")
public class ShipPlacementController {

    static List<IShip> shipList = new ArrayList<>();
    static List<IShip> shipListAI = new ArrayList<>();
    static final int MAPSIZE = 9;

    static Field playerMap = new Field(MAPSIZE);
    Field computerMap = new Field(MAPSIZE);

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

    final ToggleGroup group = new ToggleGroup();

    @FXML
    public void initialize() throws IOException {

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
                //Pane p = new Pane();
                Button b = new Button();
                b.setMaxSize(100, 100);
                b.setId(r + "," + c);
                b.getStyleClass().add("waterButton");
                playerGrid.add(b, r, c);
                b.setOnAction(event -> {
                    int rowIndex = GridPane.getRowIndex(b);
                    int colIndex = GridPane.getColumnIndex(b);
                    placeShip(event, rowIndex, colIndex);
                });
            }
        }

        infoLabel.setText("Set your " + shipList.get(0).getName().toUpperCase());
    }

    @FXML
    public void placeShip(ActionEvent event, int rowIndex, int colIndex) {

        try {

            boolean dir = (group.getSelectedToggle() == horizontal);
            int shipLength = shipList.get(0).getLength();

            //ObservableList<Node> children = playerGrid.getChildren();
            //System.out.println(children);

            if (shipList.size() > 1) {

                String first = "first ";
                String second = "second ";

                if(playerMap.setShip(shipList.get(0), rowIndex, colIndex, dir)) {
                    colorShipCells(shipLength, rowIndex, colIndex, dir, playerGrid);
                    //shipList.remove(0);
                    int counter = 0;
                    for(IShip ship : shipList) {
                        if (shipList.get(0).getName().equals(ship.getName())) {
                            counter++;
                        }
                    }
                    if(counter == 2) {
                        infoLabel.setText("Now set your " + first + shipList.get(0).getName().toUpperCase());
                    } else {
                        infoLabel.setText("Now set your " + second + shipList.get(0).getName().toUpperCase());
                    }

                } else {
                    infoLabel.setText("FAIL: There is another ship. Try again!");
                }

            } else {

                if(playerMap.setShip(shipList.get(0), rowIndex, colIndex, dir)) {
                    colorShipCells(shipLength, rowIndex, colIndex, dir, playerGrid);

                    //shipList.remove(0);
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
                            //catches Exception but ignores it to continue uninterrupted
                        }
                    }


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

    @FXML
    public void colorShipCells (int shipLength, int row, int column, boolean dir, GridPane peterPane) {

        //List all children of GridPane => all Nodes (BUTTONS, labels, etc.)
        ObservableList<Node> children = peterPane.getChildren();
        //begin at i=1 because first child causes NullPointerException => has no Row-/ColumnIndex
        for (int i = 1; i < children.size(); i++) {
            for(int l = 0; l < shipLength; l++) {
                if (dir) {
                    if (GridPane.getRowIndex(children.get(i)) == row && GridPane.getColumnIndex(children.get(i)) == column+l) {
                        children.get(i).setStyle("-fx-background-color: black");
                    }
                } else {
                    if (GridPane.getRowIndex(children.get(i)) == row+l && GridPane.getColumnIndex(children.get(i)) == column) {
                        children.get(i).setStyle("-fx-background-color: black");
                    }
                }
            }
        }
    }

    @FXML
    public void goToBattlePhase (ActionEvent event) throws IOException{
        GuiDriver.getApplication().setScene("/fxml/BattlePhase.fxml", "Battle-Phase", 1001, 559);
    }
}


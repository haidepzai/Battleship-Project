package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Gui.ShipPlacementController;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestShipPlacementController {

    private ShipPlacementController spc = new ShipPlacementController();

    @Test
    public void testColorCellsIndividually() throws InterruptedException {
        spc.initialize();
        spc.placeShip(3, 4);
        //GridPane testGrid = spc.getPlayerGrid();
        ObservableList<Node> children = spc.getPlayerGrid().getChildren();
        children
                .stream()
                .filter(child -> child instanceof Button)
                .forEach(child -> {
                    for(int i = 0; i < spc.getShipList().get(0).getLength(); i++) {
                        if (GridPane.getRowIndex(child) == 3 && GridPane.getColumnIndex(child) == 4 + i) {
                            assertEquals(child.getStyle(), "-fx-background-color: black");
                        }
                    }
                });

    }


}


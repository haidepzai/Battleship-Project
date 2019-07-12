package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Gui.GuiDriver;
import de.hdm_stuttgart.mi.sd2.Gui.MenuController;
import de.hdm_stuttgart.mi.sd2.Gui.ShipPlacementController;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.ShipFactory;
import de.hdm_stuttgart.mi.sd2.Threads.ShipListCreator;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestShipPlacementController {

    @Test
    public void testColorCellsIndividually() throws InterruptedException, IOException{
//        FXMLLoader.load(getClass().getResource("/fxml/ShipPlacement.fxml"));
//        ShipPlacementController spc = new ShipPlacementController();
//        spc.initialize();
//        spc.colorPlacedShip(4, 4,4, true, "-fx-background-color: black");
//        ObservableList<Node> children = spc.getPlayerGrid().getChildren();
//        children
//                .stream()
//                .filter(child -> child instanceof Button)
//                .forEach(child -> {
//                    for(int i = 0; i < spc.getShipList().get(0).getLength(); i++) {
//                        if (GridPane.getRowIndex(child) == 3 && GridPane.getColumnIndex(child) == 4 + i) {
//                            assertEquals(child.getStyle(), "-fx-background-color: black");
//                        }
//                    }
//                });

    }


}


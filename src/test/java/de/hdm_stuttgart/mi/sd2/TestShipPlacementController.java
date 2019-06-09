package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Gui.ShipPlacementController;
import org.junit.Test;

public class TestShipPlacementController {

    private ShipPlacementController spc = new ShipPlacementController();

    @Test
    public void testColorCellsIndividually() throws IllegalFactoryArgument {
        //todo: place two ships side by side and hover with a next ship over them and check correct coloring:
        //todo:                                                     -> is .getStyle().equals("-fx-background-color: ...")

    }

    @Test
    public void testColoring() throws IllegalFactoryArgument {
        //todo: check if cells are colored correctly: Correct length? Placement beneath ships not possible! etc.
        //todo: "negative-test": check if positions that are free aren#t colored red (no placement possible)
    }

    //todo: test showShip ? hovering process testable?

}

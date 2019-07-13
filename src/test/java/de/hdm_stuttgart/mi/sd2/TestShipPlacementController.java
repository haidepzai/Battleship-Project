package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Gui.ShipPlacementController;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.ShipFactory;
import de.hdm_stuttgart.mi.sd2.Threads.ShipListAICreator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestShipPlacementController {

    private ShipPlacementController spc = new ShipPlacementController();

    @Test
    public void testCountShipsInList() throws IllegalFactoryArgument {
        IShip Battleship;
        IShip Cruiser;
        IShip Cruiser2;
        Battleship = ShipFactory.createShip(IShip.ShipType.BATTLESHIP);
        Cruiser = ShipFactory.createShip(IShip.ShipType.CRUISER);
        Cruiser2 = ShipFactory.createShip(IShip.ShipType.CRUISER);
        List<IShip> testList = new ArrayList<>();
        testList.add(Battleship);
        testList.add(Cruiser);
        testList.add(Cruiser2);

        assertNotEquals(3, spc.countShipsInList(testList));
        assertEquals(1, spc.countShipsInList(testList));

        testList.remove(0);

        assertEquals(2, spc.countShipsInList(testList));

        testList.remove(0);

        assertNotEquals(2, spc.countShipsInList(testList));
    }

    @Test
    public void placeShipAI() throws InterruptedException {
        Thread shipListAICreator = new Thread(new ShipListAICreator());
        shipListAICreator.start();
        shipListAICreator.join();
        //TODO: Bug, just set one ship for AI before shipList is empty
        //System.out.println(ShipPlacementController.getShipListAI().size());
        spc.placeShipAI();
        //System.out.println(ShipPlacementController.getShipListAI().size());
    }
}



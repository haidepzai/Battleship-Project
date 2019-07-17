package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Gui.ShipPlacementController;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.ShipFactory;
import de.hdm_stuttgart.mi.sd2.Threads.ShipListAICreator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestShipPlacementController {

    private ShipPlacementController spc = new ShipPlacementController();
    private static List<IShip> testList = new ArrayList<>();

    /**
     * Filling shipLists with some ships
     * @throws IllegalFactoryArgument ShipType doesn't exist
     */
    @BeforeClass
    public static void setList() throws IllegalFactoryArgument{
        IShip Battleship;
        IShip Cruiser;
        IShip Cruiser2;
        Battleship = ShipFactory.createShip(IShip.ShipType.BATTLESHIP);
        Cruiser = ShipFactory.createShip(IShip.ShipType.CRUISER);
        Cruiser2 = ShipFactory.createShip(IShip.ShipType.CRUISER);
        testList.add(Battleship);
        testList.add(Cruiser);
        testList.add(Cruiser2);

    }

    /**
     * Tests whether countShipList ist counting correctly and finds equivalent shipTypes
     */
    @Test
    public void testCountShipsInList() {
        assertNotEquals(3, spc.countShipsInList(testList));
        assertEquals(1, spc.countShipsInList(testList));
        testList.remove(0);
        assertEquals(2, spc.countShipsInList(testList));
        testList.remove(0);
        assertNotEquals(2, spc.countShipsInList(testList));
    }
}



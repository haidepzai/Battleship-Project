package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Gui.ShipPlacementController;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.ShipFactory;
import de.hdm_stuttgart.mi.sd2.Threads.ShipListAICreator;
import de.hdm_stuttgart.mi.sd2.Threads.ShipListCreator;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestListCreatorThreads {


    @BeforeClass
    public static void createLists() throws InterruptedException {
        Thread shipListCreator = new Thread(new ShipListCreator());
        Thread shipListAICreator = new Thread(new ShipListAICreator());
        shipListCreator.start();
        shipListAICreator.start();
        shipListCreator.join();
        shipListAICreator.join();
    }

    @Test
    public void testShipList() {
        assertNotEquals(0, ShipPlacementController.getShipList().size());
        assertEquals(7, ShipPlacementController.getShipList().size());
        assertEquals(ShipPlacementController.getShipList().get(0).getName(), "Battleship");
        assertEquals(ShipPlacementController.getShipList().get(6).getName(), "Submarine");
    }

    @Test
    public void testShipListAI() {
        assertNotEquals(0, ShipPlacementController.getShipListAI().size());
        assertEquals(7, ShipPlacementController.getShipListAI().size());
        assertEquals(ShipPlacementController.getShipListAI().get(0).getName(), "Battleship");
        assertEquals(ShipPlacementController.getShipListAI().get(6).getName(), "Submarine");
    }


}

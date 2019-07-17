package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Gui.ShipPlacementController;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.ShipFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestField {

    private Field f = new Field(ShipPlacementController.MAPSIZE);

    /**
     * Check if Ships can be set
     * @throws IllegalFactoryArgument Ship type doesn't exist
     */
    @Test
    public void testSetShip() throws IllegalFactoryArgument {

        //Check if Battleship can be set
        assertTrue(f.setShip(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 5, 5, true));

        //Set Battleship
        f.setCore(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 5, 5, true);

        //Check if Cruiser can be set to same spot where Battleship is
        assertFalse(f.setShip(ShipFactory.createShip(IShip.ShipType.CRUISER), 5, 5, true));


    }

    /**
     * Test what will happen if Battleship is set on border
     * @throws IllegalFactoryArgument Ship type doesn't exist
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testArrayIndexOutOfBoundsException() throws IllegalFactoryArgument {

        assertTrue(f.setShip(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 9, 9, true));
        assertTrue(f.setShip(ShipFactory.createShip(IShip.ShipType.CRUISER), 1, 8, true));
        assertFalse(f.setShip(ShipFactory.createShip(IShip.ShipType.DESTROYER), 5, 8, true));
        assertFalse(f.setShip(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 1, 9, false));
        assertFalse(f.setShip(ShipFactory.createShip(IShip.ShipType.SUBMARINE), 9, 9, false));
    }

    /**
     * Check if Battleship has been set
     * @throws IllegalFactoryArgument Ship type doesn't exist
     */
    @Test
    public void testCheckGetStatus() throws IllegalFactoryArgument {

        //Battleship
        f.setCore(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 5, 5, true);

        assertEquals(f.getStatus(5, 5), Field.SHIP);
        assertEquals(f.getStatus(5, 7), Field.SHIP);
        assertNotEquals(f.getStatus(5, 4), Field.SHIP);
        assertNotEquals(f.getStatus(6, 5), Field.SHIP);

        //Cruiser
        f.setCore(ShipFactory.createShip(IShip.ShipType.CRUISER), 1, 1, false);

        assertEquals(f.getStatus(1, 1), Field.SHIP);
        assertEquals(f.getStatus(3, 1), Field.SHIP);
        assertNotEquals(f.getStatus(4, 1), Field.SHIP);
        assertNotEquals(f.getStatus(1, 2), Field.SHIP);

    }

    /**
     * Checks if the attack-method
     * @throws IllegalFactoryArgument Ship type doesn't exist
     */
    @Test
    public void testAttack() throws IllegalFactoryArgument {

        //Create horizontal Battleship
        f.setCore(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 5, 5, true);

        f.attack(5, 5);
        assertEquals(f.getStatus(5, 5), Field.HIT);

        //Attack on the right of the ship
        f.attack(5, 6);
        assertEquals(f.getStatus(5, 6), Field.HIT); //Ship length 4 = HIT

        //Attack on the left of the ship
        f.attack(5, 4);
        assertNotEquals(f.getStatus(5, 4), Field.HIT);

        //Attack water
        f.attack(9, 9);
        assertNotEquals(f.getStatus(9, 9), Field.HIT); //NO hit

        //Create vertical Cruiser
        f.setCore(ShipFactory.createShip(IShip.ShipType.CRUISER), 1, 1, false);

        f.attack(3, 1);
        assertEquals(f.getStatus(3, 1), Field.HIT);

        f.attack(4, 1);
        assertNotEquals(f.getStatus(4, 1), Field.HIT); //Cruiser length 3 = NO HIT


    }

    /**
     * Checks if the method that checks whether a ship was destroyed works correctly
     * @throws IllegalFactoryArgument Thrown if transferred ship type doesn't exist
     */
    @Test
    public void testCheckShipState() throws IllegalFactoryArgument {

        f.setCore(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 9, 1, true);
        f.attack(9, 3);
        f.attack(9, 4);
        assertFalse(f.checkShipState(9, 4));
        f.attack(9, 2);
        assertFalse(f.checkShipState(9, 2));
        f.attack(9, 1);
        assertTrue(f.checkShipState(9, 1));


        f.setCore(ShipFactory.createShip(IShip.ShipType.CRUISER), 3, 4, true);
        f.attack(3, 4);
        f.attack(3, 6);
        assertFalse(f.checkShipState(3, 6));
        assertFalse(f.checkShipState(3, 4));
        f.attack(3, 5);
        assertTrue(f.checkShipState(3, 5));


        f.setCore(ShipFactory.createShip(IShip.ShipType.DESTROYER), 2, 9, false);
        f.attack(2, 8);
        f.attack(1, 9);
        f.attack(3, 8);
        f.attack(4, 9);
        f.attack(3, 9);
        assertFalse(f.checkShipState(3, 9));
        f.attack(2, 9);
        assertTrue(f.checkShipState(2, 9));
        assertTrue(f.checkShipState(3, 9));
    }

}

package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.ShipFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestField {

    Field f = new Field(9);

    //Check if Ships can be set
    @Test
    public void testSetShip() throws IllegalFactoryArgument {

        //Check if Battleship can be set
        assertTrue(f.setShip(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 5,5, true));

        //Set Battleship
        f.setCore(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 5, 5, true);

        //Check if Cruiser can be set to same spot where Battleship is
        assertFalse(f.setShip(ShipFactory.createShip(IShip.ShipType.CRUISER),5,5,true));


    }
    //Test what will happen if Battleship is set on border
    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsException() throws IllegalFactoryArgument {

        assertEquals("Placement not possible here!", f.setShip(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 9,9, true));

    }

    //Check if Battleship has been set
    @Test
    public void testCheckShip() throws IllegalFactoryArgument {

        f.setCore(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 5, 5, true);

        assertTrue(f.getStatus(5,5) == Field.SHIP);
        assertTrue(f.getStatus(5,7) == Field.SHIP);
        assertFalse(f.getStatus(9,9) == Field.SHIP);
        assertFalse(f.getStatus(6,5) == Field.SHIP);

    }

}

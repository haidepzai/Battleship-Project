package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.ShipFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestField {

    private Field f = new Field(9);

    //Check if Ships can be set
    @Test
    public void testSetShip() throws IllegalFactoryArgument {

        //Check if Battleship can be set
        assertTrue(f.setShip(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 5, 5, true));

        //Set Battleship
        f.setCore(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 5, 5, true);

        //Check if Cruiser can be set to same spot where Battleship is
        assertFalse(f.setShip(ShipFactory.createShip(IShip.ShipType.CRUISER), 5, 5, true));


    }

    //Test what will happen if Battleship is set on border
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testArrayIndexOutOfBoundsException() throws IllegalFactoryArgument {

        assertTrue(f.setShip(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 9, 9, true));
        assertTrue(f.setShip(ShipFactory.createShip(IShip.ShipType.CRUISER), 1, 8, true));
        assertFalse(f.setShip(ShipFactory.createShip(IShip.ShipType.DESTROYER), 5, 8, true));
        assertFalse(f.setShip(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 1, 9, false));
        assertFalse(f.setShip(ShipFactory.createShip(IShip.ShipType.SUBMARINE), 9, 9, false));
    }

    //Check if Battleship has been set
    @Test
    public void testCheckGetStatus() throws IllegalFactoryArgument {

        //todo: test more states => WATER, SHOT, HIT by using "attack"-method

        //Battleship
        f.setCore(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 5, 5, true);

        assertTrue(f.getStatus(5, 5) == Field.SHIP);
        assertTrue(f.getStatus(5, 7) == Field.SHIP);
        assertFalse(f.getStatus(5, 4) == Field.SHIP);
        assertFalse(f.getStatus(6, 5) == Field.SHIP);

        //Cruiser
        f.setCore(ShipFactory.createShip(IShip.ShipType.CRUISER), 1, 1, false);

        assertTrue(f.getStatus(1, 1) == Field.SHIP);
        assertTrue(f.getStatus(3, 1) == Field.SHIP);
        assertFalse(f.getStatus(4, 1) == Field.SHIP);
        assertFalse(f.getStatus(1, 2) == Field.SHIP);

    }

    @Test
    public void testAttack() throws IllegalFactoryArgument {

        //Create horizontal Battleship
        f.setCore(ShipFactory.createShip(IShip.ShipType.BATTLESHIP), 5, 5, true);

        f.attack(5, 5);
        assertTrue(f.getStatus(5, 5) == Field.HIT);

        //Attack on the right of the ship
        f.attack(5, 6);
        assertTrue(f.getStatus(5, 6) == Field.HIT); //Ship length 4 = HIT

        //Attack on the left of the ship
        f.attack(5, 4);
        assertFalse(f.getStatus(5, 4) == Field.HIT);

        //Attack water
        f.attack(9, 9);
        assertFalse(f.getStatus(9, 9) == Field.HIT); //NO hit

        //Create vertical Cruiser
        f.setCore(ShipFactory.createShip(IShip.ShipType.CRUISER), 1, 1, false);

        f.attack(3, 1);
        assertTrue(f.getStatus(3, 1) == Field.HIT);

        f.attack(4, 1);
        assertFalse(f.getStatus(4, 1) == Field.HIT); //Cruiser length 3 = NO HIT


    }

    @Test
    public void testCheckShipState() throws IllegalFactoryArgument {
        //todo: check if a destroyed ship is correctly detected
        //todo: "negative-test" - check whether a ship which is not destroyed is not detected as destroyed!
    }

}

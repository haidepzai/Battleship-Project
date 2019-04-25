package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.Battleship;
import de.hdm_stuttgart.mi.sd2.Ships.Cruiser;
import de.hdm_stuttgart.mi.sd2.Ships.ShipFactory;
import de.hdm_stuttgart.mi.sd2.Ships.Submarine;

import java.util.ArrayList;
import java.util.List;


public class Driver {



    public static void main(String[] args) {
        int mapSize = 6;


        Field playerMap = new Field(mapSize);

        Field computerMap = new Field(mapSize);

        List<IShip> shipList = new ArrayList<>();


        IShip Battleship;
        IShip Cruiser1;
        IShip Cruiser2;
        IShip Destroyer1;
        IShip Destroyer2;
        IShip Submarine1;
        IShip Submarine2;

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


            playerMap.setShip(Battleship, 3, true);

        } catch (IllegalFactoryArgument i) {
            System.err.println(i);
            System.exit(0);
        }

        //Implement Attack method here

        playerMap.printMap();

    }

}
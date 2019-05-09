package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.Battleship;
import de.hdm_stuttgart.mi.sd2.Ships.Cruiser;
import de.hdm_stuttgart.mi.sd2.Ships.ShipFactory;
import de.hdm_stuttgart.mi.sd2.Ships.Submarine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Driver {



    public static void main(String[] args) {



        int mapSize = 6;

        //Creating the Map for the game depending of the mapSize

        Field playerMap = new Field(mapSize);

        Field computerMap = new Field(mapSize);

        List<IShip> shipList = new ArrayList<>();

        //Creating variables for the needed ships

        IShip Battleship;
        IShip Cruiser1;
        IShip Cruiser2;
        IShip Destroyer1;
        IShip Destroyer2;
        IShip Submarine1;
        IShip Submarine2;


        //Adding all needed ships for the game (2 Cruiser, 2 Destroyer, 2 Submarines and one Battleship)
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





            //Tests
          /*  playerMap.printMap();
            playerMap.checkShip(playerMap,6);
            playerMap.attack(playerMap,1);
            playerMap.attack(playerMap,2);
            playerMap.attack(playerMap,3);
            playerMap.attack(playerMap,4);
            playerMap.attack(playerMap,5);
            playerMap.attack(playerMap,6);
            playerMap.attack(playerMap,7);
            playerMap.printMap();
               playerMap.setShip(Battleship, 3, true);
*/

          /*  Scanner s = new Scanner(System.in);
            int playerChoice = s.nextInt();

            System.out.println("Setze Schiff jaaaaa");

*/


            System.out.println("Du musst noch diese Schiffe setzen: ");

            for (int i=0;i<shipList.size();i++) {
                System.out.println(shipList.get(i).getName());
            }


        } catch (IllegalFactoryArgument i) {
            System.err.println(i);
            System.exit(0);
        }







    }


}
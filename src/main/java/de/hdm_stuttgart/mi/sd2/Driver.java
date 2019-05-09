package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Driver {


    public static void main(String[] args) {


        int mapSize = 9;

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
          /*

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


            System.out.println("Set all your ships");


            try (Scanner s = new Scanner(System.in)) {

                System.out.println("\nWhich ship do you want to set?");
                while (shipList.size() != 0) {
                    System.out.println("You have to set these ships: ");
                    for (int i = 0; i < shipList.size(); i++) {
                        System.out.print(shipList.get(i).getName() + " ");
                    }
                    System.out.println();
                    String e = s.next();


                    switch (e) {

                        case "Battleship":
                            System.out.println("Where do you want to set your ship? Place it horizontal(True or False)?");
                            playerMap.setShip(Battleship, s.nextInt(), s.nextBoolean());
                            shipList.remove(Battleship);


                            break;
                        case "Cruiser":
                            System.out.println("Where do you want to set your ship? Place it horizontal(True or False)?");
                            playerMap.setShip(Cruiser1, s.nextInt(), s.nextBoolean());
                            System.out.println("Now set your second cruiser!");

                            System.out.println("Where do you want to set your ship? Place it horizontal(True or False)?");
                            playerMap.setShip(Cruiser2, s.nextInt(), s.nextBoolean());
                            shipList.remove(Cruiser1);
                            shipList.remove(Cruiser2);
                            break;
                        case "Submarine":
                            System.out.println("Where do you want to set your ship? Place it horizontal(True or False)?");
                            playerMap.setShip(Submarine1, s.nextInt(), s.nextBoolean());
                            System.out.println("Now set your second cruiser!");

                            System.out.println("Where do you want to set your ship? Place it horizontal(True or False)?");
                            playerMap.setShip(Submarine2, s.nextInt(), s.nextBoolean());
                            shipList.remove(Submarine1);
                            shipList.remove(Submarine2);

                            break;
                        case "Destroyer":
                            System.out.println("Where do you want to set your ship? Place it horizontal(True or False)?");
                            playerMap.setShip(Destroyer1, s.nextInt(), s.nextBoolean());
                            System.out.println("Now set your second Destroyer!");

                            System.out.println("Where do you want to set your ship? Place it horizontal(True or False)?");
                            playerMap.setShip(Destroyer2, s.nextInt(), s.nextBoolean());
                            shipList.remove(Destroyer1);
                            shipList.remove(Destroyer2);

                            break;
                        default:
                            System.out.println("Wrong input!");

                    }
                }

                playerMap.printMap();

            }


        } catch (IllegalFactoryArgument i) {
            System.err.println(i);
            System.exit(0);
        }


    }


}
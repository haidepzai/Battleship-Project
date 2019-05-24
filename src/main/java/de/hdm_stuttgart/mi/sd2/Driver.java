package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.*;

public class Driver {

    //Logger for Driver-class initialized
    private static Logger log = LogManager.getLogger(Driver.class);
    static List<IShip> shipList = new ArrayList<>();
    static List<IShip> shipListAI = new ArrayList<>();

    public static void main(String[] args) {

        log.debug("Program started!");

        //todo computer map random ship placements
        //todo computer ai shooting
        //todo method or class to change turns
        //todo write JUnit test's!!!
        //todo write logs , how many? where? when?
        //todo many more.. add it here when you notice something!

        int mapSize = 9;

        //Creating the Map for the game depending of the mapSize

        Field playerMap = new Field(mapSize);
        Field computerMap = new Field(mapSize);

        //Creating variables for the needed ships

        IShip Battleship;
        IShip Cruiser1;
        IShip Cruiser2;
        IShip Destroyer1;
        IShip Destroyer2;
        IShip Submarine1;
        IShip Submarine2;


        //Adding all needed ships for the game (2 Cruiser, 2 Destroyer, 2 Submarines and one Battleship)
        try (Scanner s = new Scanner(System.in)) {
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

            shipListAI.add(Battleship);
            shipListAI.add(Cruiser1);
            shipListAI.add(Cruiser2);
            shipListAI.add(Destroyer1);
            shipListAI.add(Destroyer2);
            shipListAI.add(Submarine1);
            shipListAI.add(Submarine2);


            //shipListAI = shipList;


            System.out.println("Ahoy! Set all your ships sailor!");

            //TODO: Commented complete user-action to test Computer-AI faster (look below)

            /*while(true) {

                try {


                    if (shipList.size() != 0) {
                        System.out.println("\nWhich ship do you want to set?");
                        System.out.print("You have to set these ships: ");

                        Info.shipInfo(shipList);

                        System.out.println();
                        //String e = s.next();


                        switch (s.next().toLowerCase()) {

                            case "battleship":
                                if(shipList.contains(Battleship)) {
                                    Info.shipPlaceQuestion();
                                    playerMap.setShip(Battleship, s.nextInt(), s.nextInt(), s.nextBoolean());
                                } else {
                                    System.out.println("Battleship already placed!");
                                }
                                break;

                            case "cruiser":

                                if(shipList.contains(Cruiser1)) {
                                    Info.shipPlaceQuestion();
                                    playerMap.setShip(Cruiser1, s.nextInt(), s.nextInt(), s.nextBoolean());
                                } else if(shipList.contains(Cruiser2)){
                                    Info.shipPlaceQuestion();
                                    playerMap.setShip(Cruiser2, s.nextInt(), s.nextInt(), s.nextBoolean());
                                } else {
                                    System.out.println("Cruisers already placed!");
                                }
                                break;

                            case "submarine":
                                if(shipList.contains(Submarine1)) {
                                    Info.shipPlaceQuestion();
                                    playerMap.setShip(Submarine1, s.nextInt(), s.nextInt(), s.nextBoolean());
                                } else if(shipList.contains(Submarine2)){
                                    Info.shipPlaceQuestion();
                                    playerMap.setShip(Submarine2, s.nextInt(), s.nextInt(), s.nextBoolean());
                                } else {
                                    System.out.println("Submarines already placed!");
                                }
                                break;

                            case "destroyer":
                                if(shipList.contains(Destroyer1)) {
                                    Info.shipPlaceQuestion();
                                    playerMap.setShip(Destroyer1, s.nextInt(), s.nextInt(), s.nextBoolean());
                                } else if(shipList.contains(Destroyer2)) {
                                    Info.shipPlaceQuestion();
                                    playerMap.setShip(Destroyer2, s.nextInt(), s.nextInt(), s.nextBoolean());
                                } else {
                                    System.out.println("Destroyers already placed!");
                                }
                                break;

                            default:
                                System.out.println("This ship type doesn't exist! Try again!");

                        }

                        playerMap.printMap(); //Check current placement status

                    } else {
                        log.debug("Player's map created. All ships set.");
                        System.out.println("All ships set. Map is being created.");
                        playerMap.printMap();
                        break;
                    }

                } catch (InputMismatchException e) {
                    log.error("Wrong input type!", e);
                    //System.out.println("Your input type was wrong! Try again!");
                    s.next();
                } catch (ArrayIndexOutOfBoundsException a) {
                    log.error("Entered position is too low, too high or the ship doesn't fit at this position!", a);
                    //System.out.println("Your entered position is too high or the ship doesn't fit at this position! Try again!");
                }
            }*/

            //Computer's ship placement
            Info.shipInfo(shipListAI);
            while(true) {
                try {
                    //todo -------- handle ship crossings?
                    // handle exceptions?
                    // delete shipSetPositions/surrounding positions from randomnumgenerator?
                    // just try as often as needed to place all ships? disable outputs that should become the user by using these methods (like setShip) ------------

                    for (int c = 0; c < shipListAI.size() ; c++) {
                        //do {
                            computerMap.setShip(shipListAI.get(c), aiRandom.randNumber(mapSize), aiRandom.randNumber(mapSize), aiRandom.randDir());
                            shipListAI.remove(shipListAI.get(c));
                        //} while (   //todo: hard to handle cases when ship isn't set correctly -> don't delete from list, try again AI !
                    }
                    log.debug("Computer's map created. All ships set.");
                    computerMap.printMap();
                    break;

                } catch (ArrayIndexOutOfBoundsException ignore) { } //catches Exception but ignores it to continue uninterrupted
            }


        } catch (IllegalFactoryArgument i) {
            log.error(i);
            System.exit(0);
        }
    }




}
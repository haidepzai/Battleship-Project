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

    final static int MAPSIZE = 9;

    public static void main(String[] args) {

        log.debug("Program started!");




        //todo write JUnit test's!!!
        //todo write logs , how many? where? when?

        //Creating the Map for the game depending of the MAPSIZE

        Field playerMap = new Field(MAPSIZE);
        Field computerMap = new Field(MAPSIZE);

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

            int playerFleet = shipList.size();
            int computerFleet = shipListAI.size();


            System.out.println("Ahoy! Set all your ships sailor!");



            while(true) {

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
            }


            //Computer's ship placement

            while(true) {

                try {

                    while(shipListAI.size() > 0) {

                        computerMap.setShipAI(shipListAI.get(0), aiRandom.randNumber(MAPSIZE), aiRandom.randNumber(MAPSIZE), aiRandom.randDir());
                        System.out.println();
                        shipListAI.remove(shipListAI.get(0));

                    }
                    log.debug("Computer's map created. All ships set.");
                    computerMap.printMap();
                    break;

                } catch (ArrayIndexOutOfBoundsException ignore) { } //catches Exception but ignores it to continue uninterrupted
            }

            //Info.shipInfo(shipListAI);

            shipList.remove(Battleship);
            System.out.println(playerFleet + ", " + computerFleet);

            //todo: -----------------------------------------------
            //todo: From here starts the battle-phase => STRG+C !!

            while(playerFleet != 0 && computerFleet != 0) {

                while (playerFleet != 0 && computerFleet != 0) {
                    log.debug("Player's attack phase");
                    System.out.println("Your turn. Enter a position you want to shoot" +
                            "\na) row" +
                            "\nb) col");
                    int row = s.nextInt();
                    int col = s.nextInt();
                    if (computerMap.checkShot(row, col) || computerMap.getStatus(row, col) == Field.HIT) {
                        System.out.println("Position has already been shot! Try again!");
                    } else {
                        computerMap.attack(row, col);
                        if (computerMap.field[row - 1][col - 1] == Field.HIT) {
                            System.out.println("You hit a ship! You have another try!");
                            computerFleet -= computerMap.checkShipState(row, col);
                            System.out.println("Computer has " + computerFleet + " left.");
                        } else {
                            System.out.println("Missed! Your turn is finished.");
                            break;
                        }
                    }
                }
                computerMap.printMap();

                while (playerFleet != 0 && computerFleet != 0) {
                    log.debug("Computer's attack phase");
                    int ranRow = aiRandom.randNumber(MAPSIZE);
                    int ranCol = aiRandom.randNumber(MAPSIZE);
                    if (playerMap.checkShot(ranRow, ranCol) || computerMap.getStatus(ranRow, ranCol) == Field.HIT) {
                        log.trace("Computer has already shot position (" + ranRow + ", " + ranCol + ")");
                    } else {
                        playerMap.attack(ranRow, ranCol);
                        if (playerMap.field[ranRow - 1][ranCol - 1] == Field.HIT) {
                            log.trace("Computer has hit a ship at (" + ranRow + ", " + ranCol + ")");
                            playerFleet -= playerMap.checkShipState(ranRow, ranCol);
                            System.out.println("You have " + playerFleet + " left.");
                        } else {
                            log.trace("Computer missed! Position: (" + ranRow + ", " + ranCol + "). Turn finished.");
                            break;
                        }
                    }
                }
                playerMap.printMap();
            }

            //Check who has won
            if(playerFleet == 0) {
                System.out.println("Computer has won the game!");
                log.debug("Computer won the game.");
            } else {
                System.out.println("You won the game! Congratulations sailor! Ahoy!");
                log.debug("Player won the game.");
            }


        } catch (IllegalFactoryArgument i) {
            log.error(i);
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException a) {
            log.error("Entered position is too low, too high or the ship doesn't fit at this position!", a);
        }

        log.debug("Exit program.");
    }




}
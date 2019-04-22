package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Ships.Battleship;
import de.hdm_stuttgart.mi.sd2.Ships.Cruiser;
import de.hdm_stuttgart.mi.sd2.Ships.Submarine;


public class Driver{
    public static void main(String[] args) {
        int mapSize = 6;
        Map playerMap = new Map(mapSize);
        playerMap.printMap();
        Map computerMap = new Map(mapSize);
        computerMap.printMap();

       Battleship Battleship = new Battleship();
        Cruiser Cruiser = new Cruiser();
        Submarine Submarine = new Submarine();


        playerMap.setShip(Battleship, 2, false);
        playerMap.printMap();




    }
}
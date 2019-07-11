package de.hdm_stuttgart.mi.sd2;

import java.util.Random;

public class aiRandom {

    /**
     * Returns random number for ship-placement depending on "mapSize"
     * @param mapSize Size of game-field
     * @return Random number (Type: int)
     */
     public static int randNumber(int mapSize) {

        return (int)(Math.random() * mapSize) + 1;

    }

    /**
     * @return Random boolean for ship-direction (True: horizontal, False: vertical)
     */
    public static boolean randDir() {

        Random random = new Random();

        return random.nextBoolean();

    }


}

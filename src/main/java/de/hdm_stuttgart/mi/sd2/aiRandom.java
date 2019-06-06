package de.hdm_stuttgart.mi.sd2;

import java.util.Random;

public class aiRandom {
     public static int randNumber(int mapSize) {

        return (int)(Math.random() * mapSize) + 1;

    }

    public static boolean randDir() {

        Random random = new Random();

        return random.nextBoolean();

    }


}

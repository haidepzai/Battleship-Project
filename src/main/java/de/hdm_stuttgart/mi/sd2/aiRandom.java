package de.hdm_stuttgart.mi.sd2;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class aiRandom {
    public static int randNumber(int mapsize) {

        return (int)(Math.random() * mapsize) + 1;

    }

    public static boolean randDir() {

        Random random = new Random();

        return random.nextBoolean();

    }


}

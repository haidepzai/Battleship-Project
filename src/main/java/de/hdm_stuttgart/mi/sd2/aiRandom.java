package de.hdm_stuttgart.mi.sd2;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class aiRandom {
    public static int randNumber(int mapSize) {

        int randNum = ThreadLocalRandom.current().nextInt(1,  mapSize+1);

        return randNum;

    }
    public static boolean randHor() {

        Random random = new Random();

        boolean dirBool = random.nextBoolean();

        return dirBool;

    }
}

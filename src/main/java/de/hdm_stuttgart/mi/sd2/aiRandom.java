package de.hdm_stuttgart.mi.sd2;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class aiRandom {
    public static int randNumber(int mapSize) {

        return ThreadLocalRandom.current().nextInt(1,  mapSize+1);

    }

    public static boolean randDir() {

        Random random = new Random();

        return random.nextBoolean();

    }


}

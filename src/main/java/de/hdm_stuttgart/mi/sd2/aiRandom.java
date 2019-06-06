package de.hdm_stuttgart.mi.sd2;

import java.util.Random;

class aiRandom {
     static int randNumber(int mapSize) {

        return (int)(Math.random() * mapSize) + 1;

    }

    static boolean randDir() {

        Random random = new Random();

        return random.nextBoolean();

    }


}

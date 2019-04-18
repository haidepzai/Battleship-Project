package de.hdm_stuttgart.mi.sd2;

import java.util.Arrays;

public class Map {

    public Map(int h) {
        int[][] field = new int[h][h];
        int counter = 0;
        for (int y = 0; y < field.length; y++) {

            field[y][y] = counter;
            for (int x = 0; x < field.length; x++) {
                counter++;
                field[y][x] = counter;
            }

        }

    }

    public void printMap(int h) {
        int[][] field = new int[h][h];
        int counter = 0;
        for (int y = 0; y < field.length; y++) {

            field[y][y] = counter;
            for (int x = 0; x < field.length; x++) {
                counter++;
                field[y][x] = counter;
            }

        }
        System.out.println(Arrays.deepToString(field));
    }
}
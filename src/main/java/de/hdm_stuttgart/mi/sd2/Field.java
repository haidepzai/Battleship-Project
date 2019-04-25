package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;

import java.util.Arrays;

public class Field {

    int h;
    int[][] field;

    public Field() {

    }

    public Field(int h) {
        this.h = h;
        int[][] field = new int[h][h];
        int counter = 0;
        for (int y = 0; y < field.length; y++) {

            field[y][y] = counter;
            for (int x = 0; x < field.length; x++) {
                counter++;
                field[y][x] = counter;
            }
        }
        this.field = field;
    }


    public void printMap() {
        System.out.println(Arrays.deepToString(field));

    }

    public int[][] getField() {
        return field;
    }


    public void setShip(IShip i, int pos, boolean up) {
        int size = i.getLength();

        if (up == false) {
            for (int e = 0; e < size; e++) {
                if (pos % h == 0) {
                    field[pos / h - 1][h - 1 + e] = 0;


                } else {
                    int col = pos / h;
                    int row = pos - col * h - 1;
                    field[col][row + e] = 0;

                }
            }

        }
        if (up == true) {
            for(int e = 0; e < size; e++) {
                field[e][pos-1] = 0;
            }

        }

    }

}
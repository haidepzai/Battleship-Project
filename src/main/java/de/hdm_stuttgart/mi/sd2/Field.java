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

    public int getSize() {
        return h;
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
            for (int e = 0; e < size; e++) {
                field[e][pos - 1] = 0;
            }

        }

    }

    boolean checkShip(Field f, int pos) {
        int h = f.getSize();
        int fa[][] = f.getField();

        if (pos % h == 0) {

            if (field[pos / h - 1][h - 1] == 0) {

                System.out.println("Here is a ship");
                return true;
            }

            System.out.println("Here is not a ship");
            return false;


        } else {
            int col = pos / h;
            int row = pos - col * h - 1;

            if (field[col][row] == 0) {

                System.out.println("Here is a ship");
                return true;
            }

            System.out.println("Here is not a ship");
            return false;

        }

    }

    boolean checkShot(Field f, int pos) {
        int h = f.getSize();
        int fa[][] = f.getField();

        if (pos % h == 0) {

            if (field[pos / h - 1][h - 1] == 99) {

                System.out.println("Shot");
                return true;
            }

            System.out.println("Not shot");
            return false;


        } else {
            int col = pos / h;
            int row = pos - col * h - 1;

            if (field[col][row] == 99) {

                System.out.println("Shot");
                return true;
            }

            System.out.println("Not shot");
            return false;

        }

    }

    static void attack(Field f, int pos) {
        int h = f.getSize();
        int[][] field = f.getField();

        if (pos % h == 0) {
            field[pos / h - 1][h - 1] = 99;


        } else {
            int col = pos / h;
            int row = pos - col * h - 1;
            field[col][row] = 99;

        }

    }

}
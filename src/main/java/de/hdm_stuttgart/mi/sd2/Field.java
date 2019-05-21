package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;

import java.util.ArrayList;
import java.util.Arrays;

public class Field {

    static int h;
    int[][] field;

    public Field() {

    }

    /**
     * Creates the game-field as a two-dimensional Array
     *
     * @param h Length/Width of the field
     */
    /*public Field(int h) {
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
    */
    public Field(int h) {
        this.h = h;
        int[][] field = new int[h][h];

        for (int y = 0; y < field.length; y++) {
            int counter = 0;
            field[y][y] = counter;
            for (int x = 0; x < field.length; x++) {
                counter++;
                field[y][x] = counter;
            }
        }
        this.field = field;
    }


    /**
     * Print out the two-dimensional Array
     */
    public void printMap() {
        System.out.println(Arrays.deepToString(field));

    }

    /**
     * @return Return game-field
     */
    public int[][] getField() {
        return field;
    }

    /**
     * @return Return size(length/width) of the game-field
     */
    public int getSize() {
        return h;
    }

    /*/**
     * Place the ship on the game-field
     *
     * @param i   The ship-type which shall be placed
     * @param pos Position where the ship shall be placed
     * @param up  Direction of the ship
     */
    /*
    public void setShip(IShip i, int pos1, String up) {
        int size = i.getLength();

        if (up.equals("n") || up.equals("no")) {
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
        if (up.equals("y") || up.equals("yes")) {
            for (int e = 0; e < size; e++) {
                field[e][pos - 1] = 0;
            }

        }

    }
*/

    /**
     * Check whether on the desired location is already a ship placed
     * @param i Ship-type
     * @param row Selected row for ship placement
     * @param col Selected column for ship placement
     * @param dir Horizontal placement? True/False
     * @return True: Already a ship placed , False: Position free
     */
    public boolean checkShip(IShip i, int row, int col, boolean dir) {
        int size = i.getLength();

        //Check if there is already a ship at the desired position
        if (!dir) {

            for (int f = 0; f < size; f++) {
                if (field[row - 1 + f][col - 1] == -1) {
                    return false;
                }
            }
            return true;
        } else {
            for (int f = 0; f < size; f++) {
                if(field[row - 1][col - 1 + f] == -1) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Places a ship on the game-field
     * @param i Ship-type
     * @param row Selected row for ship placement
     * @param col Selected column for ship placement
     * @param dir Horizontal placement? True/False
     */
    public void setShip(IShip i, int row, int col, boolean dir) {

        int size = i.getLength();

        if (checkShip(i, row, col, dir)) {
            if (!dir) {
                for (int v = 0; v < size; v++) {
                    field[row - 1 + v][col - 1] = -1;
                }
            } else {
                for (int v = 0; v < size; v++) {
                    field[row - 1][col - 1 + v] = -1;
                }
            }
            Driver.shipList.remove(i);
        } else {
            System.out.println("There is already a ship at the desired position!");
        }
    }
/*
    /**
     * Check a position whether there is a ship or not
     *
     * @param f   Look at player's or computer's ships
     * @param pos Which position shall be checked
     * @return True if there is a ship, false if not
     */
/*
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

    } */


    /**
     * Check if a position has already been shot
     *
     * @param f   Look at player's or computer's shots
     * @param pos Which position shall be checked
     * @return True if the position has been shot, false if not
     */
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

    /**
     * Attack a position on the game-field
     *
     * @param f   Player's or Computer's game-field
     * @param pos Position that shall be attacked
     */
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

    /**
     * @param f     Player's or Computer's game-field
     * @param pos   Position which is gonna be checked
     * @return the current status of "pos" in "field f"
     */
    public int status(Field f, int pos) {
        int h = f.getSize();
        int fa[][] = f.getField();

        if (pos % h == 0) {

            if (field[pos / h - 1][h - 1] == 99) {

                System.out.println("Shot");
                return 99;
            } else if (field[pos / h - 1][h - 1] == 0) {
                System.out.println("Ship set here");
                return 0;
            } else if (field[pos / h - 1][h - 1] != 0 && field[pos / h - 1][h - 1] != 99) {
                System.out.println("Water");
                return 1;
            }


        } else {
            int col = pos / h;
            int row = pos - col * h - 1;

            if (field[col][row] == 99) {

                System.out.println("Shot");
                return 99;
            } else if (field[col][row] == 0) {
                System.out.println("Ship set here");
                return 0;
            }
                System.out.println("Water");
                return 1;

        }
        return 1;
    }

}
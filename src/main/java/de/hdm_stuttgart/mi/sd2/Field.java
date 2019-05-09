package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;

import java.util.Arrays;

public class Field {

    int h;
    int[][] field;

    public Field() {

    }

    /**
     * Creates the game-field as a two-dimensional Array
     *
     * @param h Length/Width of the field
     */
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

    /**
     * Place the ship on the game-field
     *
     * @param i   The ship-type which shall be placed
     * @param pos Position where the ship shall be placed
     * @param up  Direction of the ship
     */
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

    /**
     * Check a position whether there is a ship or not
     *
     * @param f   Look at player's or computer's ships
     * @param pos Which position shall be checked
     * @return True if there is a ship, false if not
     */

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
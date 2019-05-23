package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;

import java.util.ArrayList;
import java.util.Arrays;

public class Field {

    static int h;
    int[][] field;

        
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
        //System.out.println(Arrays.deepToString(field));
        for(int i = 0; i < h; i++)
        {
            for(int j = 0; j < h; j++)
            {
                System.out.printf("%3d ", field[i][j]);
            }
            System.out.println();
        }
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
                //1. Case: Ship in the upper left corner
                if(row == 1 && col == 1) {
                    //todo: last condition in the if clause the same for all conditions => export?! => performance
                    if(field[row-1+f][col-1] == -1 || field[row-1+f][col] == -1 || field[row-1+size][col-1] == -1) {
                        return true;
                    } else {
                        return false;
                    }
                }
                //2. Case: Ship in the upper right corner
                if(row == 1 && col == h) {
                    if(field[row-1+f][col-2] == -1 || field[row-1+f][col-1] == -1  || field[row-1+size][col-1] == -1) {
                        return true;
                    } else {
                        return false;
                    }
                }
                //3. Case: Ship at the top of the map but not in a corner
                if(row == 1) {
                    if(field[row-1+f][col-2] == -1 || field[row-1+f][col-1] == -1 || field[row-1+f][col] == -1 || field[row-1+size][col-1] == -1) {
                        return true;
                    } else {
                        return false;
                    }
                }
                //4. Case: Ship at the bottom left corner
                if(row == h-size && col == 1) {

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

    /**
     * Check if a position has already been shot
     * @param row Row of position that shall be checked
     * @param col Column of position that shall be checked
     * @return True: Position has already been shot , False: Not been shot
     */
    boolean checkShot(int row, int col) {

       if(field[row-1][col-1] == -99) {
           return true;
       } else {
           return false;
       }
    }

    /**
     * Attack position on game-field
     * @param row Row of position that shall be shot
     * @param col Column of position that shall be shot
     */
    public void attack(int row, int col) {

        field[row - 1][col - 1] = -99;

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
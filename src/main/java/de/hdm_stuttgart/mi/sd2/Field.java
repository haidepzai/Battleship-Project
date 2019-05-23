package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;

import java.util.ArrayList;
import java.util.Arrays;

public class Field {

    static int h;
    int[][] field;

    //todo: statische Zustandsvariablen korrekt? sinnvoll?
    static final int BORDER = -42;
    static final int WATER = 0;
    static final int SHIP = -1;

    public Field(int h) {
        this.h = h;
        int[][] field = new int[h][h];

        for (int y = 0; y < field.length; y++) {
            int counter = 0;
            field[y][y] = counter;
            for (int x = 0; x < field.length; x++) {
                //counter++;
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

        //Vertical
        if (!dir) {
            for(int v = 0; v < size; v++) {
                if (getLeft(row+v, col) == SHIP || getRight(row+v, col) == SHIP || getTop(row+v, col) == SHIP || getBot(row+v, col) == SHIP) {
                    return true;
                }
            }
            return false;

        //Horizontal
        } else {
            for (int h = 0; h < size; h++) {
                if (getLeft(row, col+h) == SHIP || getRight(row, col+h) == SHIP || getTop(row, col+h) == SHIP || getBot(row, col+h) == SHIP) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Check the status of the position on the LEFT side of the transferred position
     * @param row Row of passed parameters
     * @param col Column of passed parameters
     * @return BORDER: Left-border of the Array / SHIP: Ship on the left side / WATER: Water (no ship) on the left side
     */
    public int getLeft(int row, int col) {
        if(col == 1) {
            return BORDER;
        } else if (field[row-1][col-2] == -1){
            return SHIP;
        } else {
            return WATER; //todo: Add more states later!!
        }
    }

    /**
     * Check the status of the position on the RIGHT side of the transferred position
     * @param row Row of passed parameters
     * @param col Column of passed parameters
     * @return BORDER: Right-border of the Array / SHIP: Ship on the right side / WATER: Water, free space on the right side
     */
    public int getRight(int row, int col) {
        if(col == h) {
            return BORDER;
        } else if (field[row-1][col] == -1){
            return SHIP;
        } else {
            return WATER; //todo: Add more states later!!
        }
    }

    /**
     * Check the status of the position ABOVE the transferred position
     * @param row Row of passed parameters
     * @param col Column of passed parameters
     * @return BORDER: Top-border of the Array / SHIP: Ship above / WATER: Water, free space above
     */
    public int getTop(int row, int col) {
        if(row == 1) {
            return BORDER;
        } else if (field[row-2][col-1] == -1){
            return SHIP;
        } else {
            return WATER; //todo: Add more states later!!
        }
    }

    /**
     * Check the status of the position BENEATH the transferred position
     * @param row Row of passed parameters
     * @param col Column of passed parameters
     * @return BORDER: Bottom-border of the Array / SHIP: Ship beneath / WATER: Water, free space beneath
     */
    public int getBot(int row, int col) {
        if(row == h) {
            return BORDER;
        } else if (field[row][col-1] == -1){
            return SHIP;
        } else {
            return WATER; //todo: Add more states later!!
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

        if (!checkShip(i, row, col, dir)) {
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
            System.out.println("No ship-placement at this position possible! Try again!");
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
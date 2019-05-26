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
    static final int SHOT = -99;
    static final int HIT = -100;

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
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < h; j++) {
                System.out.printf("%4d ", field[i][j]);
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


    public int getStatus(int row, int col) {
        if (field[row - 1][col - 1] == -1) {
            return SHIP;
        } else if (field[row - 1][col - 1] == -99) {
            return SHOT;
        } else if (field[row - 1][col - 1] == -100) {
            return HIT;
        } else {
            return WATER;
        }
    }

    /**
     * Check the status of the position on the LEFT side of the transferred position
     *
     * @param row Row of passed parameters
     * @param col Column of passed parameters
     * @return BORDER: Left-border of the Array / SHIP: Ship on the left side / WATER: Water (no ship) on the left side
     */
    public int getLeft(int row, int col) {
        if (col == 1) {
            return BORDER;
        } else if (field[row - 1][col - 2] == -1) {
            return SHIP;
        } else if (field[row - 1][col - 2] == -99) {
            return SHOT;
        } else {
            return WATER; //todo: Add more states later!!
        }
    }

    /**
     * Check the status of the position on the RIGHT side of the transferred position
     *
     * @param row Row of passed parameters
     * @param col Column of passed parameters
     * @return BORDER: Right-border of the Array / SHIP: Ship on the right side / WATER: Water, free space on the right side
     */
    public int getRight(int row, int col) {
        if (col == h) {
            return BORDER;
        } else if (field[row - 1][col] == -1) {
            return SHIP;
        } else if (field[row - 1][col] == -99) {
            return SHOT;
        } else {
            return WATER; //todo: Add more states later!!
        }
    }

    /**
     * Check the status of the position ABOVE the transferred position
     *
     * @param row Row of passed parameters
     * @param col Column of passed parameters
     * @return BORDER: Top-border of the Array / SHIP: Ship above / WATER: Water, free space above
     */
    public int getTop(int row, int col) {
        if (row == 1) {
            return BORDER;
        } else if (field[row - 2][col - 1] == -1) {
            return SHIP;
        } else if (field[row - 2][col - 1] == -99) {
            return SHOT;
        } else {
            return WATER; //todo: Add more states later!!
        }
    }

    /**
     * Check the status of the position BENEATH the transferred position
     *
     * @param row Row of passed parameters
     * @param col Column of passed parameters
     * @return BORDER: Bottom-border of the Array / SHIP: Ship beneath / WATER: Water, free space beneath
     */
    public int getBot(int row, int col) {
        if (row == h) {
            return BORDER;
        } else if (field[row][col - 1] == -1) {
            return SHIP;
        } else if (field[row][col - 1] == -99) {
            return SHOT;
        } else {
            return WATER; //todo: Add more states later!!
        }
    }

    /**
     * Check whether on the desired location is already a ship placed
     *
     * @param i   Ship-type
     * @param row Selected row for ship placement
     * @param col Selected column for ship placement
     * @param dir Horizontal placement? True/False
     * @return True: Already a ship placed , False: Position free
     */
    public boolean checkShip(IShip i, int row, int col, boolean dir) {
        int size = i.getLength();

        //Vertical
        if (!dir) {
            for (int v = 0; v < size; v++) {
                if (getStatus(row + v, col) == SHIP || getLeft(row + v, col) == SHIP || getRight(row + v, col) == SHIP || getTop(row + v, col) == SHIP || getBot(row + v, col) == SHIP) {
                    return true;
                }
            }
            return false;

            //Horizontal
        } else {
            for (int h = 0; h < size; h++) {
                if (getStatus(row, col + h) == SHIP || getLeft(row, col + h) == SHIP || getRight(row, col + h) == SHIP || getTop(row, col + h) == SHIP || getBot(row, col + h) == SHIP) {
                    return true;
                }
            }
            return false;
        }
    }

    public void setCore(IShip i, int row, int col, boolean dir) {
        int size = i.getLength();

        if (!dir) {
            for (int v = 0; v < size; v++) {
                field[row - 1 + v][col - 1] = -1;
            }
        } else {
            for (int v = 0; v < size; v++) {
                field[row - 1][col - 1 + v] = -1;
            }
        }
    }

    /**
     * Places a ship on the game-field
     *
     * @param i   Ship-type
     * @param row Selected row for ship placement
     * @param col Selected column for ship placement
     * @param dir Horizontal placement? True/False
     */
    public void setShip(IShip i, int row, int col, boolean dir) {

        if (!checkShip(i, row, col, dir)) {
            setCore(i, row, col, dir);
            Driver.shipList.remove(i);
        } else {
            System.out.println("\nFAILURE: No ship-placement at this position possible! Try again!\n");
        }
    }

    public void setShipAI(IShip i, int row, int col, boolean dir) {

        if (!checkShip(i, row, col, dir)) {
            setCore(i, row, col, dir);
        } else {
            setShipAI(i, aiRandom.randNumber(h), aiRandom.randNumber(h), aiRandom.randDir());
        }
    }

    /**
     * Check if a position has already been shot
     *
     * @param row Row of position that shall be checked
     * @param col Column of position that shall be checked
     * @return True: Position has already been shot , False: Not been shot
     */
    boolean checkShot(int row, int col) {

        if (field[row - 1][col - 1] == -99) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attack position on game-field
     *
     * @param row Row of position that shall be shot
     * @param col Column of position that shall be shot
     */
    public void attack(int row, int col) {

        if (getStatus(row, col) == SHIP) {
            field[row - 1][col - 1] = HIT;
        } else {
            field[row - 1][col - 1] = -99;
        }

    }

    public int checkShipState(int row, int col) {

        if(checkVertical(row, col) && checkHorizontal(row, col)) {
            System.out.println("You have destroyed a ship.");
            return 1;
        } else {
            return 0;
        }

    }

    public boolean checkHorizontal(int row, int col) {

        //Check left side
        for(int l = 0; getLeft(row, col-l) != WATER && getLeft(row, col-l) != SHOT && getLeft(row, col-l) != BORDER; l++) {   //todo: bis auf SHOT/BORDER/WATER trifft!
            if(getLeft(row, col-l) == SHIP) {
                return false;
            }
        }
        //Check right side
        for(int r = 0; getRight(row, col+r) != WATER && getRight(row, col+r) != SHOT && getRight(row, col+r) != BORDER; r++) {
            if(getRight(row, col+r) == SHIP) {
                return false;
            }
        }
        return true;
    }

    public boolean checkVertical(int row, int col) {

        //Check top side
        for(int t = 0; getTop(row-t, col) != WATER && getTop(row-t, col) != SHOT && getTop(row-t, col) != BORDER; t++) {   //todo: bis auf SHOT/BORDER/WATER trifft!
            if(getTop(row-t, col) == SHIP) {
                return false;
            }
        }
        //Check bot side
        for(int b = 0; getBot(row+b, col) != WATER && getBot(row+b, col) != SHOT && getBot(row+b, col) != BORDER; b++) {
            if(getBot(row+b, col) == SHIP) {
                return false;
            }
        }

        return true;
    }
}

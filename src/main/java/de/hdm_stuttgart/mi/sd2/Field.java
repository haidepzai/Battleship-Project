package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Gui.ShipPlacementController;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;

public class Field {

    private int h;
    private int[][] field;

    private static final int BORDER = -42;
    private static final int WATER = 0;
    public static final int SHIP = -1;
    public static final int SHOT = -99;
    public static final int HIT = -100;

    /**
     * Constructor that creates a new two-dimensional game-field
     * @param h Size (edge length) of the square game-field
     */
    public Field(int h) {
        this.h = h;
        this.field = new int[h][h];
    }

    /**
     * Clears/Reset a field for a game-restart
     */
    public void clearField() {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < h; j++) {
                field[i][j] = 0;
            }
        }
    }

    /**
     * Method that returns the status of the selected position
     *
     * @param row Row of selected position
     * @param col Column of selected position
     * @return SHIP: Ship on the position
     *         SHOT: Position was shot, but didn't hit a ship
     *         HIT: Position was shot and hit a ship
     *         WATER: Water on the position (unaffected state)
     */
    public int getStatus(int row, int col) {
        if (field[row - 1][col - 1] == SHIP) {
            return SHIP;
        } else if (field[row - 1][col - 1] == SHOT) {
            return SHOT;
        } else if (field[row - 1][col - 1] == HIT) {
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
    private int getLeft(int row, int col) {
        if (col == 1) {
            return BORDER;
        } else if (field[row - 1][col - 2] == SHIP) {
            return SHIP;
        } else if (field[row - 1][col - 2] == SHOT) {
            return SHOT;
        } else if (field[row - 1][col - 2] == HIT) {
            return HIT;
        } else {
            return WATER;
        }
    }

    /**
     * Check the status of the position on the RIGHT side of the transferred position
     *
     * @param row Row of passed parameters
     * @param col Column of passed parameters
     * @return BORDER: Right-border of the Array / SHIP: Ship on the right side / WATER: Water, free space on the right side
     */
    private int getRight(int row, int col) {
        if (col == h) {
            return BORDER;
        } else if (field[row - 1][col] == SHIP) {
            return SHIP;
        } else if (field[row - 1][col] == SHOT) {
            return SHOT;
        } else if (field[row - 1][col] == HIT) {
            return HIT;
        } else {
            return WATER;
        }
    }

    /**
     * Check the status of the position ABOVE the transferred position
     *
     * @param row Row of passed parameters
     * @param col Column of passed parameters
     * @return BORDER: Top-border of the Array / SHIP: Ship above / WATER: Water, free space above
     */
    private int getTop(int row, int col) {
        if (row == 1) {
            return BORDER;
        } else if (field[row - 2][col - 1] == SHIP) {
            return SHIP;
        } else if (field[row - 2][col - 1] == SHOT) {
            return SHOT;
        } else if (field[row - 2][col - 1] == HIT) {
            return HIT;
        } else {
            return WATER;
        }
    }

    /**
     * Check the status of the position BENEATH the transferred position
     *
     * @param row Row of passed parameters
     * @param col Column of passed parameters
     * @return BORDER: Bottom-border of the Array / SHIP: Ship beneath / WATER: Water, free space beneath
     */
    private int getBot(int row, int col) {
        if (row == h) {
            return BORDER;
        } else if (field[row][col - 1] == SHIP) {
            return SHIP;
        } else if (field[row][col - 1] == SHOT) {
            return SHOT;
        } else if (field[row][col - 1] == HIT) {
            return HIT;
        } else {
            return WATER;
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

    /**
     * Core method for ship placement on the game-field
     *
     * @param i Ship type
     * @param row Selected row
     * @param col Selected column
     * @param dir Selected direction: Horizontal? True/False
     */
    void setCore(IShip i, int row, int col, boolean dir) {
        int size = i.getLength();

        if (!dir) {
            for (int v = 0; v < size; v++) {
                field[row - 1 + v][col - 1] = SHIP;
            }
        } else {
            for (int v = 0; v < size; v++) {
                field[row - 1][col - 1 + v] = SHIP;
            }
        }
    }

    /**
     * Handles the ship placement of the player by checking if a placement is possible at the selected position.
     * If yes, the ship will be placed and removied out of the ship list
     *
     * @param i   Ship-type
     * @param row Selected row for ship placement
     * @param col Selected column for ship placement
     * @param dir Horizontal placement? True/False
     */
    public boolean setShip(IShip i, int row, int col, boolean dir) {

        if (!checkShip(i, row, col, dir)) {
            setCore(i, row, col, dir);
            ShipPlacementController.shipList.remove(i);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Handles the ship placement for the computer (functionality: look "setShip")
     *
     * @param i Ship-type
     * @param row Randomly selected row for ship placement
     * @param col Randomly selected column for ship placement
     * @param dir Randomly selected direction of the ship. Horizontal? True/False
     */
    public void setShipAI(IShip i, int row, int col, boolean dir) {

        if (!checkShip(i, row, col, dir)) {
            setCore(i, row, col, dir);
            ShipPlacementController.shipListAI.remove(i);

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
            field[row - 1][col - 1] = SHOT;
        }

    }

    /**
     * Checks whether a ship is destroyed or not by using checkVertical/checkHorizontal methods
     *
     * @param row Row of selected position
     * @param col Column of selected position
     * @return True, if the ship is destroyed
     */
    public boolean checkShipState(int row, int col) {

        return (checkVertical(row, col) && checkHorizontal(row, col));

    }


    /**
     * Checks, whether in horizontal direction is no cell with the status SHIP left which belongs to the hit ship
     *
     * @param row Row of selected position
     * @param col Column of selected position
     * @return True, if the ship is destroyed
     */
    private boolean checkHorizontal(int row, int col) {

        //Check left side
        for (int l = 0; getLeft(row, col - l) != WATER && getLeft(row, col - l) != SHOT && getLeft(row, col - l) != BORDER; l++) {
            if (getLeft(row, col - l) == SHIP) {
                return false;
            }
        }
        //Check right side
        for (int r = 0; getRight(row, col + r) != WATER && getRight(row, col + r) != SHOT && getRight(row, col + r) != BORDER; r++) {
            if (getRight(row, col + r) == SHIP) {
                return false;
            }
        }
        return true;
    }


    /**
     * Checks, whether in vertical direction is no cell with the status SHIP left which belongs to the hit ship
     *
     * @param row Row of selected position
     * @param col Column of selected position
     * @return True, if the ship is destroyed
     */
    private boolean checkVertical(int row, int col) {

        //Check top side
        for (int t = 0; getTop(row - t, col) != WATER && getTop(row - t, col) != SHOT && getTop(row - t, col) != BORDER; t++) {
            if (getTop(row - t, col) == SHIP) {
                return false;
            }
        }
        //Check bot side
        for (int b = 0; getBot(row + b, col) != WATER && getBot(row + b, col) != SHOT && getBot(row + b, col) != BORDER; b++) {
            if (getBot(row + b, col) == SHIP) {
                return false;
            }
        }

        return true;
    }
}

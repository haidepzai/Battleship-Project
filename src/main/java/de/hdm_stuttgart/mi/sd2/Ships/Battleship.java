package de.hdm_stuttgart.mi.sd2.Ships;

import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;

public class Battleship implements IShip {
    @Override
    public int getLength() {
        return 4;
    }

    @Override
    public String getName() {
        return "Destroyer";
    }
}

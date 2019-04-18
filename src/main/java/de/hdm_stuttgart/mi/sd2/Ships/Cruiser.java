package de.hdm_stuttgart.mi.sd2.Ships;

import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;

public class Cruiser implements IShip {
    @Override
    public int getLength() {
        return 3;
    }

    @Override
    public String getName() {
        return "Cruiser";
    }
}

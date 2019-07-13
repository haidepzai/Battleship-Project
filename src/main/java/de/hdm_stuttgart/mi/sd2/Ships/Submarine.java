package de.hdm_stuttgart.mi.sd2.Ships;

import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;

class Submarine implements IShip {
    @Override
    public int getLength() {
        return 1;
    }

    @Override
    public String getName() {
        return "Submarine";
    }
}

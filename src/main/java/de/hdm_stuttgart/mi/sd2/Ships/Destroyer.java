package de.hdm_stuttgart.mi.sd2.Ships;

import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;

class Destroyer implements IShip {
    @Override
    public int getLength() {
        return 2;
    }

    @Override
    public String getName() {
        return "Destroyer";
    }
}

package de.hdm_stuttgart.mi.sd2.Interfaces;

public interface IShip {
    enum ShipType {

        DESTROYER,
        SUBMARINE,
        CRUISER,
        BATTLESHIP;

        ShipType() {
        }
    }

    int getLength();
    String getName();
}




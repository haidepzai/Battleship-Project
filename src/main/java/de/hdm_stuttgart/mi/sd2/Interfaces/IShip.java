package de.hdm_stuttgart.mi.sd2.Interfaces;

public interface IShip {
    public enum ShipType{

        DESTROYER,
        SUBMARINE,
        CRUISER,
        BATTLESHIP;

        private ShipType() {
        }
    }

    public int getLength();
    public String getName();
}




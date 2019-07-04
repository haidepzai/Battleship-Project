package de.hdm_stuttgart.mi.sd2.Ships;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;

public class ShipFactory {
    public static synchronized IShip createShip(IShip.ShipType i) throws IllegalFactoryArgument {
        switch (i) {
            case DESTROYER: return new Destroyer() ;
            case CRUISER:return new Cruiser();
            case SUBMARINE: return new Submarine();
            case BATTLESHIP: return new Battleship();
            default: throw new IllegalFactoryArgument("Invalid Ship type!");

        }
    }
}

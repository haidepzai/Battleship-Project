package de.hdm_stuttgart.mi.sd2.Threads;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Gui.ShipPlacementController;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.ShipFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("Duplicates")
public class ShipListCreator implements Runnable {

    private static Logger log = LogManager.getLogger(ShipListCreator.class);

    @Override
    public synchronized void run() {
        try {
            IShip Battleship;
            IShip Cruiser1;
            IShip Cruiser2;
            IShip Destroyer1;
            IShip Destroyer2;
            IShip Submarine1;
            IShip Submarine2;

            Cruiser1 = ShipFactory.createShip(IShip.ShipType.CRUISER);
            Cruiser2 = ShipFactory.createShip(IShip.ShipType.CRUISER);
            Destroyer1 = ShipFactory.createShip(IShip.ShipType.DESTROYER);
            Destroyer2 = ShipFactory.createShip(IShip.ShipType.DESTROYER);
            Submarine1 = ShipFactory.createShip(IShip.ShipType.SUBMARINE);
            Submarine2 = ShipFactory.createShip(IShip.ShipType.SUBMARINE);
            Battleship = ShipFactory.createShip(IShip.ShipType.BATTLESHIP);

            ShipPlacementController.shipListAI.add(Battleship);
            ShipPlacementController.shipListAI.add(Cruiser1);
            ShipPlacementController.shipListAI.add(Cruiser2);
            ShipPlacementController.shipListAI.add(Destroyer1);
            ShipPlacementController.shipListAI.add(Destroyer2);
            ShipPlacementController.shipListAI.add(Submarine1);
            ShipPlacementController.shipListAI.add(Submarine2);

            ShipPlacementController.playerFleet = ShipPlacementController.shipList.size();

        } catch (IllegalFactoryArgument i) {
            log.error(i);
            System.exit(0);
        }

    }
}

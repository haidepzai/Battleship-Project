package de.hdm_stuttgart.mi.sd2.Threads;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Gui.ShipPlacementController;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.ShipFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class ShipListCreator implements Runnable {

    final private static Logger log = LogManager.getLogger(ShipListCreator.class);
    private static List<IShip> shipList = new ArrayList<>();

    @Override
    public void run() {
        log.debug("Thread for creation of shipList started");
        try {
            IShip Cruiser1 = ShipFactory.createShip(IShip.ShipType.CRUISER);
            IShip Cruiser2 = ShipFactory.createShip(IShip.ShipType.CRUISER);
            IShip Destroyer1 = ShipFactory.createShip(IShip.ShipType.DESTROYER);
            IShip Destroyer2 = ShipFactory.createShip(IShip.ShipType.DESTROYER);
            IShip Submarine1 = ShipFactory.createShip(IShip.ShipType.SUBMARINE);
            IShip Submarine2 = ShipFactory.createShip(IShip.ShipType.SUBMARINE);
            IShip Battleship = ShipFactory.createShip(IShip.ShipType.BATTLESHIP);

            shipList.add(Battleship);
            shipList.add(Cruiser1);
            shipList.add(Cruiser2);
            shipList.add(Destroyer1);
            shipList.add(Destroyer2);
            shipList.add(Submarine1);
            shipList.add(Submarine2);

            ShipPlacementController.setShipList(shipList);
            log.debug("Assigned value of \"shipList\" to copy in \"ShipPlacementController\"");

        } catch (IllegalFactoryArgument i) {
            log.fatal("Invalid ship type found! System exited: " + i);
            System.exit(0);
        }
        log.debug("Thread for creation of shipList finished");
    }
}

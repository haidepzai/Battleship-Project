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

    private static Logger log = LogManager.getLogger(ShipListCreator.class);
    private static List<IShip> shipList = new ArrayList<>();

    @Override
    public void run() {
        log.debug("Thread for creation of shipList started");
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

            shipList.add(Battleship);
            shipList.add(Cruiser1);
            shipList.add(Cruiser2);
            shipList.add(Destroyer1);
            shipList.add(Destroyer2);
            shipList.add(Submarine1);
            shipList.add(Submarine2);

            //ShipPlacementController spc = new ShipPlacementController();
            ShipPlacementController.setShipList(shipList);

        } catch (IllegalFactoryArgument i) {
            log.error(i);
            System.exit(0);
        }
        log.debug("Thread for creation of shipList started");
    }
}

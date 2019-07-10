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
public class ShipListAICreator implements Runnable {

    private static Logger log = LogManager.getLogger(ShipListAICreator.class);
    private static List<IShip> shipListAI = new ArrayList<>();

    @Override
    public void run() {
        log.info("Thread for creation of shipListAI started");
        try {
            IShip BattleshipAI1;
            IShip CruiserAI1;
            IShip CruiserAI2;
            IShip DestroyerAI1;
            IShip DestroyerAI2;
            IShip SubmarineAI1;
            IShip SubmarineAI2;

            CruiserAI1 = ShipFactory.createShip(IShip.ShipType.CRUISER);
            CruiserAI2 = ShipFactory.createShip(IShip.ShipType.CRUISER);
            DestroyerAI1 = ShipFactory.createShip(IShip.ShipType.DESTROYER);
            DestroyerAI2 = ShipFactory.createShip(IShip.ShipType.DESTROYER);
            SubmarineAI1 = ShipFactory.createShip(IShip.ShipType.SUBMARINE);
            SubmarineAI2 = ShipFactory.createShip(IShip.ShipType.SUBMARINE);
            BattleshipAI1 = ShipFactory.createShip(IShip.ShipType.BATTLESHIP);

            shipListAI.add(BattleshipAI1);
            shipListAI.add(CruiserAI1);
            shipListAI.add(CruiserAI2);
            shipListAI.add(DestroyerAI1);
            shipListAI.add(DestroyerAI2);
            shipListAI.add(SubmarineAI1);
            shipListAI.add(SubmarineAI2);

            ShipPlacementController.setShipListAI(shipListAI);
            log.debug("Assigned value of \"shipListAI\" to copy in \"ShipPlacementController\"");

        } catch (IllegalFactoryArgument i) {
            log.error(i + ": Invalid ship type found!");
            System.exit(0);
        }
        log.info("Thread for creation of shipListAI finished");
    }
}

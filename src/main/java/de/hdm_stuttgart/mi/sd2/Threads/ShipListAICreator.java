package de.hdm_stuttgart.mi.sd2.Threads;

import de.hdm_stuttgart.mi.sd2.Exceptions.IllegalFactoryArgument;
import de.hdm_stuttgart.mi.sd2.Gui.ShipPlacementController;
import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;
import de.hdm_stuttgart.mi.sd2.Ships.ShipFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("Duplicates")
public class ShipListAICreator implements Runnable {

    private static Logger log = LogManager.getLogger(ShipListAICreator.class);

    @Override
    public synchronized void run() {
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

            ShipPlacementController.shipListAI.add(BattleshipAI1);
            ShipPlacementController.shipListAI.add(CruiserAI1);
            ShipPlacementController.shipListAI.add(CruiserAI2);
            ShipPlacementController.shipListAI.add(DestroyerAI1);
            ShipPlacementController.shipListAI.add(DestroyerAI2);
            ShipPlacementController.shipListAI.add(SubmarineAI1);
            ShipPlacementController.shipListAI.add(SubmarineAI2);

            ShipPlacementController.computerFleet = ShipPlacementController.shipListAI.size();

        } catch (IllegalFactoryArgument i) {
            log.error(i);
            System.exit(0);
        }
    }
}

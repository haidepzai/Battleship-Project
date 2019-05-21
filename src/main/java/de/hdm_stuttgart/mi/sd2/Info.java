package de.hdm_stuttgart.mi.sd2;

import de.hdm_stuttgart.mi.sd2.Interfaces.IShip;

import java.util.List;

public class Info {
   public static void shipInfo(List<IShip> s){
        for (int i = 0; i < s.size(); i++) {
            System.out.print(s.get(i).getName() + " ");
        }
    }

    public static void shipPlaceQuestion() {
        //System.out.println("1. Where do you want to set your ship? \n2. Place it horizontal? y (yes) / n (no) ");
        System.out.println("1. Where do you want to set your ship? \n  a) Row (1-" + Field.h + ")\n  b) Column (1-" + Field.h + ")\n2. Place it horizontal? true / false");
    }

}

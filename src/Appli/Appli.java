package Appli;

import ThreeSpotGame.Element;
import ThreeSpotGame.Plateau;

public class Appli {
    public static void main(String[] args) {
        Plateau p = new Plateau();
        p.initPlateau();
        System.out.println(p.toString());
    }
}
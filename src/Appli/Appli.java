package Appli;

import ThreeSpotGame.Plateau;

public class Appli {
    public static void main(String[] args) {
        Plateau p = new Plateau();
        p.start3SG();
        System.out.println(p.toString());
    }
}
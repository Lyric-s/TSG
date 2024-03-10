package ThreeSpotGame;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Element {
    private final char id;
    private int xa, ya, xb, yb;
    private boolean enMouvement;

    public Element(char id, int xa, int ya, int xb, int yb) {
        assertTrue((xb-xa)+(yb-ya)==1 || (xb-xa)+(ya-yb)==1 || (ya-yb)+(xa-xb)==1 || (yb-ya)+(xa-xb)==1);
        this.id = id;
        this.xa = xa;
        this.ya = ya;
        this.xb = xb;
        this.yb = yb;
        this.enMouvement = false;
    }

    public char getId() {
        return id;
    }

    public int getXa() {
        return xa;
    }

    public int getXb() {
        return xb;
    }

    public int getYa() {
        return ya;
    }

    public int getYb() {
        return yb;
    }

    public boolean estEnMouvement() {
        return enMouvement;
    }

    public void setEnMouvement(boolean enMouvement) {
        this.enMouvement = enMouvement;
    }

    //    public void deplacerNord() {
//        xa -= 1;
//        xb -= 1;
//    }
//    public void deplacerSud() {
//        xa += 1;
//        xb += 1;
//    }
//    public void deplacerEst() {
//        ya += 1;
//        yb += 1;
//    }
//    public void deplacerOuest() {
//        ya -= 1;
//        yb -= 1;
//    }

//    public void deplaceDiagonaDescGauche() {
//        x += 1;
//        y += 1;
//    }

//    public void pivoterPlus() {
//        if (ya == yb && xa < xb) {
//            xb -= 1;
//            yb -= 1;
//        }
//        else if (ya == yb && xa > xb) {
//            xb += 1;
//            yb += 1;
//        }
//        else if (xa == xb && ya > yb) {
//            xb -= 1;
//            yb += 1;
//        }
//        else {
//            xb += 1;
//            yb -= 1;
//        }
//    }

//    public void pivoterMoins() {
//        if (ya == yb && xa < xb) {
//            xb -= 1;
//            yb += 1;
//        }
//        else if (ya == yb && xa > xb) {
//            xb += 1;
//            yb -= 1;
//        }
//        else if (xa == xb && ya > yb) {
//            xb += 1;
//            yb -= 1;
//        }
//        else {
//            xb -= 1;
//            yb += 1;
//        }
//    }

    public boolean occupe(int x, int y) {
        return this.xa == x && this.ya == y || this.xb == x && this.yb == y;
    }

    public boolean occupe(int xa, int ya, int xb, int yb) {
        return this.xa == xa && this.ya == ya && this.xb == xb && this.yb == yb;
    }
}

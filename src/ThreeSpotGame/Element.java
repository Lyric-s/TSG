package ThreeSpotGame;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Element {
    private final char id;
    private final int xa, ya, xb, yb;
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

    public boolean estVerticale() {
        return this.ya==this.yb && this.xb-this.xa == 1;
    }

    public boolean estHorizontale() {
        return this.xa==this.xb && this.yb-this.ya == 1;
    }

    public boolean occupe(int x, int y) {
        return this.xa == x && this.ya == y || this.xb == x && this.yb == y;
    }

    public boolean occupe(int xa, int ya, int xb, int yb) {
        return this.xa == xa && this.ya == ya && this.xb == xb && this.yb == yb;
    }
}

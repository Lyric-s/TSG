package threespotgame;


public class Piece {
    private final char id;
    private final int xa, ya, xb, yb;
    private boolean enMouvement;

    public Piece(char id, int xa, int ya, int xb, int yb) {
        assert((xb-xa)+(yb-ya)==1 || (xb-xa)+(ya-yb)==1 || (ya-yb)+(xa-xb)==1 || (yb-ya)+(xa-xb)==1);
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

    public boolean estEnMouvement() {
        return enMouvement;
    }

    public void setEnMouvement(boolean enMouvement) {
        this.enMouvement = enMouvement;
    }

    public boolean occupe(int x, int y) {
        return this.xa == x && this.ya == y || this.xb == x && this.yb == y;
    }

    public boolean occupe(int xa, int ya, int xb, int yb) {
        return this.xa == xa && this.ya == ya && this.xb == xb && this.yb == yb;
    }
}
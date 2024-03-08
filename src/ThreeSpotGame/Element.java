package ThreeSpotGame;

public class Element {
    private final char id;
    private int xa, ya, xb, yb;

    public Element(char id, int xa, int ya, int xb, int yb) {
        assert(xa != xb && ya != yb);
        this.id = id;
        this.xa = xa;
        this.ya = ya;
        this.xb = xb;
        this.yb = yb;
    }

    public char getId() {
        return id;
    }

    public void deplacer(int xa, int ya, int xb, int yb) {

    }

    public boolean occupe(int x, int y) {
        return this.xa == x && this.ya == y || this.xb == x && this.yb == y;
    }

    public boolean occupe(int xa, int ya, int xb, int yb) {
        return this.xa == xa && this.ya == ya && this.xb == xb && this.yb == yb;
    }
}

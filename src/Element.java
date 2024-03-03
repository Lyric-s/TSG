public class Element {
    private char id;
    private int dx,dy;
    private int x,y;

    public char getId() {
        return id;
    }
    public boolean occupe(int x, int y) {
        return this.x == x && this.y == y;
    }
}

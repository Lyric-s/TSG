public class Element {
    private char id;
    private int x, y;
    private int dx, dy;

    public Element(char id, int x, int y, int dx, int dy) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public char getId() {
        return id;
    }

    public boolean occupe(int x, int y) {
        return this.x == x && this.y == y;
    }

    public void bouge(int largeur, int hauteur) {
        int nx = x + dx, ny = y + dy;
        if (nx >= 0 && nx < largeur)
            x = nx;
        else {
            dx *= -1;
            x += dx;
        }
        if (ny >= 0 && ny < hauteur) {
            y = ny;
        }
        else {
            dy *= -1;
            y += dy;
        }
    }
}

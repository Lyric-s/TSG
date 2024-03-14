package threespotgame;


public class Piece {
    private final char id;
    private final int xa, ya, xb, yb;
    private boolean enMouvement;

    /**
     * Constructeur de la classe Piece
     * @param id, char Identifiant de la pièces de la pièce
     * @param xa, int coordonnée x de la première case de la pièce
     * @param ya, int coordonnée y de la première case de la pièce
     * @param xb, int coordonnée x de la seconde case de la pièce
     * @param yb, int coordonnée y de la seconde case de la pièce
     */
    public Piece(char id, int xa, int ya, int xb, int yb) {
        assert((xb-xa)+(yb-ya)==1 || (xb-xa)+(ya-yb)==1 || (ya-yb)+(xa-xb)==1 || (yb-ya)+(xa-xb)==1);
        this.id = id;
        this.xa = xa;
        this.ya = ya;
        this.xb = xb;
        this.yb = yb;
        this.enMouvement = false;
    }

    /**
     * Getter de l'Id de la pièce
     * @return int id
     */
    public char getId() {
        return id;
    }

    /**
     * Confirme si une pièce est en état de mouvement (i.e. est sur le point d'être déplacée)
     * @return boolean
     */
    public boolean estEnMouvement() {
        return enMouvement;
    }

    /**
     * Met une pièce en état de mouvement donné, vrai (true) ou faux (false) (i.e. est sur le point d'être déplacée ou non)
     * @param enMouvement, boolean true ou false
     */
    public void setEnMouvement(boolean enMouvement) {
        this.enMouvement = enMouvement;
    }

    /**
     * Confirme si la pièce occupe la coordonnée entrée en paramètres
     * @param x, int
     * @param y, int
     * @return boolean
     */
    public boolean occupe(int x, int y) {
        return this.xa == x && this.ya == y || this.xb == x && this.yb == y;
    }

    /**
     * Confirme si la pièce occupe les deux coordonnées entrées en paramètres
     * @param xa, int
     * @param ya, int
     * @param xb, int
     * @param yb, int
     * @return boolean
     */
    public boolean occupe(int xa, int ya, int xb, int yb) {
        return this.xa == xa && this.ya == ya && this.xb == xb && this.yb == yb;
    }
}
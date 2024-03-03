import java.util.LinkedList;

public class Grille {
    private final int largeur, hauteur;
    private final LinkedList<Element> grille;

    public Grille(int largeur, int hauteur) {
        assert(hauteur > 0 && largeur > 0);
        this.largeur = largeur;
        this.hauteur = hauteur;
        grille = new LinkedList<>();
    }

    private Element occupant(int x, int y) {
        for (Element e: grille) {
            if (e.occupe(x, y))
                return e;
        }
        return null;
    }
}

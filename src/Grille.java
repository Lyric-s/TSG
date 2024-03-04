import java.util.LinkedList;

public class Grille {
    private final int largeur, hauteur;
    private final LinkedList<Element> pieces;

    public Grille(int largeur, int hauteur) {
        assert(hauteur > 0 && largeur > 0);
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.pieces = new LinkedList<>();
    }

    public void ajouter (Element e) {
        pieces.add(e);
    }

    public void simuler() {
        for(Element e : pieces)
            e.bouge(largeur, hauteur);
    }

    public String toString() {
        String s = "";
        for (int l = 0; l < hauteur; ++l) {
            for (int c = 0; c < largeur; ++c) {
                Element e = occupant(l, c);
                s += e == null ? " - " : " " + e.getId() + " ";
            }
            s += "\n";
        }
        return s;
    }

    private Element occupant(int l, int c) {
        for (Element e: pieces) {
            if (e.occupe(l, c))
                return e;
        }
        return null;
    }
}

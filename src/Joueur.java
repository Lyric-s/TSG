public class Joueur {
    private int nbPointJoueur;
    private final int idJoueur;
    private static int nbJoueurs;

    public Joueur() {
        nbPointJoueur = 0;
        idJoueur = ++nbJoueurs;
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    public int getNbPointJoueur() {
        return nbPointJoueur;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public void incNbPointsJoueur() {
        ++nbPointJoueur;
    }
}

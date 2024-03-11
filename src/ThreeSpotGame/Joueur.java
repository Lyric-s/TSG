package ThreeSpotGame;

public class Joueur {
    private int nbPointJoueur;
    private char idPiece;
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

    public void setIdPiece(char idPiece) {
        this.idPiece = idPiece;
    }

    public char getIdPiece() {
        return idPiece;
    }

    public void incNbPointsJoueur() {
        ++nbPointJoueur;
    }

    public void setNbPointJoueur(int nbPointJoueur) {
        this.nbPointJoueur = nbPointJoueur;
    }
}

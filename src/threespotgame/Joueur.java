package threespotgame;

public class Joueur {
    private int nbPointJoueur;
    private char idPiece;
    private final int idJoueur;
    private static int nbJoueurs;

    /**
     * Constructeur de la classe Joueur
     */
    public Joueur() {
        nbPointJoueur = 0;
        idJoueur = ++nbJoueurs;
    }

    /**
     * Getter de l'Id de joueur
     * @return int idJoueur
     */
    public int getIdJoueur() {
        return idJoueur;
    }

    /**
     * Getter du nombre de point du joueur
     * @return int nbPointJoueur
     */
    public int getNbPointJoueur() {
        return nbPointJoueur;
    }

    /**
     * Getter du nombre de joueur
     * @return int nbJoueur
     */
    public static int getNbJoueurs() {
        return nbJoueurs;
    }

    /**
     *
     * @param idPiece, int; Id de la pièce
     */
    public void setIdPiece(char idPiece) {
        this.idPiece = idPiece;
    }

    /**
     * Getter de l'Id de la pièce possédée actuellement par le joueur
     * @return int idPiece
     */
    public char getIdPiece() {
        return idPiece;
    }

    /**
     * Incrémente de 1 le nombre de de point du joueur lors de l'appel de cette fonction
     */
    public void incNbPointsJoueur() {
        ++nbPointJoueur;
    }

    /**
     * Instancie le nombre de point du joueur
     * @param nbPointJoueur, nombre de point à attribuer
     */
    public void setNbPointJoueur(int nbPointJoueur) {
        this.nbPointJoueur = nbPointJoueur;
    }
}
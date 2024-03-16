package threespotgame;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JoueurTest {

    @Test
    void getIdJoueurEstPositif() {
        Joueur joueur1 = new Joueur();
        assertTrue(joueur1.getIdJoueur() > 0);
    }

    @Test
    void getNbPointJoueurBienInitialise() {
        Joueur joueur = new Joueur();
        assertEquals(0, joueur.getNbPointJoueur());
    }

    @Test
    void getNbJoueurs() {
        assertEquals(0, Joueur.getNbJoueurs());
        Joueur joueur1 = new Joueur();
        assertEquals(1, Joueur.getNbJoueurs());
        Joueur joueur2 = new Joueur();
        Joueur joueur3 = new Joueur();
        Joueur joueur4 = new Joueur();
        assertEquals(4, Joueur.getNbJoueurs());
    }

    @Test
    void idPieceBienAttribue() {
        Joueur joueur = new Joueur();
        joueur.setIdPiece('T');
        assertEquals('T', joueur.getIdPiece());
        joueur.setIdPiece('R');
        assertEquals('R', joueur.getIdPiece());
        assertNotEquals('T', joueur.getIdPiece());
    }

    @Test
    void getIdPieceNonInitialise() {
        Joueur joueur = new Joueur();
        assertNotEquals(null, Optional.of(joueur.getIdPiece()));
        idPieceBienAttribue();

    }

    @Test
    void incNbPointsJoueur() {
        Joueur joueur = new Joueur();
        joueur.incNbPointsJoueur();
        assertEquals(1, joueur.getNbPointJoueur());
        joueur.incNbPointsJoueur();
        joueur.incNbPointsJoueur();
        joueur.incNbPointsJoueur();
        assertEquals(4, joueur.getNbPointJoueur());
        joueur.incNbPointsJoueur();
        joueur.incNbPointsJoueur();
        joueur.incNbPointsJoueur();
        joueur.incNbPointsJoueur();
        joueur.incNbPointsJoueur();
        joueur.incNbPointsJoueur();
        joueur.incNbPointsJoueur();
        joueur.incNbPointsJoueur();
        assertEquals(12, joueur.getNbPointJoueur());
    }

    @Test
    void setNbPointJoueur() {
        Joueur joueur = new Joueur();
        assertEquals(0, joueur.getNbPointJoueur());
        joueur.setNbPointJoueur(100);
        assertEquals(100, joueur.getNbPointJoueur());
    }
}
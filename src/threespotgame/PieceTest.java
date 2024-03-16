package threespotgame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    void getId() {
        Piece piece = new Piece('T', 1, 1, 1, 2);
        assertEquals('T', piece.getId());
    }

    @Test
    void estEnMouvement() {
        Piece piece = new Piece('T', 1, 1, 1, 2);
        assertFalse(piece.estEnMouvement());
        piece.setEnMouvement(true);
        assertTrue(piece.estEnMouvement());
    }

    @Test
    void setEnMouvement() {
        Piece piece = new Piece('T', 1, 1, 1, 2);
        assertFalse(piece.estEnMouvement());
        piece.setEnMouvement(true);
        assertTrue(piece.estEnMouvement());
        piece.setEnMouvement(false);
        assertFalse(piece.estEnMouvement());
    }

    @Test
    void testOccupeUneCase() {
        Piece piece = new Piece('T', 1, 1, 1, 2);
        assertTrue(piece.occupe(1, 1));
        assertFalse(piece.occupe(2, 1));
    }

    @Test
    void testOccupeDeuxCases() {
        Piece piece = new Piece('T', 1, 1, 1, 2);
        assertTrue(piece.occupe(1, 1, 1, 2));
        assertFalse(piece.occupe(2, 1, 1, 1));
        assertFalse(piece.occupe(1, 2, 1, 1));
    }
}
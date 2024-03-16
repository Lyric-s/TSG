package threespotgame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlateauTest {

    @Test
    void testToStringEstCarre() {
        Plateau plateau = new Plateau();
        assertFalse(plateau.toString().isEmpty());
        assertEquals(13 * 37 + 12, plateau.toString().length()); // nombre de caractères par ligne * nombre de caractères par colonnes + nombre de retour à la ligne.
    }
}
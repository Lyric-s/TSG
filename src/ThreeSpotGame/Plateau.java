package ThreeSpotGame;


import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Plateau {
    private final int largeur, hauteur;
    private final LinkedList<Element> elements;
    private final LinkedList<Spot> spots;
    private Joueur J1, J2;
    private int nbTour;

    private static class Spot {
        private static int nbSpots = 0;
        private final int idSpot;
        private final int x, y;

        public Spot(int x, int y) {
            this.x = x;
            this.y = y;
            this.idSpot = ++nbSpots;
        }

        public int getIdSpot() {
            return idSpot;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public Plateau() {
        this.largeur = 3;
        this.hauteur = 3;
        this.elements = new LinkedList<>();
        this.spots = new LinkedList<>();
        this.J1 = new Joueur();
        this.J2 = new Joueur();
        this.nbTour = 0;
    }

    private void ajouter (Element e) {
        elements.add(e);
    }
    private void ajouter (Spot s) {
        spots.add(s);
    }

    private Element occupant(int l, int c) {
        for (Element e: elements) {
            if (e.occupe(l, c))
                return e;
        }
        return null;
    }

    private void initPiecesPlateau() {
        ajouter(new Element('R',0,1,0,2));
        ajouter(new Element('W',1,1,1,2));
        ajouter(new Element('B',2,1,2,2));
        assertEquals('R', elements.getFirst().getId());
        assertEquals('W', elements.get(1).getId());
        assertEquals('B', elements.get(2).getId());
        ajouter(new Spot(0,2));
        ajouter(new Spot(1,2));
        ajouter(new Spot(2,2));
        assertEquals(1, spots.getFirst().getIdSpot());
        assertEquals(2, spots.get(1).getIdSpot());
        assertEquals(3, spots.get(2).getIdSpot());
    }

    private void initJoueursThreeSpotGame() {
        String couleurPiece;
        Scanner sc = new Scanner(System.in);
        System.out.print("Joueur " + J1.getIdJoueur() + " choisit la pièce 'Rouge' ou 'Bleue': ");
        while(true) {
            couleurPiece = sc.next();
            if ("Rouge".equals(couleurPiece)) {
                J1.setIdPiece('R');
                assertEquals('R', J1.getIdPiece());
                System.out.print("Piece rouge attribuée au Joueur " + J1.getIdJoueur() + " : 'R'");
                J2.setIdPiece('B');
                assertEquals('B', J2.getIdPiece());
                System.out.print("\nPiece bleue attribuée au Joueur " + J2.getIdJoueur() + " : 'B'\n\n");
                break;
            }
            else if ("Bleue".equals(couleurPiece)) {
                J1.setIdPiece('B');
                assertEquals('B', J1.getIdPiece());
                System.out.print("Piece bleue attribuée au Joueur " + J1.getIdJoueur() + " : 'B'");
                J2.setIdPiece('R');
                assertEquals('R', J2.getIdPiece());
                System.out.print("\nPiece rouge attribuée au Joueur " + J2.getIdJoueur() + " : 'R'\n\n");
                break;
            }
            else
                System.out.print("\nERREUR : Couleur non référencée\nRéessayez : ");
        }
    }

    private boolean rougeEstSurSpot(Spot s) {
        return elements.getFirst().occupe(s.getX(),s.getY());
    }

    private boolean blancEstSurSpot(Spot s) {
        return elements.get(1).occupe(s.getX(),s.getY());
    }

    private boolean bleuEstSurSpot(Spot s) {
        return elements.get(2).occupe(s.getX(),s.getY());
    }

    private void pointsPourRouge(Joueur j) {
        assertEquals(j.getIdPiece(), 'R');
        for(Spot s: spots) {
            if (rougeEstSurSpot(s))
                j.incNbPointsJoueur();
        }
    }

    private void pointsPourBleu(Joueur j) {
        assertEquals(j.getIdPiece(), 'B');
        for(Spot s: spots) {
            if (bleuEstSurSpot(s))
                j.incNbPointsJoueur();
        }
    }

    public void initThreeSpotGame() {
        elements.clear();
        initPiecesPlateau();
        initJoueursThreeSpotGame();
    }

    public void nouvTour() {
        if (J1.getIdPiece() == 'R') {
            pointsPourRouge(J1);
        }
        System.out.print("" + J1.getNbPointJoueur());
    }

    private void finDePartie() {
        //TODO
    }

    public String toString() {
        StringBuilder str = new StringBuilder("*  *  *  *  *  *  *  *  *  *  *  *  *");
        str.append("\n*           *           *           *\n");
        for (int l = 0; l < hauteur; ++l) {
            for (int c = 0; c < largeur; ++c) {
                Element e = occupant(l, c);
                if (c > 0 && c < largeur-1) {
                    str.append(e == null ? "           " : "     " + e.getId() + "     ");
                }
                else if (c == 0)
                    str.append(e == null ? "*           *" : "*     " + e.getId() + "     *");
                else if (c == largeur-1)
                    str.append(e == null ? "*     O     *" : "*     " + e.getId() + "     *");
            }
            str.append("\n*           *           *           *\n");
            str.append("*  *  *  *  *  *  *  *  *  *  *  *  *");
            if (l < hauteur-1)
                str.append("\n*           *           *           *\n");
        }
        String s;
        s = valueOf(str);
        return s;
    }
}
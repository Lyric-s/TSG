package ThreeSpotGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.*;

public class Plateau {
    private enum Langage {
        FRANCAIS, ENGLISH;
    }
    private Langage langue;
    private final int largeur, hauteur;
    private final LinkedList<Element> elements;
    private final LinkedList<Destination> destinations;
    private final LinkedList<Spot> spots;
    private final Joueur J1, J2;
    private int nbTour;

    private class Spot {
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

    private class Destination {
        private static int nbDestinations = 0;
        private final int xa, ya, xb, yb;
        private final char idDestination;

        public Destination(int xa, int ya, int xb, int yb) {
            ++nbDestinations;
            String s = valueOf(nbDestinations);
            this.xa = xa;
            this.ya = ya;
            this.xb = xb;
            this.yb = yb;
            this.idDestination = s.charAt(0);
        }

        public int getXa() {
            return xa;
        }

        public int getYa() {
            return ya;
        }

        public int getXb() {
            return xb;
        }

        public int getYb() {
            return yb;
        }

        public char getId() {
            return idDestination;
        }

        public boolean estVerticale() {
            return ya==yb && xb-xa == 1;
        }

        public boolean estHorizontale() {
            return xa==xb && yb-ya == 1;
        }

        public boolean occupeA(int xa, int ya) {
            return this.xa == xa && this.ya == ya;
        }

        public boolean occupeB(int xb, int yb) {
            return this.xb == xb && this.yb == yb;
        }

        public static void resetNbDestination() {
            Destination.nbDestinations = 0;
        }
    }
    public Plateau() {
        this.langue = Langage.FRANCAIS;
        this.largeur = 3;
        this.hauteur = 3;
        this.elements = new LinkedList<>();
        this.destinations = new LinkedList<>();
        this.spots = new LinkedList<>();
        this.J1 = new Joueur();
        this.J2 = new Joueur();
        this.nbTour = 0;
    }

    private void ajouter(Element e) {
        elements.add(e);
    }

    private void ajouter(Spot s) {
        spots.add(s);
    }

    private void viderDestinations() {
        destinations.clear();
        Destination.resetNbDestination();
    }

    private Destination occupante(int l, int c) {
        for (Destination d: destinations) {
            if (d.estVerticale() && d.occupeB(l, c))
                return d;
            else if (d.estHorizontale() && d.occupeA(l, c))
                return d;
        }
        return null;
    }

    private void destinationsOccupantes(ArrayList<Destination> listDest, int l, int c) {
        for (Destination d: destinations) {
            if (d.estVerticale() && d.occupeB(l, c) || d.estHorizontale() && d.occupeA(l, c))
                listDest.add(d);
        }
        if (!listDest.isEmpty())
            if (listDest.getFirst().estHorizontale()) {
                listDest.addFirst(listDest.getLast());
                listDest.removeLast();
            }
    }

    private Element occupant(int l, int c) {
        for (Element e: elements) {
            if (e.occupe(l, c))
                return e;
        }
        return null;
    }

    private void initPiecesPlateau() {
        elements.clear();
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
        System.out.println(this + "\n");
    }

    private void initJoueursThreeSpotGame() {
        String couleurPiece;
        Scanner scanCouleur = new Scanner(System.in);
        J1.setNbPointJoueur(0);
        J2.setNbPointJoueur(0);
        System.out.print("Joueur 1 choisit sa pièce (Rouge/Bleue) : ");
        while(true) {
            couleurPiece = scanCouleur.next();
            if ("Rouge".equals(couleurPiece)) {
                J1.setIdPiece('R');
                J2.setIdPiece('B');
                assertEquals('R', J1.getIdPiece());
                assertEquals('B', J2.getIdPiece());
                System.out.print("\n          | Joueur 1 : R |\n");
                System.out.print("          | Joueur 2 : B |\n\n");
                break;
            } else if ("Bleue".equals(couleurPiece)) {
                J1.setIdPiece('B');
                J2.setIdPiece('R');
                assertEquals('B', J1.getIdPiece());
                assertEquals('R', J2.getIdPiece());
                System.out.print("\n          | Joueur 1 : B |\n");
                System.out.print("          | Joueur 2 : R |\n\n");
                break;
            } else if ("(English)".equals(couleurPiece)) {
                this.langue = Langage.ENGLISH;
                System.out.print("\n\n*--- The language has been successfully changed to English! ---*\n\n");
                System.out.print("Player 1 chooses his piece (Red/Blue) : ");
                while (true) {
                    couleurPiece = scanCouleur.next();
                    if ("Red".equals(couleurPiece)) {
                        J1.setIdPiece('R');
                        J2.setIdPiece('B');
                        assertEquals('R', J1.getIdPiece());
                        assertEquals('B', J2.getIdPiece());
                        System.out.print("\n          | Player 1 : R |\n");
                        System.out.print("          | Player 2 : B |\n\n");
                        break;
                    } else if ("Blue".equals(couleurPiece)) {
                        J1.setIdPiece('B');
                        J2.setIdPiece('R');
                        assertEquals('B', J1.getIdPiece());
                        assertEquals('R', J2.getIdPiece());
                        System.out.print("\n          | Player 1 : B |\n");
                        System.out.print("          | Player 2 : R |\n\n");
                        break;
                    }
                    else {
                        System.out.print("\nERROR: Color unknown.\nTry again: ");
                    }
                }
                break;

            } else {
                System.out.print("\nERREUR : Couleur non référencée.\nRéessayez : ");
            }
        }
    }

    private boolean rougeEstSurSpot(Spot s) {
        return elements.getFirst().occupe(s.getX(),s.getY());
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

    private boolean sontNonOccupees(int xa, int ya, int xb, int yb) {
        for (Element e: elements) {
            if (e.occupe(xa, ya, xb, yb))
                return false;
        }
        return true;
    }

    private boolean estNonOccupee(int x, int y) {
        for (Element e: elements) {
            if (e.occupe(x, y) && !e.estEnMouvement())
                return false;
        }
        return true;
    }

    private boolean estPosNonOccupee(int xa, int ya, int xb, int yb) {
        for (Destination d: destinations) {
            if (d.estVerticale() && d.occupeB(xb, yb) && d.occupeA(xa, ya) || d.estHorizontale() && d.occupeA(xa, ya) && d.occupeB(xb, yb))
                return false;
        }
        return true;
    }

    private void recherchePosLibre() {
        for (int ya = 0; ya < hauteur; ++ya) {
            for (int xb = 0; xb < largeur; ++xb) {
                for (int yb = 0; yb < hauteur; ++yb) {
                    for (int xa = 0; xa < largeur; ++xa) {
                        if ((ya==yb && xb-xa == 1 || xa==xb && yb-ya == 1) && estNonOccupee(xa, ya) && estNonOccupee(xb, yb) && sontNonOccupees(xa, ya, xb, yb)) {
                            if (destinations.isEmpty())
                                destinations.add(new Destination(xa, ya, xb, yb));
                            else if (estPosNonOccupee(xa, ya, xb, yb)) {
                                destinations.add(new Destination(xa, ya, xb, yb));
                            }
                        }
                    }
                }
            }
        }
    }

    private int entreePosition() {
        Scanner scanPosition = new Scanner(System.in);
        int position;
        while (true) {
            position = scanPosition.nextInt();
            if (position > destinations.size() || position < 1) {
                if (this.langue.equals(Langage.ENGLISH))
                    System.out.print("\nERROR: unknown position.\nTry again: ");
                else if (this.langue.equals(Langage.FRANCAIS))
                    System.out.print("\nERREUR : position inexistante.\nRéessayez : ");
            }
            else
                break;
        }
        return --position;
    }

    private void deplacementPieceJoueur(int numJoueur, char idPiece) {
        int position;
        if (idPiece == 'R') {
            elements.getFirst().setEnMouvement(true);
            recherchePosLibre();
            assertFalse(destinations.isEmpty());
            System.out.println(this);
            if (this.langue.equals(Langage.ENGLISH))
                System.out.print("\n*--------- Turn of Player " + numJoueur + " --------*\n\n(R) Choice of destination [1 ; " + destinations.size() + "] : ");
            else if (this.langue.equals(Langage.FRANCAIS))
                System.out.print("\n*--------- Tour du Joueur " + numJoueur + " --------*\n\n(R) Choix de destination [1 ; " + destinations.size() + "] : ");
            position = entreePosition();
            elements.removeFirst();
            elements.addFirst(new Element('R', destinations.get(position).getXa(), destinations.get(position).getYa(), destinations.get(position).getXb(), destinations.get(position).getYb()));
            viderDestinations();
        }
        else if (idPiece == 'W') {
            elements.get(1).setEnMouvement(true);
            recherchePosLibre();
            assertFalse(destinations.isEmpty());
            System.out.println(this);
            if (this.langue.equals(Langage.ENGLISH))
                System.out.print("\n*--------- Turn of Player " + numJoueur + " --------*\n\n(W) Choice of destination [1 ; " + destinations.size() + "] : ");
            else if (this.langue.equals(Langage.FRANCAIS))
                System.out.print("\n*--------- Tour du Joueur " + numJoueur + " --------*\n\n(W) Choix de destination [1 ; " + destinations.size() + "] : ");
            position = entreePosition();
            elements.remove(1);
            elements.add(1, new Element('W', destinations.get(position).getXa(), destinations.get(position).getYa(), destinations.get(position).getXb(), destinations.get(position).getYb()));
            viderDestinations();
        }
        else if (idPiece == 'B') {
            elements.get(2).setEnMouvement(true);
            recherchePosLibre();
            assertFalse(destinations.isEmpty());
            System.out.println(this);
            if (this.langue.equals(Langage.ENGLISH))
                System.out.print("\n*--------- Turn of Player " + numJoueur + " --------*\n\n(B) Choice of destination [1 ; " + destinations.size() + "] : ");
            else if (this.langue.equals(Langage.FRANCAIS))
                System.out.print("\n*--------- Tour du Joueur " + numJoueur + " --------*\n\n(B) Choix de destination [1 ; " + destinations.size() + "] : ");
            position = entreePosition();
            elements.remove(2);
            elements.add(2, new Element('B', destinations.get(position).getXa(), destinations.get(position).getYa(), destinations.get(position).getXb(), destinations.get(position).getYb()));
            viderDestinations();
        }
    }

    private void initThreeSpotGame() {
        elements.clear();
        initPiecesPlateau();
        initJoueursThreeSpotGame();
    }

    private void nouvTour() {
        ++nbTour;
        if (nbTour % 2 == 1) {
            if (J1.getIdPiece() == 'R') {
                deplacementPieceJoueur(1,'R');
                deplacementPieceJoueur(1,'W');
                pointsPourRouge(J1);
            }
            else if (J1.getIdPiece() == 'B') {
                deplacementPieceJoueur(1,'B');
                deplacementPieceJoueur(1,'W');
                pointsPourBleu(J1);
            }
        }
        else {
            if (J2.getIdPiece() == 'R') {
                deplacementPieceJoueur(2,'R');
                deplacementPieceJoueur(2,'W');
                pointsPourRouge(J2);
            }
            else if (J2.getIdPiece() == 'B') {
                deplacementPieceJoueur(2,'B');
                deplacementPieceJoueur(2,'W');
                pointsPourBleu(J2);
            }
        }
        System.out.println(this);
        System.out.println("\n*----------- Nouveau tour ----------*\n");
        if (this.langue.equals(Langage.ENGLISH)) {
            System.out.print("\nPlayer 1" + (J1.getIdPiece() == 'R' ? " (Red piece) : " : " (Blue piece) : ") + J1.getNbPointJoueur() + "points");
            System.out.print("\nPlayer 2" + (J2.getIdPiece() == 'R' ? " (Red piece) : " : " (Blue piece) : ") + J2.getNbPointJoueur() + "points\n");
        }
        else if (this.langue.equals(Langage.FRANCAIS)) {
            System.out.print("\nJoueur 1" + (J1.getIdPiece() == 'R' ? " (piece Rouge) : " : " (piece Bleue) : ") + J1.getNbPointJoueur() + "points");
            System.out.print("\nJoueur 2" + (J2.getIdPiece() == 'R' ? " (piece Rouge) : " : " (piece Bleue) : ") + J2.getNbPointJoueur() + "points\n");
        }
    }

    private boolean finDePartie() {
        return(J1.getNbPointJoueur() >= 12 || J2.getNbPointJoueur() >= 12);
    }

    public void jouerThreeSpotGame() {
        Scanner sc = new Scanner(System.in);
        String ouiNon;
        while (true) {
            initThreeSpotGame();
            while (!finDePartie()) {
                nouvTour();
            }
            if (J2.getNbPointJoueur() >= 12 && J1.getNbPointJoueur() >= 6) {
                if (this.langue.equals(Langage.ENGLISH))
                    System.out.println("*------- Victory of Player 2 -------*\n");
                else if (this.langue.equals(Langage.FRANCAIS))
                    System.out.println("*------- Victoire du Joueur 2 ------*\n");
            } else if (J1.getNbPointJoueur() >= 12 && J2.getNbPointJoueur() >= 6) {
                if (this.langue.equals(Langage.ENGLISH))
                    System.out.println("*------- Victory of Player 1 -------*\n");
                else if (this.langue.equals(Langage.FRANCAIS))
                    System.out.println("*------- Victoire du Joueur 1 ------*\n");
            } else if (J1.getNbPointJoueur() >= 12 && J2.getNbPointJoueur() < 6) {
                if (this.langue.equals(Langage.ENGLISH))
                    System.out.println("*------- Victory of Player 2 -------*\n");
                else if (this.langue.equals(Langage.FRANCAIS))
                    System.out.println("*------- Victoire du Joueur 2 ------*\n");
            } else if (J2.getNbPointJoueur() >= 12 && J1.getNbPointJoueur() < 6) {
                if (this.langue.equals(Langage.ENGLISH))
                    System.out.println("*------- Victory of Player 1 -------*\n");
                else if (this.langue.equals(Langage.FRANCAIS))
                    System.out.println("*------- Victoire du Joueur 1 ------*\n");
            }
            while (true) {
                if (this.langue.equals(Langage.ENGLISH)) {
                    System.out.println("Play again ? (yes/no) : ");
                    ouiNon = sc.next();
                    if (ouiNon.equals("no")) {
                        System.out.print("\nThanks for playing! ❤\uFE0F");
                        break;
                    } else if (ouiNon.equals("yes")) {
                        break;
                    }
                }
                else if (this.langue.equals(Langage.FRANCAIS)) {
                    System.out.print("Faire une nouvelle partie ? (oui/non) : ");
                    ouiNon = sc.next();
                    if (ouiNon.equals("non")) {
                        System.out.print("\nMerci d'avoir joué ! ❤\uFE0F");
                        break;
                    } else if (ouiNon.equals("oui")) {
                        break;
                    }
                }
            }
            if ((ouiNon.equals("non") && this.langue.equals(Langage.FRANCAIS) || (ouiNon.equals("no") && this.langue.equals(Langage.ENGLISH)))) {
                break;
            }
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder("*  *  *  *  *  *  *  *  *  *  *  *  *");
        str.append("\n*           *           *           *\n");
        if (destinations.isEmpty()) {
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
        }
        else {
            ArrayList<Destination> listDestTempo = new ArrayList<>();
            for (int l = 0; l < hauteur; ++l) {
                for (int c = 0; c < largeur; ++c) {
                    Element e = occupant(l, c);
                    Destination d = occupante(l, c);
                    listDestTempo.clear();
                    destinationsOccupantes(listDestTempo, l, c);
                    if (c > 0 && c < largeur-1) {
                        if (d != null) {
                            str.append(e == null ? (listDestTempo.size() > 1 ? "   " + listDestTempo.getFirst().getId() + " - " + listDestTempo.getLast().getId() + "   " : "     " + d.getId() + "     ") : (e.estEnMouvement() ? (listDestTempo.size() > 1 ? "   " + listDestTempo.getFirst().getId() + " - " + listDestTempo.getLast().getId() + "   " : "     " + d.getId() + "     ") : "     " + e.getId() + "    "));
                        }
                        else
                            str.append(e == null ? (listDestTempo.size() > 1 ? "   " + listDestTempo.getFirst().getId() + " - " + listDestTempo.getLast().getId() + "   " : "           ") : "     " + (e.estEnMouvement() ? " " : e.getId()) + "     ");
                    }
                    else if (c == 0) {
                        if (d != null) {
                            str.append(e == null ? (listDestTempo.size() > 1 ? "*   " + listDestTempo.getFirst().getId() + " - " + listDestTempo.getLast().getId() + "   *" : "*     " + d.getId() + "     *") : (e.estEnMouvement() ? (listDestTempo.size() > 1 ? "*   " + listDestTempo.getFirst().getId() + " - " + listDestTempo.getLast().getId() + "   *" : "*     " + d.getId() + "     *") : "*     " + e.getId() + "    *"));
                        }
                        else
                            str.append(e == null ? (listDestTempo.size() > 1 ? "*   " + listDestTempo.getFirst().getId() + " - " + listDestTempo.getLast().getId() + "   *" : "*           *") : "*     " + (e.estEnMouvement() ? " " : e.getId()) + "     *");
                    }
                    else if (c == largeur-1) {
                        if (d != null) {
                            str.append(e == null ? (listDestTempo.size() > 1 ? "*   " + listDestTempo.getFirst().getId() + " - " + listDestTempo.getLast().getId() + "   *" : "*     " + d.getId() + "     *") : (e.estEnMouvement() ? (listDestTempo.size() > 1 ? "*   " + listDestTempo.getFirst().getId() + " - " + listDestTempo.getLast().getId() + "   *" : "*     " + d.getId() + "     *") : "*     " + e.getId() + "    *"));
                        }
                        else
                            str.append(e == null ? (listDestTempo.size() > 1 ? "*   " + listDestTempo.getFirst().getId() + " - " + listDestTempo.getLast().getId() + "   *" : "*     O     *"): "*     " + (e.estEnMouvement() ? "O" : e.getId()) + "     *");
                    }
                }
                str.append("\n*           *           *           *\n");
                str.append("*  *  *  *  *  *  *  *  *  *  *  *  *");
                if (l < hauteur-1)
                    str.append("\n*           *           *           *\n");
            }
        }
        String s;
        s = valueOf(str);
        return s;
    }
}
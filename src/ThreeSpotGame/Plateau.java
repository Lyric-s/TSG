package ThreeSpotGame;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.String.valueOf;
import static org.junit.jupiter.api.Assertions.*;

public class Plateau {
    private final int largeur, hauteur;
    private HashMap<Integer, ArrayList<Integer>> casesLibres;
    private final LinkedList<Element> elements;
    private final LinkedList<Destination> destinations;
    private final LinkedList<Spot> spots;
    private Joueur J1, J2;
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
        private int xa, ya, xb, yb;
        private char idDestination;

        public Destination(int xa, int ya, int xb, int yb) {
            ++nbDestinations;
            String s = String.valueOf(nbDestinations);
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
            return this.ya == this.yb;
        }

        public boolean estHorizontale() {
            return this.xa == this.xb;
        }

        public boolean occupeA(int xa, int ya) {
            return this.xa == xa || this.ya == ya;
        }

        public boolean occupeB(int xb, int yb) {
            return this.xb == xb || this.yb == yb;
        }

    }
    public Plateau() {
        this.largeur = 3;
        this.hauteur = 3;
        this.casesLibres = new HashMap<>();
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

    private void ajouter(Destination d) {
        destinations.add(d);
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
        System.out.println(toString() + "\n");
    }

    private void initJoueursThreeSpotGame() {
        String couleurPiece;
        Scanner sc = new Scanner(System.in);
        System.out.print("Joueur " + J1.getIdJoueur() + " choisit la pièce 'Rouge' ou 'Bleue': ");
        while(true) {
            couleurPiece = sc.next();
            if ("Rouge".equals(couleurPiece)) {
                J1.setIdPiece('R');
                J2.setIdPiece('B');
                assertEquals('R', J1.getIdPiece());
                assertEquals('B', J2.getIdPiece());
                System.out.print("Joueur 1 : 'R'");
                System.out.print("\nJoueur 2 : 'B'\n\n");
                break;
            }
            else if ("Bleue".equals(couleurPiece)) {
                J1.setIdPiece('B');
                J2.setIdPiece('R');
                assertEquals('B', J1.getIdPiece());
                assertEquals('R', J2.getIdPiece());
                System.out.print("Joueur 1 : 'B'");
                System.out.print("\nJoueur 2 : 'R'\n\n");
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

    private boolean estDansTableau(Element e) {
        return (e.getXa() >= 0 && e.getYa() >= 0 && e.getXb() < hauteur && e.getYb() < hauteur);
    }

    private boolean estNonOccupee(int xa, int ya, int xb, int yb) {
        for (Element e: elements) {
            if (e.occupe(xa, ya, xb, yb))
                return false;
        }
        return true;
    }

    private boolean estNonOccupee(int x, int y) {
        for (Element e: elements) {
            if (e.occupe(x, y))
                return false;
        }
        return true;
    }

    private boolean estPosNonOccupee(int xa, int ya, int xb, int yb) {
        for (Destination d: destinations) {
            if (d.estVerticale() && d.occupeA(xa, ya) || d.estHorizontale() && d.occupeB(xb, yb))
                return false;
        }
        return true;
    }

    private void recherchePosLibre() {
        casesLibres.clear();
        int n=0;
        for (int xa = 0; xa < hauteur; ++xa) {
            for (int ya = 0; ya < largeur; ++ya) {
                for (int xb = 0; xb < hauteur; ++xb) {
                    for (int yb = 0; yb < largeur; ++yb) {
                        if ((((xb-xa)+(yb-ya))==1 || ((xb-xa)+(ya-yb))==1 || ((ya-yb)+(xa-xb))==1 || ((yb-ya)+(xa-xb))==1) && estNonOccupee(xa, ya) && estNonOccupee(xb, yb)) {
                            if (destinations.isEmpty())
                                destinations.add(new Destination(xa, ya, xb, yb));
                            else if (estPosNonOccupee(xa, ya, xb, yb) && estPosNonOccupee(xb, yb, xa, ya)) {
                                destinations.add(new Destination(xa, ya, xb, yb));
                            }
                        }
                    }
                }
            }
        }
    }

    public void initThreeSpotGame() {
        elements.clear();
        initPiecesPlateau();
        initJoueursThreeSpotGame();
    }

    private boolean positionExiste(int p) {
        return p <= destinations.size() && p > 0;
    }

    public void nouvTour() {
        ++nbTour;
        Scanner sc = new Scanner(System.in);
        if (nbTour % 2 == 1) {
            if (J1.getIdPiece() == 'R') {
                elements.getFirst().setEnMouvement(true);
                recherchePosLibre();
                assertFalse(destinations.isEmpty());
                System.out.println(toString());
                System.out.print("\nPuisse le Joueur 1 choisir une destination entre 1 et " + destinations.size() + " : ");
                int n;
                while (true) {
                    n = sc.nextInt();
                    if (n > destinations.size() || n < 1) {
                        System.out.print("\nERREUR : position inexistante\nRéeessayez : ");
                    }
                    else
                        break;
                }
                elements.removeFirst();
                elements.addFirst(new Element('R', destinations.get(n-1).getXa(), destinations.get(n-1).getYa(), destinations.get(n-1).getXb(), destinations.get(n-1).getYb()));
                destinations.clear();
                pointsPourRouge(J1);
                pointsPourBleu(J2);
            }
            else if (J1.getIdPiece() == 'B') {
                elements.get(2).setEnMouvement(true);
                System.out.println(toString());
                System.out.println("\nPuisse le Joueur 1 choisir une destination entre 1 et " + destinations.size() + " : ");
                int n;
                while(true) {
                    n = sc.nextInt();
                    if (n > destinations.size() || n < 1) {
                        System.out.print("\nERREUR : position inexistante\nRéeessayez : ");
                    }
                    else
                        break;
                }
                elements.get(2).setEnMouvement(false);
                pointsPourBleu(J1);
            }
        }
        else {
            if (J2.getIdPiece() == 'R') {
                elements.getFirst().setEnMouvement(true);
                System.out.println(toString());
                System.out.println("\nPuisse le Joueur 2 choisir une destination entre 1 et " + destinations.size() + " : ");
                int n;
                while (true) {
                    n = sc.nextInt();
                    if (n > destinations.size() || n < 1) {
                        System.out.print("\nERREUR : position inexistante\nRéeessayez : ");
                    }
                    else
                        break;
                }
                elements.getFirst().setEnMouvement(false);
                pointsPourRouge(J2);
            }
            else if (J2.getIdPiece() == 'B') {
                elements.get(2).setEnMouvement(true);
                System.out.println(toString());
                System.out.println("\nPuisse le Joueur 2 choisir une destination entre 1 et " + destinations.size() + " : ");
                int n;
                while (true) {
                    n = sc.nextInt();
                    if (n > destinations.size() || n < 1) {
                        System.out.print("\nERREUR : position inexistante\nRéeessayez : ");
                    }
                    else
                        break;
                }
                elements.get(2).setEnMouvement(false);
                pointsPourBleu(J2);
            }
        }
        System.out.println(toString());
        System.out.print("\nCompteur points Joueur 1" + (J1.getIdPiece() == 'R' ? " (piece Rouge) : " : " (piece Bleue) : ") + J1.getNbPointJoueur());
        System.out.print("\nCompteur points Joueur 2" + (J2.getIdPiece() == 'R' ? " (piece Rouge) : " : " (piece Bleue) : ") + J2.getNbPointJoueur() + "\n");
        System.out.println("\nNombre de destinations disponibles : " + destinations.size());
    }

    private void jouer() {
        nouvTour();
    }

    public boolean finDePartie() {
        return(J1.getNbPointJoueur() >= 12 || J2.getNbPointJoueur() >= 12);
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
            for (int l = 0; l < hauteur; ++l) {
                for (int c = 0; c < largeur; ++c) {
                    Element e = occupant(l, c);
                    Destination d = occupante(l, c);
                    if (c > 0 && c < largeur-1) {
                        if (d != null) {
                            str.append(e == null ? "     " + d.getId() + "     " : "     " + (e.estEnMouvement() ? " " : e.getId()) + "     ");
                        }
                        else
                            str.append(e == null ? "           " : "     " + (e.estEnMouvement() ? " " : e.getId()) + "     ");
                    }
                    else if (c == 0) {
                        if (d != null) {
                            str.append(e == null ? "*     " + d.getId() + "     *" : "*     " + (e.estEnMouvement() ? " " : e.getId()) + "     *");
                        }
                        else
                            str.append(e == null ? "*           *" : "*     " + (e.estEnMouvement() ? " " : e.getId()) + "     *");
                    }
                    else if (c == largeur-1) {
                        if (d != null) {
                            str.append(e == null ? "*     " + d.getId() + "     *" : "*     " + (e.estEnMouvement() ? " " : e.getId()) + "     *");
                        }
                        else
                            str.append(e == null ? "*     O     *" : "*     " + (e.estEnMouvement() ? "O" : e.getId()) + "     *");
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
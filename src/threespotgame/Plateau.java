package threespotgame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.String.valueOf;

public class Plateau {
    private enum Langages {
        FRANCAIS, ENGLISH;
    }
    private Langages langue;
    private final int largeur, hauteur;
    private final LinkedList<Piece> pieces;
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

        public static void resetNbSpots() {
            Spot.nbSpots = 0;
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

        public static void resetNbDestinations() {
            Destination.nbDestinations = 0;
        }
    }
    public Plateau() {
        this.langue = Langages.FRANCAIS;
        this.largeur = 3;
        this.hauteur = 3;
        this.pieces = new LinkedList<>();
        this.destinations = new LinkedList<>();
        this.spots = new LinkedList<>();
        this.J1 = new Joueur();
        this.J2 = new Joueur();
        this.nbTour = 0;
    }

    private void ajouter(Piece e) {
        pieces.add(e);
    }

    private void ajouter(Spot s) {
        spots.add(s);
    }

    private void viderSpots() {
        spots.clear();
        Spot.resetNbSpots();
    }

    private void viderDestinations() {
        destinations.clear();
        Destination.resetNbDestinations();
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
            if (listDest.get(0).estHorizontale()) {
                listDest.addFirst(listDest.get(listDest.size()-1));
                listDest.remove(listDest.size()-1);
            }
    }

    private Piece occupant(int l, int c) {
        for (Piece e: pieces) {
            if (e.occupe(l, c))
                return e;
        }
        return null;
    }

    private void initPiecesPlateau() {
        pieces.clear();
        viderSpots();
        ajouter(new Piece('R',0,1,0,2));
        ajouter(new Piece('W',1,1,1,2));
        ajouter(new Piece('B',2,1,2,2));
        assert('R' == pieces.get(0).getId());
        assert('W' == pieces.get(1).getId());
        assert('B' == pieces.get(2).getId());
        ajouter(new Spot(0,2));
        ajouter(new Spot(1,2));
        ajouter(new Spot(2,2));
        assert(1 == spots.get(0).getIdSpot());
        assert(2 == spots.get(1).getIdSpot());
        assert(3 == spots.get(2).getIdSpot());
        System.out.println(this + "\n");
    }

    private void initJoueursThreeSpotGame() {
        String couleurPiece;
        Scanner scanCouleur = new Scanner(System.in);
        J1.setNbPointJoueur(0);
        J2.setNbPointJoueur(0);
        this.nbTour = 0;
        if (this.langue.equals(Langages.ENGLISH))
            System.out.print("Player 1 must choose his colored piece (Red/Blue): ");
        else if (this.langue.equals(Langages.FRANCAIS))
            System.out.print("Joueur 1 doit choisir sa pièce de couleur (Rouge/Bleue) : ");
        while(true) {
            couleurPiece = scanCouleur.next();
            if (this.langue.equals(Langages.FRANCAIS)) {
                if ("Rouge".equals(couleurPiece)) {
                    J1.setIdPiece('R');
                    J2.setIdPiece('B');
                    assert('R' == J1.getIdPiece());
                    assert('B' == J2.getIdPiece());
                    System.out.print("\n          | Joueur 1 : R |\n");
                    System.out.print("          | Joueur 2 : B |\n\n");
                    break;
                } else if ("Bleue".equals(couleurPiece)) {
                    J1.setIdPiece('B');
                    J2.setIdPiece('R');
                    assert('B' == J1.getIdPiece());
                    assert('R' == J2.getIdPiece());
                    System.out.print("\n          | Joueur 1 : B |\n");
                    System.out.print("          | Joueur 2 : R |\n\n");
                    break;
                } else if ("(English)".equals(couleurPiece)) {
                    this.langue = Langages.ENGLISH;
                    assert(this.langue.equals(Langages.ENGLISH));
                    System.out.print("\n\n*--- The language of the game has been successfully set to English! ---*\n\n");
                    System.out.print("Player 1 must choose his colored piece (Red/Blue): ");
                    while (true) {
                        couleurPiece = scanCouleur.next();
                        if ("Red".equals(couleurPiece)) {
                            J1.setIdPiece('R');
                            J2.setIdPiece('B');
                            assert('R' == J1.getIdPiece());
                            assert('B' == J2.getIdPiece());
                            System.out.print("\n           | Player 1: R |\n");
                            System.out.print("           | Player 2: B |\n\n");
                            break;
                        } else if ("Blue".equals(couleurPiece)) {
                            J1.setIdPiece('B');
                            J2.setIdPiece('R');
                            assert('B' == J1.getIdPiece());
                            assert('R' == J2.getIdPiece());
                            System.out.print("\n           | Player 1: B |\n");
                            System.out.print("           | Player 2: R |\n\n");
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
            else if (this.langue.equals(Langages.ENGLISH)) {
                while (true) {
                    if ("Red".equals(couleurPiece)) {
                        J1.setIdPiece('R');
                        J2.setIdPiece('B');
                        assert('R' == J1.getIdPiece());
                        assert('B' == J2.getIdPiece());
                        System.out.print("\n           | Player 1: R |\n");
                        System.out.print("           | Player 2: B |\n\n");
                        break;
                    } else if ("Blue".equals(couleurPiece)) {
                        J1.setIdPiece('B');
                        J2.setIdPiece('R');
                        assert('B' == J1.getIdPiece());
                        assert('R' == J2.getIdPiece());
                        System.out.print("\n           | Player 1: B |\n");
                        System.out.print("           | Player 2: R |\n\n");
                        break;
                    }
                    else {
                        System.out.print("\nERROR: Color unknown.\nTry again: ");
                    }
                    couleurPiece = scanCouleur.next();
                }
                break;
            }
        }
    }

    private boolean rougeEstSurSpot(Spot s) {
        return pieces.get(0).occupe(s.getX(),s.getY());
    }

    private boolean bleuEstSurSpot(Spot s) {
        return pieces.get(2).occupe(s.getX(),s.getY());
    }

    private void pointsPourRouge(Joueur j) {
        assert(j.getIdPiece() == 'R');
        for(Spot s: spots) {
            if (rougeEstSurSpot(s))
                j.incNbPointsJoueur();
        }
    }

    private void pointsPourBleu(Joueur j) {
        assert(j.getIdPiece() == 'B');
        for(Spot s: spots) {
            if (bleuEstSurSpot(s))
                j.incNbPointsJoueur();
        }
    }

    private boolean sontNonOccupees(int xa, int ya, int xb, int yb) {
        for (Piece e: pieces) {
            if (e.occupe(xa, ya, xb, yb))
                return false;
        }
        return true;
    }

    private boolean estNonOccupee(int x, int y) {
        for (Piece e: pieces) {
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
        String temp;
        int position = 0;
        while (true) {
            temp = scanPosition.next();
            if (temp.charAt(0) == '1' || temp.charAt(0) == '2' || temp.charAt(0) == '3' || temp.charAt(0) == '4') {
                position = temp.charAt(0) - '0';
            }
            if (position > destinations.size() || position < 1) {
                if (this.langue.equals(Langages.ENGLISH))
                    System.out.print("\nERROR: unknown position.\nTry again: ");
                else if (this.langue.equals(Langages.FRANCAIS))
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
            pieces.get(0).setEnMouvement(true);
            recherchePosLibre();
            assert(!destinations.isEmpty());
            System.out.println(this);
            if (this.langue.equals(Langages.ENGLISH))
                System.out.print("\n*------ Turn of Player " + numJoueur + " (1/2) -----*\n\n(R) Destination choice [1 ; " + destinations.size() + "]: ");
            else if (this.langue.equals(Langages.FRANCAIS))
                System.out.print("\n*------ Tour du Joueur " + numJoueur + " (1/2) -----*\n\n(R) Choix de destination [1 ; " + destinations.size() + "] : ");
            position = entreePosition();
            pieces.removeFirst();
            pieces.addFirst(new Piece('R', destinations.get(position).getXa(), destinations.get(position).getYa(), destinations.get(position).getXb(), destinations.get(position).getYb()));
            viderDestinations();
        }
        else if (idPiece == 'W') {
            pieces.get(1).setEnMouvement(true);
            recherchePosLibre();
            assert(!destinations.isEmpty());
            System.out.println(this);
            if (this.langue.equals(Langages.ENGLISH))
                System.out.print("\n*------ Turn of Player " + numJoueur + " (2/2) -----*\n\n(W) Destination choice [1 ; " + destinations.size() + "]: ");
            else if (this.langue.equals(Langages.FRANCAIS))
                System.out.print("\n*------ Tour du Joueur " + numJoueur + " (2/2) -----*\n\n(W) Choix de destination [1 ; " + destinations.size() + "] : ");
            position = entreePosition();
            pieces.remove(1);
            pieces.add(1, new Piece('W', destinations.get(position).getXa(), destinations.get(position).getYa(), destinations.get(position).getXb(), destinations.get(position).getYb()));
            viderDestinations();
        }
        else if (idPiece == 'B') {
            pieces.get(2).setEnMouvement(true);
            recherchePosLibre();
            assert(!destinations.isEmpty());
            System.out.println(this);
            if (this.langue.equals(Langages.ENGLISH))
                System.out.print("\n*------ Turn of Player " + numJoueur + " (1/2) -----*\n\n(B) Destination choice [1 ; " + destinations.size() + "]: ");
            else if (this.langue.equals(Langages.FRANCAIS))
                System.out.print("\n*------ Tour du Joueur " + numJoueur + " (1/2) -----*\n\n(B) Choix de destination [1 ; " + destinations.size() + "] : ");
            position = entreePosition();
            pieces.remove(2);
            pieces.add(2, new Piece('B', destinations.get(position).getXa(), destinations.get(position).getYa(), destinations.get(position).getXb(), destinations.get(position).getYb()));
            viderDestinations();
        }
    }

    private void initThreeSpotGame() {
        pieces.clear();
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
        if (this.langue.equals(Langages.ENGLISH) && !finDePartie())
            System.out.println("\n\n\n*------------- NEW TURN ------------*");
        else if (this.langue.equals(Langages.ENGLISH) && finDePartie())
            System.out.println("\n\n\n*------------ GAME OVER ------------*");
        else if (this.langue.equals(Langages.FRANCAIS) && !finDePartie())
            System.out.println("\n\n\n*----------- NOUVEAU TOUR ----------*");
        else if (this.langue.equals(Langages.FRANCAIS) && finDePartie())
            System.out.println("\n\n\n*--------- PARTIE TERMINEE ---------*");
        if (this.langue.equals(Langages.ENGLISH)) {
            System.out.print("\nPlayer 1" + (J1.getIdPiece() == 'R' ? " (Red piece): " : " (Blue piece): ") + J1.getNbPointJoueur() + " points");
            System.out.print("\nPlayer 2" + (J2.getIdPiece() == 'R' ? " (Red piece): " : " (Blue piece): ") + J2.getNbPointJoueur() + " points\n");
        }
        else if (this.langue.equals(Langages.FRANCAIS)) {
            System.out.print("\nJoueur 1" + (J1.getIdPiece() == 'R' ? " (piece Rouge) : " : " (piece Bleue) : ") + J1.getNbPointJoueur() + " points");
            System.out.print("\nJoueur 2" + (J2.getIdPiece() == 'R' ? " (piece Rouge) : " : " (piece Bleue) : ") + J2.getNbPointJoueur() + " points\n");
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
                if (this.langue.equals(Langages.ENGLISH))
                    System.out.println("\n*----- Victory of the Player 2 -----*\n");
                else if (this.langue.equals(Langages.FRANCAIS))
                    System.out.println("\n*------- Victoire du Joueur 2 ------*\n");
            } else if (J1.getNbPointJoueur() >= 12 && J2.getNbPointJoueur() >= 6) {
                if (this.langue.equals(Langages.ENGLISH))
                    System.out.println("\n*----- Victory of the Player 1 -----*\n");
                else if (this.langue.equals(Langages.FRANCAIS))
                    System.out.println("\n*------- Victoire du Joueur 1 ------*\n");
            } else if (J1.getNbPointJoueur() >= 12 && J2.getNbPointJoueur() < 6) {
                if (this.langue.equals(Langages.ENGLISH))
                    System.out.println("\n*----- Victory of the Player 2 -----*\n");
                else if (this.langue.equals(Langages.FRANCAIS))
                    System.out.println("\n*------- Victoire du Joueur 2 ------*\n");
            } else if (J2.getNbPointJoueur() >= 12 && J1.getNbPointJoueur() < 6) {
                if (this.langue.equals(Langages.ENGLISH))
                    System.out.println("\n*----- Victory of the Player 1 -----*\n");
                else if (this.langue.equals(Langages.FRANCAIS))
                    System.out.println("\n*------- Victoire du Joueur 1 ------*\n");
            }
            while (true) {
                if (this.langue.equals(Langages.ENGLISH)) {
                    System.out.print("Play again? (yes/no): ");
                    ouiNon = sc.next();
                    if (ouiNon.equals("no")) {
                        System.out.print("\nThanks for playing! ✌\uFE0F");
                        break;
                    } else if (ouiNon.equals("yes")) {
                        String ouiNonBis;
                        while (true) {
                            System.out.print("Change the language? (yes/no): ");
                            ouiNonBis = sc.next();
                            if (ouiNonBis.equals("no"))
                                break;
                            else if (ouiNonBis.equals("yes")) {
                                System.out.print("Choose a language among the available ones (FRENCH: (Francais); ENGLISH: (English)): ");
                                while (true) {
                                    ouiNonBis = sc.next();
                                    if (ouiNonBis.equals("(Francais)")) {
                                        this.langue = Langages.FRANCAIS;
                                        assert(this.langue.equals(Langages.FRANCAIS));
                                        ouiNonBis = "no";
                                        System.out.print("\n\n*--- La langue du jeu a été mise en Français avec succès! ---*\n\n");
                                        break;
                                    }
                                    else if (ouiNonBis.equals("(English)")) {
                                        ouiNonBis = "no";
                                        break;
                                    }
                                    else
                                        System.out.print("ERROR: Unreferenced language.\nTry again: ");
                                }
                                break;
                            }
                        }
                        if (ouiNonBis.equals("no"))
                            break;
                    }
                }
                else if (this.langue.equals(Langages.FRANCAIS)) {
                    System.out.print("Faire une nouvelle partie ? (oui/non) : ");
                    ouiNon = sc.next();
                    if (ouiNon.equals("non")) {
                        System.out.print("\nMerci d'avoir joué ! ✌\uFE0F");
                        break;
                    } else if (ouiNon.equals("oui")) {
                        break;
                    }
                }
            }
            if ((ouiNon.equals("non") && this.langue.equals(Langages.FRANCAIS) || (ouiNon.equals("no") && this.langue.equals(Langages.ENGLISH)))) {
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
                    Piece e = occupant(l, c);
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
                    Piece e = occupant(l, c);
                    Destination d = occupante(l, c);
                    listDestTempo.clear();
                    destinationsOccupantes(listDestTempo, l, c);
                    if (c > 0 && c < largeur-1) {
                        if (d != null) {
                            str.append(e == null ? (listDestTempo.size() > 1 ? "   " + listDestTempo.get(0).getId() + " - " + listDestTempo.get(listDestTempo.size()-1).getId() + "   " : "     " + d.getId() + "     ") : (e.estEnMouvement() ? (listDestTempo.size() > 1 ? "   " + listDestTempo.get(0).getId() + " - " + listDestTempo.get(listDestTempo.size()-1).getId() + "   " : "     " + d.getId() + "     ") : "     " + e.getId() + "    "));
                        }
                        else
                            str.append(e == null ? (listDestTempo.size() > 1 ? "   " + listDestTempo.get(0).getId() + " - " + listDestTempo.get(listDestTempo.size()-1).getId() + "   " : "           ") : "     " + (e.estEnMouvement() ? " " : e.getId()) + "     ");
                    }
                    else if (c == 0) {
                        if (d != null) {
                            str.append(e == null ? (listDestTempo.size() > 1 ? "*   " + listDestTempo.get(0).getId() + " - " + listDestTempo.get(listDestTempo.size()-1).getId() + "   *" : "*     " + d.getId() + "     *") : (e.estEnMouvement() ? (listDestTempo.size() > 1 ? "*   " + listDestTempo.get(0).getId() + " - " + listDestTempo.get(listDestTempo.size()-1).getId() + "   *" : "*     " + d.getId() + "     *") : "*     " + e.getId() + "    *"));
                        }
                        else
                            str.append(e == null ? (listDestTempo.size() > 1 ? "*   " + listDestTempo.get(0).getId() + " - " + listDestTempo.get(listDestTempo.size()-1).getId() + "   *" : "*           *") : "*     " + (e.estEnMouvement() ? " " : e.getId()) + "     *");
                    }
                    else if (c == largeur-1) {
                        if (d != null) {
                            str.append(e == null ? (listDestTempo.size() > 1 ? "*   " + listDestTempo.get(0).getId() + " - " + listDestTempo.get(listDestTempo.size()-1).getId() + "   *" : "*     " + d.getId() + "     *") : (e.estEnMouvement() ? (listDestTempo.size() > 1 ? "*   " + listDestTempo.get(0).getId() + " - " + listDestTempo.get(listDestTempo.size()-1).getId() + "   *" : "*     " + d.getId() + "     *") : "*     " + e.getId() + "    *"));
                        }
                        else
                            str.append(e == null ? (listDestTempo.size() > 1 ? "*   " + listDestTempo.get(0).getId() + " - " + listDestTempo.get(listDestTempo.size()-1).getId() + "   *" : "*     O     *"): "*     " + (e.estEnMouvement() ? "O" : e.getId()) + "     *");
                    }
                }
                str.append("\n*           *           *           *\n");
                str.append("*  *  *  *  *  *  *  *  *  *  *  *  *");
                if (l < hauteur-1)
                    str.append("\n*           *           *           *\n");
            }
            if (this.langue.equals(Langages.ENGLISH) && nbTour >= 1)
                str.append("\n            Turn number: ").append(nbTour);
            else if (this.langue.equals(Langages.FRANCAIS) && nbTour >= 1)
                str.append("\n           Tour numéro : ").append(nbTour);
        }
        String s;
        s = valueOf(str);
        return s;
    }
}
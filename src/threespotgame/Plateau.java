package threespotgame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.String.valueOf;

public class Plateau {
    private enum Langues {
        /**
         * Ensemble des différentes langues pour lesquels les différents affichages du plateau ont une version traduite
         */
        FRANCAIS, ENGLISH;
    }
    private Langues langue;
    private final int largeur, hauteur;
    private final LinkedList<Piece> pieces;
    private final LinkedList<Destination> destinations;
    private final LinkedList<Spot> spots;
    private final Joueur J1, J2;
    private int nbTour;

    /**
     * Classe propre aux différents spots du plateau
     */
    private class Spot {
        private static int nbSpots = 0;
        private final int idSpot;
        private final int x, y;

        /**
         * Constructeur de la classe privée spot
         * @param x, coordonnée x (ligne) du spot
         * @param y, coordonnée y (ligne) du spot
         */
        public Spot(int x, int y) {
            this.x = x;
            this.y = y;
            this.idSpot = ++nbSpots;
        }

        /**
         * Getter de l'Id d'un spot
         * @return int idSpot
         */
        public int getIdSpot() {
            return idSpot;
        }

        /**
         * Getter de la coordonée d'un spot
         * @return int x
         */
        public int getX() {
            return x;
        }

        /**
         * Getter de la coordonée y d'un spot
         * @return int x
         */
        public int getY() {
            return y;
        }

        /**
         * Re instancie à zéro le nombre de spots dans le classe Spot
         */
        public static void resetNbSpots() {
            Spot.nbSpots = 0;
        }
    }

    /**
     * Classe propre aux futures potentielles destinations d'une pièce du plateau
     */
    private class Destination {
        private static int nbDestinations = 0;
        private final int xa, ya, xb, yb;
        private final char idDestination;

        /**
         * Constructeur de la clasee privée Destination
         * @param xa
         * @param ya
         * @param xb
         * @param yb
         */
        public Destination(int xa, int ya, int xb, int yb) {
            ++nbDestinations;
            String s = valueOf(nbDestinations);
            this.xa = xa;
            this.ya = ya;
            this.xb = xb;
            this.yb = yb;
            this.idDestination = s.charAt(0);
        }

        /**
         * Getter du premier x (xa) de la destination
         * @return int xa
         */
        public int getXa() {
            return xa;
        }

        /**
         * Getter du premier y (ya) de la destination
         * @return int ya
         */
        public int getYa() {
            return ya;
        }

        /**
         * Getter du second x (xb) de la destination
         * @return int xb
         */
        public int getXb() {
            return xb;
        }

        /**
         * Getter du second y (yb) de la destination
         * @return int yb
         */
        public int getYb() {
            return yb;
        }

        /**
         * Getter de l'Id de la destination
         * @return char idDestination
         */
        public char getId() {
            return idDestination;
        }

        /**
         * Confirme qu'une destination est de nature verticale, ou non
         * @return boolean
         */
        public boolean estVerticale() {
            return ya==yb && xb-xa == 1;
        }

        /**
         * Confirme qu'une destination est de nature horizontale, ou non
         * @return boolean
         */
        public boolean estHorizontale() {
            return xa==xb && yb-ya == 1;
        }

        /**
         * Confirme que la coordonée réferencée correspond  à la premère coordonée (xa, ya) d'une destination
         * @param xa
         * @param ya
         * @return boolean
         */
        public boolean occupeA(int xa, int ya) {
            return this.xa == xa && this.ya == ya;
        }

        /**
         * Confirme que la coordonée réferencée correspond à la seconde coordonée (xb, yb) d'une destination
         * @param xb
         * @param yb
         * @return boolean
         */
        public boolean occupeB(int xb, int yb) {
            return this.xb == xb && this.yb == yb;
        }

        /**
         * Re instancie le nombre de destination à 0
         */
        public static void resetNbDestinations() {
            Destination.nbDestinations = 0;
        }
    }

    /**
     * Constructeur de la classe Plateau
     */
    public Plateau() {
        this.langue = Langues.FRANCAIS;
        this.largeur = 3;
        this.hauteur = 3;
        this.pieces = new LinkedList<>();
        this.destinations = new LinkedList<>();
        this.spots = new LinkedList<>();
        this.J1 = new Joueur();
        this.J2 = new Joueur();
        this.nbTour = 0;
    }

    /**
     * Ajoute une pièce e, de la classe Piece, à l'objet/au plateau de la classe Plateau
     * @param e, piece
     */
    private void ajouter(Piece e) {
        pieces.add(e);
    }

    /**
     * Ajoute un spot s, de la classe Spot, au Plateau
     * @param s, spot
     */
    private void ajouter(Spot s) {
        spots.add(s);
    }

    /**
     * Vide l'ensemble de la liste destinations du plateau, ainsi que sont compteur
     */
    private void viderSpots() {
        spots.clear();
        Spot.resetNbSpots();
    }

    /**
     * Vide l'ensemble de la liste destinations du plateau, ainsi que sont compteur
     */
    private void viderDestinations() {
        destinations.clear();
        Destination.resetNbDestinations();
    }

    /**
     * Recherche la destination occupant la case de coordonée l, c, du plateau
     * @param l, ligne (x)
     * @param c, colonne (y)
     * @return La destination d, de la classe Destination, occupant les coordonées entrées
     */
    private Destination occupante(int l, int c) {
        for (Destination d: destinations) {
            if (d.estVerticale() && d.occupeB(l, c))
                return d;
            else if (d.estHorizontale() && d.occupeA(l, c))
                return d;
        }
        return null;
    }

    /**
     * Ajoute toutes les destinations occupant la même case (Verticale vs Horizontale) dans une liste où les stocker
     * @param listDest
     * @param l, ligne (x)
     * @param c, colonne (y)
     */
    private void destinationsOccupantes(ArrayList<Destination> listDest, int l, int c) {
        for (Destination d: destinations) {
            if (d.estVerticale() && d.occupeB(l, c) || d.estHorizontale() && d.occupeA(l, c))
                listDest.add(d);
        }
        if (!listDest.isEmpty())
            if (listDest.get(0).estHorizontale()) {
                listDest.add(0, listDest.get(listDest.size()-1));
                listDest.remove(listDest.size()-1);
            }
    }

    /**
     * Recherche la Piece occupant la case de coordonée l, c, du plateau
     * @param l, ligne (x)
     * @param c, colonne (y)
     * @return Piece p, si une a été trouvée
     */
    private Piece pieceOccupant(int l, int c) {
        for (Piece p: pieces) {
            if (p.occupe(l, c))
                return p;
        }
        return null;
    }

    /**
     * Initialise les pièces d'un objet de la classe Plateau
     */
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

    /**
     * Initialise les joueurs, de la classe Joueur, d'un objet de la classe Plateau afin de jouer au 3 Spot Game
     */
    private void initJoueursThreeSpotGame() {
        String couleurPiece;
        Scanner scanCouleur = new Scanner(System.in);
        J1.setNbPointJoueur(0);
        J2.setNbPointJoueur(0);
        this.nbTour = 0;
        if (this.langue.equals(Langues.ENGLISH))
            System.out.print("Player 1 must choose his colored piece (Red/Blue): ");
        else if (this.langue.equals(Langues.FRANCAIS))
            System.out.print("Joueur 1 doit choisir sa pièce de couleur (Rouge/Bleue) : ");
        while(true) {
            couleurPiece = scanCouleur.next();
            if (this.langue.equals(Langues.FRANCAIS)) {
                if ("ROUGE".equals(couleurPiece.toUpperCase())) {
                    J1.setIdPiece('R');
                    J2.setIdPiece('B');
                    assert('R' == J1.getIdPiece());
                    assert('B' == J2.getIdPiece());
                    System.out.print("\n          | Joueur 1 : R |\n");
                    System.out.print("          | Joueur 2 : B |\n\n");
                    break;
                } else if ("BLEUE".equals(couleurPiece.toUpperCase())) {
                    J1.setIdPiece('B');
                    J2.setIdPiece('R');
                    assert('B' == J1.getIdPiece());
                    assert('R' == J2.getIdPiece());
                    System.out.print("\n          | Joueur 1 : B |\n");
                    System.out.print("          | Joueur 2 : R |\n\n");
                    break;
                } else if ("(ENGLISH)".equals(couleurPiece.toUpperCase())) {
                    this.langue = Langues.ENGLISH;
                    assert(this.langue.equals(Langues.ENGLISH));
                    System.out.print("\n\n*--- The language of the game has been successfully set to English! ---*\n\n");
                    System.out.print("Player 1 must choose his colored piece (Red/Blue): ");
                    while (true) {
                        couleurPiece = scanCouleur.next();
                        if ("RED".toUpperCase().equals(couleurPiece)) {
                            J1.setIdPiece('R');
                            J2.setIdPiece('B');
                            assert('R' == J1.getIdPiece());
                            assert('B' == J2.getIdPiece());
                            System.out.print("\n           | Player 1: R |\n");
                            System.out.print("           | Player 2: B |\n\n");
                            break;
                        } else if ("BLUE".toUpperCase().equals(couleurPiece)) {
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
            else if (this.langue.equals(Langues.ENGLISH)) {
                while (true) {
                    if ("RED".equals(couleurPiece.toUpperCase())) {
                        J1.setIdPiece('R');
                        J2.setIdPiece('B');
                        assert('R' == J1.getIdPiece());
                        assert('B' == J2.getIdPiece());
                        System.out.print("\n           | Player 1: R |\n");
                        System.out.print("           | Player 2: B |\n\n");
                        break;
                    } else if ("BLUE".equals(couleurPiece.toUpperCase())) {
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

    /**
     * Confirme si la pièce rouge (La première) se trouve sur un spot
     * @param s, spot
     * @return boolean
     */
    private boolean rougeEstSurSpot(Spot s) {
        return pieces.get(0).occupe(s.getX(),s.getY());
    }

    /**
     * Confirme si la pièce bleue (La dernière) se trouve sur un spot
     * @param s, spot
     * @return boolean
     */
    private boolean bleuEstSurSpot(Spot s) {
        return pieces.get(2).occupe(s.getX(),s.getY());
    }

    /**
     * Incrémente les points au joueur donné en fonction de la pièce rouge, et des spots couverts par cette dernière
     * @param j, joueur
     */
    private void pointsPourRouge(Joueur j) {
        assert(j.getIdPiece() == 'R');
        for(Spot s: spots) {
            if (rougeEstSurSpot(s))
                j.incNbPointsJoueur();
        }
    }

    /**
     * Incrémente les points au joueur donné en fonction de la pièce bleue, et des spots couverts par cette dernière
     * @param j, joueur
     */
    private void pointsPourBleu(Joueur j) {
        assert(j.getIdPiece() == 'B');
        for(Spot s: spots) {
            if (bleuEstSurSpot(s))
                j.incNbPointsJoueur();
        }
    }

    /**
     * Confirme si les deux cases, de coordonées données, sont non occupées par une pièce du plateau
     * @param xa, ligne
     * @param ya, colonne
     * @param xb, ligne
     * @param yb, colonne
     * @return boolean
     */
    private boolean sontNonOccupees(int xa, int ya, int xb, int yb) {
        for (Piece e: pieces) {
            if (e.occupe(xa, ya, xb, yb))
                return false;
        }
        return true;
    }

    /**
     * Confirme si la case, de coordonée donnée, est non occupée par une pièce du plateau
     * @param x, ligne
     * @param y, colonne
     * @return boolean
     */
    private boolean estNonOccupee(int x, int y) {
        for (Piece e: pieces) {
            if (e.occupe(x, y) && !e.estEnMouvement())
                return false;
        }
        return true;
    }

    /**
     * Confirme si les deux cases, de coordonées données, sont non occupées par une position, de la classe Position, du plateau
     * @param xa, ligne
     * @param ya, colonne
     * @param xb, ligne
     * @param yb, colonne
     * @return boolean
     */
    private boolean estPosNonOccupee(int xa, int ya, int xb, int yb) {
        for (Destination d: destinations) {
            if (d.estVerticale() && d.occupeB(xb, yb) && d.occupeA(xa, ya) || d.estHorizontale() && d.occupeA(xa, ya) && d.occupeB(xb, yb))
                return false;
        }
        return true;
    }

    /**
     * Recherche toutes les positions du plateau qui sont libres/non occupées, et les ajoute à la liste destinations de l'objet de plateau
     */
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

    /**
     * Prend en input la position choisit par le joueur utilisateur
     * @return int position, la position entrée par le joueur
     */
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
                if (this.langue.equals(Langues.ENGLISH))
                    System.out.print("\nERROR: unknown position.\nTry again: ");
                else if (this.langue.equals(Langues.FRANCAIS))
                    System.out.print("\nERREUR : position inexistante.\nRéessayez : ");
            }
            else
                break;
        }
        return --position;
    }

    /**
     * Déplace les pièces en fonctions des actions d'un joueur
     * @param numJoueur, Le numéro du joueur faisant une action
     * @param idPiece, Id de la pièce avec laquelle le joueur s'apprète à effectuer une action
     */
    private void deplacementPieceJoueur(int numJoueur, char idPiece) {
        int position;
        if (idPiece == 'R') {
            pieces.get(0).setEnMouvement(true);
            recherchePosLibre();
            assert(!destinations.isEmpty());
            System.out.println(this);
            if (this.langue.equals(Langues.ENGLISH))
                System.out.print("\n*------ Turn of Player " + numJoueur + " (1/2) -----*\n\n(R) Destination choice [1 ; " + destinations.size() + "]: ");
            else if (this.langue.equals(Langues.FRANCAIS))
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
            if (this.langue.equals(Langues.ENGLISH))
                System.out.print("\n*------ Turn of Player " + numJoueur + " (2/2) -----*\n\n(W) Destination choice [1 ; " + destinations.size() + "]: ");
            else if (this.langue.equals(Langues.FRANCAIS))
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
            if (this.langue.equals(Langues.ENGLISH))
                System.out.print("\n*------ Turn of Player " + numJoueur + " (1/2) -----*\n\n(B) Destination choice [1 ; " + destinations.size() + "]: ");
            else if (this.langue.equals(Langues.FRANCAIS))
                System.out.print("\n*------ Tour du Joueur " + numJoueur + " (1/2) -----*\n\n(B) Choix de destination [1 ; " + destinations.size() + "] : ");
            position = entreePosition();
            pieces.remove(2);
            pieces.add(2, new Piece('B', destinations.get(position).getXa(), destinations.get(position).getYa(), destinations.get(position).getXb(), destinations.get(position).getYb()));
            viderDestinations();
        }
    }

    /**
     * Initialise l'objet afin de faire un partie de 3 Spot Game
     */
    private void initThreeSpotGame() {
        pieces.clear();
        initPiecesPlateau();
        initJoueursThreeSpotGame();
    }

    /**
     * Effectue un nouveau tour de 3 Spot Game
     */
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
        if (this.langue.equals(Langues.ENGLISH) && !finDePartie())
            System.out.println("\n\n\n*------------- NEW TURN ------------*");
        else if (this.langue.equals(Langues.ENGLISH) && finDePartie())
            System.out.println("\n\n\n*------------ GAME OVER ------------*");
        else if (this.langue.equals(Langues.FRANCAIS) && !finDePartie())
            System.out.println("\n\n\n*----------- NOUVEAU TOUR ----------*");
        else if (this.langue.equals(Langues.FRANCAIS) && finDePartie())
            System.out.println("\n\n\n*--------- PARTIE TERMINEE ---------*");
        if (this.langue.equals(Langues.ENGLISH)) {
            System.out.print("\nPlayer 1" + (J1.getIdPiece() == 'R' ? " (Red piece): " : " (Blue piece): ") + J1.getNbPointJoueur() + " points");
            System.out.print("\nPlayer 2" + (J2.getIdPiece() == 'R' ? " (Red piece): " : " (Blue piece): ") + J2.getNbPointJoueur() + " points\n");
        }
        else if (this.langue.equals(Langues.FRANCAIS)) {
            System.out.print("\nJoueur 1" + (J1.getIdPiece() == 'R' ? " (piece Rouge) : " : " (piece Bleue) : ") + J1.getNbPointJoueur() + " points");
            System.out.print("\nJoueur 2" + (J2.getIdPiece() == 'R' ? " (piece Rouge) : " : " (piece Bleue) : ") + J2.getNbPointJoueur() + " points\n");
        }
    }

    /**
     * Confirme si la condition de fin de partie est atteinte ou non
     * @return boolean
     */
    private boolean finDePartie() {
        return(J1.getNbPointJoueur() >= 12 || J2.getNbPointJoueur() >= 12);
    }

    /**
     * Fait jouer au moins une partie de 3 Spot Game
     */
    public void jouerThreeSpotGame() {
        Scanner sc = new Scanner(System.in);
        String ouiNon;
        while (true) {
            initThreeSpotGame();
            while (!finDePartie()) {
                nouvTour();
            }
            if (J2.getNbPointJoueur() >= 12 && J1.getNbPointJoueur() >= 6) {
                if (this.langue.equals(Langues.ENGLISH))
                    System.out.println("\n*----- Victory of the Player 2 -----*\n");
                else if (this.langue.equals(Langues.FRANCAIS))
                    System.out.println("\n*------- Victoire du Joueur 2 ------*\n");
            } else if (J1.getNbPointJoueur() >= 12 && J2.getNbPointJoueur() >= 6) {
                if (this.langue.equals(Langues.ENGLISH))
                    System.out.println("\n*----- Victory of the Player 1 -----*\n");
                else if (this.langue.equals(Langues.FRANCAIS))
                    System.out.println("\n*------- Victoire du Joueur 1 ------*\n");
            } else if (J1.getNbPointJoueur() >= 12 && J2.getNbPointJoueur() < 6) {
                if (this.langue.equals(Langues.ENGLISH))
                    System.out.println("\n*----- Victory of the Player 2 -----*\n");
                else if (this.langue.equals(Langues.FRANCAIS))
                    System.out.println("\n*------- Victoire du Joueur 2 ------*\n");
            } else if (J2.getNbPointJoueur() >= 12 && J1.getNbPointJoueur() < 6) {
                if (this.langue.equals(Langues.ENGLISH))
                    System.out.println("\n*----- Victory of the Player 1 -----*\n");
                else if (this.langue.equals(Langues.FRANCAIS))
                    System.out.println("\n*------- Victoire du Joueur 1 ------*\n");
            }
            while (true) {
                if (this.langue.equals(Langues.ENGLISH)) {
                    System.out.print("Play again? (yes/no): ");
                    ouiNon = sc.next();
                    if (ouiNon.toUpperCase().equals("NO")) {
                        System.out.print("\nThanks for playing!");
                        break;
                    } else if (ouiNon.toUpperCase().equals("YES")) {
                        String ouiNonBis;
                        while (true) {
                            System.out.print("Change the language? (yes/no): ");
                            ouiNonBis = sc.next();
                            if (ouiNonBis.toUpperCase().equals("NO"))
                                break;
                            else if (ouiNonBis.toUpperCase().equals("YES")) {
                                System.out.print("Choose a language among the available ones (FRENCH: (Francais); ENGLISH: (English)): ");
                                while (true) {
                                    ouiNonBis = sc.next();
                                    if (ouiNonBis.toUpperCase().equals("(FRANCAIS)")) {
                                        this.langue = Langues.FRANCAIS;
                                        assert(this.langue.equals(Langues.FRANCAIS));
                                        ouiNonBis = "NO";
                                        System.out.print("\n\n*--- La langue du jeu a été mise en Français avec succès! ---*\n\n");
                                        break;
                                    }
                                    else if (ouiNonBis.toUpperCase().equals("(ENGLISH)")) {
                                        ouiNonBis = "NO";
                                        break;
                                    }
                                    else
                                        System.out.print("ERROR: Unreferenced language.\nTry again: ");
                                }
                                break;
                            }
                        }
                        if (ouiNonBis.toUpperCase().equals("NO"))
                            break;
                    }
                }
                else if (this.langue.equals(Langues.FRANCAIS)) {
                    System.out.print("Faire une nouvelle partie ? (oui/non) : ");
                    ouiNon = sc.next();
                    if (ouiNon.toUpperCase().equals("NON")) {
                        System.out.print("\nMerci d'avoir joué !");
                        break;
                    } else if (ouiNon.toUpperCase().equals("OUI")) {
                        break;
                    }
                }
            }
            if ((ouiNon.toUpperCase().equals("NON") && this.langue.equals(Langues.FRANCAIS) || (ouiNon.toUpperCase().equals("NO") && this.langue.equals(Langues.ENGLISH)))) {
                break;
            }
        }
    }

    /**
     * toString de la classe Plateau. Enregistre sous forme de chaine de caractères les données à enregistrer de la sorte dans l'objet de la classe Plateau
     * @return String de l'objet de la classe Plateau
     */
    public String toString() {
        StringBuilder str = new StringBuilder("*  *  *  *  *  *  *  *  *  *  *  *  *");
        str.append("\n*           *           *           *\n");
        if (destinations.isEmpty()) {
            for (int l = 0; l < hauteur; ++l) {
                for (int c = 0; c < largeur; ++c) {
                    Piece e = pieceOccupant(l, c);
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
                    Piece e = pieceOccupant(l, c);
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
            if (this.langue.equals(Langues.ENGLISH) && nbTour >= 1)
                if (nbTour >= 100)
                    str.append("\n          Turn number: ").append(nbTour); // Garder centré le comtpeur de tour >= 100
                else if (nbTour >= 10)
                    str.append("\n           Turn number: ").append(nbTour); // Garder centré le comtpeur de tour >= 10
                else
                    str.append("\n            Turn number: ").append(nbTour);
            else if (this.langue.equals(Langues.FRANCAIS) && nbTour >= 1)
                if (nbTour >= 100)
                    str.append("\n         Tour numéro : ").append(nbTour); // Garder centré le comtpeur de tour >= 100
                else if (nbTour >= 10)
                    str.append("\n          Tour numéro : ").append(nbTour); // Garder centré le comtpeur de tour >= 10
                else
                    str.append("\n           Tour numéro : ").append(nbTour);
        }
        String s;
        s = valueOf(str);
        return s;
    }
}
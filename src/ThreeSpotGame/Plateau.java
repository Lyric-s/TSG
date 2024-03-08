package ThreeSpotGame;


import java.util.LinkedList;

import static java.lang.String.valueOf;

public class Plateau {
    private final int largeur, hauteur;
    private final LinkedList<Element> elements;
    private final LinkedList<Spot> spots;
    private Joueur J1, J2;

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

    public void initPlateau() {
        ajouter(new Element('R',0,1,0,2));
        assert(elements.getFirst().getId()=='R');
        ajouter(new Element('W',1,1,1,2));
        assert(elements.get(1).getId()=='W');
        ajouter(new Element('B',2,1,2,1));
        assert(elements.get(2).getId()=='B');
        ajouter(new Spot(0,2));
        assert(spots.getFirst().getIdSpot()==1);
        ajouter(new Spot(1,2));
        assert(spots.get(1).getIdSpot()==2);
        ajouter(new Spot(2,2));
        assert(spots.get(2).getIdSpot()==3);
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

    public int pointsPourRouge() {
        int pts = 0;
        for(Spot s: spots) {
            if (rougeEstSurSpot(s))
                ++pts;
        }
        return pts;
    }

    public int pointsPourBleu() {
        int pts = 0;
        for(Spot s: spots) {
            if (bleuEstSurSpot(s))
                ++pts;
        }
        return pts;
    }

    public void prochainTour() {
        //to do...
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
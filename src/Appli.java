import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class Appli {
    public static void main(String[] args) throws InterruptedException {
        Grille g = new Grille(10,10);
        g.ajouter(new Element('R',1,7,1,0));
        g.ajouter(new Element('A',5,1,1,-1));
        g.ajouter(new Element('B',7,7,0,1));
        g.ajouter(new Element('F',6,8,-1,1));
        g.ajouter(new Element('T',4,3,-1,-1));
        g.ajouter(new Element('1',1,1,1,1));
        while(true) {
            System.out.println(g.toString());
            TimeUnit.SECONDS.sleep(1);
            g.simuler();
        }
    }
}
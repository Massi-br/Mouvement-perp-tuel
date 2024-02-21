package motion.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Random;

import javax.swing.JLabel;

public class SimpleComponent extends JLabel implements Animable {

    // ATTRIBUTS

    private final Random r;

    // CONSTRUCTEURS

    public SimpleComponent(int w, int h) {
        super(" ");
        setOpaque(true);
        setPreferredSize(new Dimension(w, h));
        r = new Random();
        setBackground(new Color(r.nextInt()));
    }

    // COMMANDE

    @Override
    public void animate() {
        setBackground(new Color(r.nextInt()));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Il faut s'assurer du vidage immédiat du buffer d'affichage.
        // D'après ce que j'ai lu, et ce que Nicolas a expérimenté, cela pose
        //  des problèmes de fréquence de rafraîchissement aux gestionnaires
        //  de fenêtres sous Linux et probablement aussi sous mac, mais pas
        //  sous Windows...
        // https://bugs.java.com/bugdatabase/view_bug.do?bug_id=8068529
        Toolkit.getDefaultToolkit().sync();
    }
}

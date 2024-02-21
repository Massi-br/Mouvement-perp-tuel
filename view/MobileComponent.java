package motion.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;

import javax.swing.JComponent;

import motion.model.Mobile;
import motion.model.StdMobile;
import util.Contract;

public class MobileComponent extends JComponent implements Animable {

    /**
     * La couleur (bleu) du rectangle statique du modèle.
     */
    private static final Color STAT_COLOR = Color.BLUE;

    /**
     * La couleur (rouge) du rectangle mobile du modèle.
     */
    private static final Color MOV_COLOR = Color.RED;

    // ATTRIBUTS

    // Le modèle du MobileComponent.
    private final Mobile model;

    // CONSTRUCTEURS

    /**
     * @pre <pre>
     *     width > 0 && height > 0
     *     0 < ray <= min(width, height) / 2 </pre>
     * @post <pre>
     *     model.isMovable()
     *     model.getStaticRect() est un rectangle (0, 0, width, height)
     *     model.getMovingRect() est un rectangle (0, 0, 2 * ray, 2 * ray)
     *     model.getHorizontalShift() == 0
     *     model.getVerticalShift() == 0
     *     getPreferredSize().width == width
     *     getPreferredSize().height == height </pre>
     */
    public MobileComponent(int width, int height, int ray) {
        Contract.checkCondition(width > 0 && height > 0);
        Contract.checkCondition(ray > 0 && ray <= Math.min(width, height) / 2);

        model = buildModel(width, height, ray);
        configureView();
        connectControllers();
    }

    // COMMANDES

    @Override
    public void animate() {
        /*****************/
        /** A COMPLETER **/
        /*****************/
    }

    // OUTILS

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(STAT_COLOR);
        Rectangle sr = model.getStaticRect();
        g.fillRect(0, 0, sr.width, sr.height);
        g.setColor(MOV_COLOR);
        Rectangle mr = model.getMovingRect();
        Point tlc = mr.getLocation();
        g.fillOval(tlc.x, tlc.y, mr.width, mr.height);
        // Il faut s'assurer du vidage immédiat du buffer d'affichage.
        // D'après ce que j'ai lu, et ce que Nicolas a expérimenté, cela pose
        //  des problèmes de fréquence de rafraîchissement aux gestionnaires
        //  de fenêtres sous Linux et probablement aussi sous mac, mais pas
        //  sous Windows...
        // https://bugs.java.com/bugdatabase/view_bug.do?bug_id=8068529
        Toolkit.getDefaultToolkit().sync();
    }

    private Mobile buildModel(int width, int height, int ray) {
        Dimension dim = new Dimension(width, height);
        Rectangle sr = new Rectangle(dim);
        Rectangle mr = new Rectangle(new Dimension(2 * ray, 2 * ray));
        Mobile mobile = new StdMobile(sr, mr);
        mobile.setHorizontalShift(ray / 2);
        mobile.setVerticalShift(ray / 2);
        return mobile;
    }
    
    private void configureView() {
        Rectangle r = model.getStaticRect();
        Dimension d = new Dimension(r.width, r.height);
        setPreferredSize(d);
    }

    private void connectControllers() {
        /*****************/
        /** A COMPLETER **/
        /*****************/
    }
    
    // TYPES IMBRIQUES

    private class MouseHandler extends MouseAdapter {
        /*****************/
        /** A COMPLETER **/
        /*****************/
    }
}

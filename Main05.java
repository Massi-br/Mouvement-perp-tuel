package motion;

import javax.swing.SwingUtilities;

import motion.gui.MotionAppli;
import motion.model.Animator;
import motion.model.ThreadAnimator;
import motion.model.WorkerAnimator;
import motion.view.Animable;
import motion.view.MobileComponent;
import motion.view.SimpleComponent;

/**
 * L'argument attendu dans main() en première position (args[0]) est un mot.
 * Ce mot peut contenir, entre autre, un ou plusieurs des quatre caractères
 *  suivant :
 * - 'T' pour ThreadAnimator
 * - 'W' pour WorkerAnimator
 * - 'S' pour SimpleComponent
 * - 'M' pour MobileComponent
 * quel que soit leur ordre d'apparition et leur nombre dans le mot.
 * Pour configurer l'application il faut une lettre pour l'animateur et une
 *  autre pour le composant animable.
 * args[0] est pris en compte pour construire un mot à deux lettres :
 *  'T' ou 'W', puis 'S' ou 'M'.
 * Si args[0] ne contient pas une lettre pour chacune des deux catégories, les
 *  caractères absents sont remplacés par ceux contenus dans DEFAULT.
 * Si un même caractère est rencontré plusieurs fois dans args[0], ou si ce
 *  dernier contient les deux caractères d'une même catégorie, c'est le premier
 *  trouvé en partant de la gauche qui est pris en compte.
 * Quelques exemples :
 * - "sw" ou "WS" ou "swift" -> "WS"
 * - "TS" ou "tps" ou "Tips" -> "TS"
 * - "Wm" ou "windowManager" -> "WM"
 * - "tM" ou "tamis" ou "metis" -> "TM"
 * - "wora" ou "pawnee" -> "W" + DEFAULT.charAt(1)
 * - "mec" ou "meuf" -> DEFAULT.charAt(0) + "M"
 * - "euh..." ou "bonjour" ou "@#!!$?" -> DEFAULT
 */
public final class Main05 {
    
    private static final String USAGE =
            "\n ==>\n"
            + " ==> MESSAGE IMPORTANT :\n"
            + " ==> Si vous avez fini de développer la classe %1$s,\n"
            + " ==> * cliquez sur le numéro de ligne indiqué au sommet de la"
            + " pile d'erreurs ci-dessous,\n"
            + " ==> * allez à la ligne au-dessus de celle où vous êtes"
            + " arrivé,\n"
            + " ==> * supprimez le premier caractère '/' (ce qui inverse les"
            + " commentaires),\n"
            + " ==> * importez le type %1$s,\n"
            + " ==> * relancez l'application.\n"
            + " ==>";

    private static final String DEFAULT = "WS";
    private static final String ALTERNATIVE = "TM";
    
    private Main05() {
        // rien
    }
    
    // POINT D'ENTREE

    public static void main(final String[] args) {
        String name = name(args);
        Creator c = Creator.TS;
        		Creator.valueOf(name);
        final Animable animable = c.createAnimable();
        final Animator animator = c.createAnimator();
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MotionAppli(animable, animator).display();
            }
        });
    }
    
    private static String name(String[] args) {
        if (args.length == 0) {
            return DEFAULT;
        }
        
        String arg = args[0];
        StringBuilder name = new StringBuilder(DEFAULT);
        arg = arg.trim().toUpperCase() + DEFAULT;
        int iD0 = arg.indexOf(DEFAULT.charAt(0));
        int iD1 = arg.indexOf(DEFAULT.charAt(1));
        int iA0 = arg.indexOf(ALTERNATIVE.charAt(0));
        int iA1 = arg.indexOf(ALTERNATIVE.charAt(1));
        if (iA0 >= 0 && iA0 < iD0) {
            name.setCharAt(0, ALTERNATIVE.charAt(0));
        }
        if (iA1 >= 0 && iA1 < iD1) {
            name.setCharAt(1, ALTERNATIVE.charAt(1));
        }
        return name.toString();
    }
    
    /**
     * Permet la création de l'animable selon le choix défini à l'exécution
     *  (voir paramètre du constructeur, transmis par la méthode Main.main).
     */
    public enum Creator {
        TS {
            @Override Animator createAnimator() {
                return ta(SIMPLE_RANGE);
            }
            @Override Animable createAnimable() {
                return sc();
            }
        },
        TM {
            @Override Animator createAnimator() {
                return ta(MOBILE_RANGE);
            }
            @Override Animable createAnimable() {
                return mc();
            }
        },
        WS {
            @Override Animator createAnimator() {
                return wa(SIMPLE_RANGE);
            }
            @Override Animable createAnimable() {
                return sc();
            }
        },
        WM {
            @Override Animator createAnimator() {
                return wa(MOBILE_RANGE);
            }
            @Override Animable createAnimable() {
                return mc();
            }
        };
        
        private static final int SIMPLE_RANGE = 10;
        
        private static final int MOBILE_RANGE = 100;
        private static final int WIDTH = 419;
        private static final int HEIGHT = 211;
        private static final int MOBILE_RAY = 10;
        
        abstract Animator createAnimator();
        abstract Animable createAnimable();
        
        static Animable sc() {
            return new SimpleComponent(WIDTH, HEIGHT);
        }
        static Animable mc() {
            return new MobileComponent(WIDTH, HEIGHT, MOBILE_RAY);
        }
        static Animator ta(int range) {
            /*
            throw new UnsupportedOperationException(
                    String.format(USAGE, "ThreadAnimator"));
            /*/
            return new ThreadAnimator(range);
            //*/
        }
       static Animator wa(int range) {
            /*
            throw new UnsupportedOperationException(
                    String.format(USAGE, "WorkerAnimator"));
            /*/
            return new WorkerAnimator(range);
            //*/
        }
    }
}

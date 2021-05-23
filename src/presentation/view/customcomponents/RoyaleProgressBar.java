package presentation.view.customcomponents;

import presentation.graphics.MenuGraphics;

import javax.swing.*;
import java.awt.*;

/**
 * RoyaleProgressBar is the custom JProgressBar of the application.
 * <p>It features a custom style and added functionalities.
 *
 * @see JProgressBar
 * @version 1.0
 */
public class RoyaleProgressBar extends JProgressBar {


    /**
     * Default RoyaleProgressBar constructor.
     */
    public RoyaleProgressBar(){
        super();
        setCursor(MenuGraphics.getDefaultCursor());
   }

}

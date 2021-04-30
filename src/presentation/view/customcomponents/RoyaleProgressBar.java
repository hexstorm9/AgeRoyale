package presentation.view.customcomponents;

import presentation.graphics.MenuGraphics;

import javax.swing.*;

/**
 * RoyaleProgressBar is the custom JProgressBar of the application.
 * <p>It features a custom style and added functionalities.
 *
 * @see JProgressBar
 * @version 1.0
 */
public class RoyaleProgressBar extends JProgressBar {


    public RoyaleProgressBar(){
        super();
        setCursor(MenuGraphics.getInstance().getDefaultCursor());
    }

}

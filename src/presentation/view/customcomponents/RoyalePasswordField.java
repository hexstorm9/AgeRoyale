package presentation.view.customcomponents;

import presentation.graphics.MenuGraphics;

import javax.swing.*;

/**
 * RoyalePasswordField is the custom JPasswordField of the application.
 * <p>It features a custom style and added functionalities.
 *
 * @see JPasswordField
 * @version 1.0
 */
public class RoyalePasswordField extends JPasswordField {

    /**
     * Default RoyalePasswordField constructor.
     */
    public RoyalePasswordField() {
        super(20);
        setFont(MenuGraphics.getMainFont());
        setCursor(MenuGraphics.getWritingCursor());
        setEchoChar('*');
    }

}

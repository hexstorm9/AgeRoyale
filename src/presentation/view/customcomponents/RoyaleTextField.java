package presentation.view.customcomponents;

import presentation.graphics.MenuGraphics;

import javax.swing.*;

/**
 * RoyaleTextField is the custom JTextField of the application.
 * <p>It features a custom style and added functionalities.
 *
 * @see JTextField
 * @version 1.0
*/
public class RoyaleTextField extends JTextField {


    public RoyaleTextField() {
        super(20);
        setFont(MenuGraphics.getInstance().getMainFont());
        setCursor(MenuGraphics.getInstance().getWritingCursor());
    }
}

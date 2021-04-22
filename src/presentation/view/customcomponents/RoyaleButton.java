package presentation.view.customcomponents;


import presentation.graphics.MenuGraphics;

import javax.swing.*;
import java.awt.*;


/**
 * RoyaleButton is the custom JButton of the application.
 * <p>It features a custom style depending on the {@link ButtonType} provided on the constructor.
 *
 * @see JButton
 * @version 1.0
*/
public class RoyaleButton extends JButton {

    public enum ButtonType{
        NORMAL,
        BIG
    }

    public RoyaleButton(String buttonMessage){
        super(buttonMessage);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFont(MenuGraphics.getInstance().getMainFont());
        setBackground(MenuGraphics.YELLOW);
        setOpaque(true);
        setBorderPainted(false);

        setMargin(new Insets(10, 20, 10, 20));
    }

}

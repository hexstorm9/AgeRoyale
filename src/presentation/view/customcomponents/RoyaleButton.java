package presentation.view.customcomponents;


import business.entities.Sounds;
import presentation.graphics.MenuGraphics;
import presentation.sound.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * RoyaleButton is the custom JButton of the application.
 * <p>It features a custom style depending on the {@link ButtonType} provided on the constructor.
 *
 * @see JButton
 * @version 1.0
*/
public class RoyaleButton extends JButton implements MouseListener{


    public enum ButtonType{
        NORMAL,
        BIG
    }

    public RoyaleButton(String buttonMessage){
        super(buttonMessage);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBackground(MenuGraphics.YELLOW);
        setOpaque(true);
        setBorderPainted(false);

        setMargin(new Insets(10, 20, 10, 20));
        addMouseListener(this);

        setFont(MenuGraphics.getInstance().getMainFont());
        setCursor(MenuGraphics.getInstance().getClickableCursor());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(isEnabled()){
            SoundPlayer.getInstance().play(Sounds.BUTTON);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        if(isEnabled()){
            setFont(getFont().deriveFont(Font.BOLD));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(isEnabled()){
            setFont(getFont().deriveFont(Font.TRUETYPE_FONT));
        }
    }
}

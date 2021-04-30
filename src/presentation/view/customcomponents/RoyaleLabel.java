package presentation.view.customcomponents;


import business.entities.Sounds;
import presentation.graphics.MenuGraphics;
import presentation.sound.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * RoyaleLabel is the custom {@code JLabel} of the program.
 * <p>The label will be formatted according to the {@link LabelType} provided in the constructor.
 *
 * @see JLabel
 * @version 1.0
 */
public class RoyaleLabel extends JLabel implements MouseListener{

    private String actionCommand;

    private boolean clickable; //Can the Label be clicked?
    private MouseListener[] mouseListenerBuffer; //Needed so as to disable clicks temporarily


    public RoyaleLabel(ImageIcon imageIcon) {
        super(imageIcon);
    }


    public enum LabelType{
        TITLE,
        PARAGRAPH,
        LINK,
        ERROR
    }


    public RoyaleLabel(String s, LabelType labelType){
        super(s, JLabel.CENTER);

        setFont(MenuGraphics.getInstance().getMainFont());
        setForeground(Color.WHITE);
        switch (labelType){
            case TITLE:
                setFont(getFont().deriveFont(40f));
                setForeground(MenuGraphics.YELLOW);
                break;
            case PARAGRAPH:
                setFont(getFont().deriveFont(20f));
                break;
            case LINK:
                addMouseListener(this);
                setFont(getFont().deriveFont(16f));
                break;
            case ERROR:
                setFont(getFont().deriveFont(14f));
                setForeground(MenuGraphics.BLUE);
                break;
            default:
                setFont(getFont().deriveFont(16f));
        }
    }


    /**
     * Sets the action command of this Label.
     * <p>As RoyaleLabels can be clicked (using a mouseListener), an actionCommand is necessary so as to
     * detect what RoyaleLabel has been pressed.
      * @param actionCommand Action Command of this Label
     */
    public void setActionCommand(String actionCommand){
       this.actionCommand = actionCommand;
    }

    /**
     * Returns the action command of this label
     * @return Action Command of this Label
     */
    public String getActionCommand(){
        return actionCommand;
    }

    /**
     * Modifies whether the RoyaleLabel can be clicked or not.
     * <p>Depending on its state, the cursor when hovering the Label will change.
     * @param clickable Whether it's clickable or not
     */
    public void setClickable(boolean clickable){
        if(clickable){
            this.clickable = true;
            setCursor(MenuGraphics.getInstance().getClickableCursor());

            if(mouseListenerBuffer == null) return;
            for(MouseListener ml: mouseListenerBuffer) addMouseListener(ml);
            mouseListenerBuffer = null;
        }
        else{
            this.clickable = false;
            setCursor(MenuGraphics.getInstance().getDefaultCursor());
            mouseListenerBuffer = getMouseListeners();

            if(mouseListenerBuffer == null) return;
            for(MouseListener ml: mouseListenerBuffer) removeMouseListener(ml);
        }
    }




    @Override
    public void mouseEntered(MouseEvent e) {
        setForeground(MenuGraphics.YELLOW);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setForeground(Color.WHITE);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(clickable){
            SoundPlayer.getInstance().play(Sounds.BUTTON);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

}

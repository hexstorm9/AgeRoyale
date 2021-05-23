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

    private Color foregroundColor;

    /**
     * There are different types of {@link RoyaleLabel} you can construct.
     * <p>Use this enum to provide a type on the constructor
     */
    public enum LabelType{
        /**
         * Defines a title Label
         */
        TITLE,
        /**
         * Defines a paragraph Label
         */
        PARAGRAPH,
        /**
         * Defines a lin Label
         */
        LINK,
        /**
         * Defines an error Label
         */
        ERROR
    }


    /**
     * Default RoyaleLabel Constructor.
     * @param s The string of the label
     * @param labelType The type of the label
     *
     * @see LabelType
     */
    public RoyaleLabel(String s, LabelType labelType){
        super(s, JLabel.CENTER);

        setFont(MenuGraphics.getMainFont());
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

        foregroundColor = getForeground();
    }



    /**
     * RoyaleLabel constructor for Images.
     * <p>Instantiates a new RoyaleLabel with an image
     * @param imageIcon The image that the RoyaleLabel will hold
     *
     * @see ImageIcon
     */
    public RoyaleLabel(ImageIcon imageIcon) {
        super(imageIcon);
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
            setCursor(MenuGraphics.getClickableCursor());

            if(mouseListenerBuffer == null) return;
            for(MouseListener ml: mouseListenerBuffer) addMouseListener(ml);
            mouseListenerBuffer = null;
        }
        else{
            this.clickable = false;
            setCursor(MenuGraphics.getDefaultCursor());
            mouseListenerBuffer = getMouseListeners();

            if(mouseListenerBuffer == null) return;
            for(MouseListener ml: mouseListenerBuffer) removeMouseListener(ml);
        }
    }




    @Override
    public void mouseEntered(MouseEvent e) {
        foregroundColor = getForeground(); //Let's get the current color before changing it
        setForeground(MenuGraphics.YELLOW);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setForeground(foregroundColor);
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if(clickable){
            SoundPlayer.getInstance().play(Sounds.BUTTON);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

}

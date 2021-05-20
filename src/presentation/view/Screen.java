package presentation.view;

import javax.swing.*;
import java.awt.*;

/**
 * Parent class of all {@code Screens} of the Game.
 * <p>It defines all basic functionality of a {@code Screen}, given the characteristics of its controller,
 * the {@link presentation.controller.ScreenController}
 * <p>Every {@code Screen} will be put on top of the {@link RoyaleFrame} by its corresponding
 * {@link presentation.controller.ScreenController}
 *
 * @version 1.0
 * @see presentation.controller.ScreenController
 */
public class Screen extends JPanel {

    protected final int SCREEN_HEIGHT;

    /**
     * Default Screen constructor.
     * <p>The screen height must be provided in order to scale all elements of the Screen accordingly
     *
     * @param screenHeight Height that the {@code Screen} will have
     */
    public Screen(int screenHeight){
        SCREEN_HEIGHT = screenHeight;
        setOpaque(false);
    }


    /**
     * Paints the Panel and adds a Settings button to the top-right corner of the screen.
     * <p>If overridden from a subclass, <b>call always super.paintComponent(g)</b> at the end of the method.
     * @param g The Graphics object to paint the Panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}

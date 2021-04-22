package presentation.view;

import presentation.graphics.MenuGraphics;

import javax.swing.*;
import java.awt.*;

/**
 * MenuBackgroundPanel is a JPanel that paints in itself a background image, depending on the {@code width} and {@code height} of the screen.
 *
 * <p>It will have a {@link BorderLayout} and subsequent added JPanels should have a transparent background.
 *
 * @see BorderLayout
 * @see JPanel#setOpaque(boolean)
 * @version 1.0
 */
public class MenuBackgroundPanel extends JPanel {

    private final int SCREEN_WIDTH, SCREEN_HEIGHT;

    private final int TILE_HORIZONTAL_SIZE = 90; //This is the horizontal size that fits best on the majority of the screens
    private final int TILE_VERTICAL_SIZE = TILE_HORIZONTAL_SIZE;
    private final int HORIZONTAL_TILES;
    private final int VERTICAL_TILES;

    /**
     * MenuBackgroundPanel constructor.
     * <p>Depending on the screen width and height, it will draw a larger or smaller background.
     * @param screenWidth The Width of the screen
     * @param screenHeight The Height of the screen
     */
    public MenuBackgroundPanel(int screenWidth, int screenHeight){
        SCREEN_WIDTH = screenWidth;
        SCREEN_HEIGHT = screenHeight;

        HORIZONTAL_TILES = (SCREEN_WIDTH / TILE_HORIZONTAL_SIZE) + 1; //Horizontal Tiles needed
        VERTICAL_TILES = (SCREEN_HEIGHT / TILE_VERTICAL_SIZE) + 1; //Vertical Tiles needed

        setBackground(MenuGraphics.RED);
        setLayout(new BorderLayout());
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundTile = MenuGraphics.getInstance().getBackgroundTile();
        if(backgroundTile == null) return;

        //Drawing each tile to the background depending on the vertical and horizontal tiles needed
        for(int verticalTile = 0; verticalTile < VERTICAL_TILES; verticalTile++){
            for(int horizontalTile = 0; horizontalTile < HORIZONTAL_TILES; horizontalTile++){
                g.drawImage(backgroundTile, horizontalTile*TILE_HORIZONTAL_SIZE, verticalTile*TILE_VERTICAL_SIZE, TILE_HORIZONTAL_SIZE, TILE_VERTICAL_SIZE, Color.WHITE, null);
            }
        }
    }

}

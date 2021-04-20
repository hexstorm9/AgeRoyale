package presentation.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class RoyaleScreen extends JPanel {

    private static final String BACKGROUND_TILE_IMAGE_PATH = "./resources/sprites/backgroundTile.png";
    private Image backgroundTile;

    private Color blue;
    private final int SCREEN_WIDTH, SCREEN_HEIGHT;

    private final int TILE_HORIZONTAL_SIZE = 90;
    private final int TILE_VERTICAL_SIZE = TILE_HORIZONTAL_SIZE;
    private final int HORIZONTAL_TILES;
    private final int VERTICAL_TILES;


    public RoyaleScreen(int screenWidth, int screenHeight){
        try{
            backgroundTile = ImageIO.read(new File(BACKGROUND_TILE_IMAGE_PATH));
        }catch(IOException e){}

        SCREEN_WIDTH = screenWidth;
        SCREEN_HEIGHT = screenHeight;

        HORIZONTAL_TILES = (SCREEN_WIDTH / TILE_HORIZONTAL_SIZE) + 1;
        VERTICAL_TILES = (SCREEN_HEIGHT / TILE_VERTICAL_SIZE) + 1;

        blue = new Color(0, 64, 163);
        setBackground(blue);

        setLayout(new BorderLayout());
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int verticalTile = 0; verticalTile < VERTICAL_TILES; verticalTile++){
            for(int horizontalTile = 0; horizontalTile < HORIZONTAL_TILES; horizontalTile++){
                g.drawImage(backgroundTile, horizontalTile*TILE_HORIZONTAL_SIZE, verticalTile*TILE_VERTICAL_SIZE, TILE_HORIZONTAL_SIZE, TILE_VERTICAL_SIZE, blue, null);
            }
        }
    }

}

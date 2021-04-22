package presentation.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * MenuGraphics is a {@code singleton} class that will have all the graphics (images, fonts, colors, etc.)
 * for drawing the menu.
 *
 * <p>Before using any objects of the class, {@link MenuGraphics#load()} shall be called. Otherwise, objects will be null.
 *
 * @see Image
 * @see Font
 * @see Color
 * @version 1.0
 */
public class MenuGraphics {

    public static final Color RED = new Color(200, 30, 30);
    public static final Color YELLOW = new Color(255, 183, 72);

    private Image backgroundTile, logo;
    private Font mainFont;

    private static final String BACKGROUND_TILE_IMAGE_PATH = "./resources/sprites/backgroundTile.png";
    private static final String LOGO_IMAGE_PATH = "./resources/sprites/logo.png";
    private static final String YOU_BLOCKHEAD_FONT_PATH = "./resources/fonts/YouBlockhead.ttf";

    private static MenuGraphics singletonInstance;


    private MenuGraphics(){}

    /**
     * Returns the singleton instance of this class.
     * @return MenuGraphics singleton instance
     */
    public static MenuGraphics getInstance(){
       if(singletonInstance == null) singletonInstance = new MenuGraphics();
       return singletonInstance;
    }

    /**
     * Loads all the resources into RAM to the singleton's attributes for later use.
     * @throws IOException Whenever a resource hasn't been found
     */
    public void load() throws IOException, FontFormatException{
        backgroundTile = ImageIO.read(new File(BACKGROUND_TILE_IMAGE_PATH));
        logo = ImageIO.read(new File(LOGO_IMAGE_PATH));

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(mainFont = Font.createFont(Font.TRUETYPE_FONT, new File(YOU_BLOCKHEAD_FONT_PATH)));
        mainFont = mainFont.deriveFont(16f);
        //TODO: Load other resources like fonts
    }


    /**
     * Returns the background tile for the menu
     * @return Background tile Image
     */
    public Image getBackgroundTile(){
        return backgroundTile;
    }

    /**
     * Returns the WarRoyale logo image
     * @return Logo image
     */
    public Image getLogo(){
        return logo;
    }


    public Font getMainFont(){
        return mainFont;
    }
}

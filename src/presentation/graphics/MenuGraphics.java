package presentation.graphics;

import javax.imageio.ImageIO;
import javax.tools.Tool;
import java.awt.*;
import java.awt.image.BufferedImage;
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

    private static MenuGraphics singletonInstance;

    public static final Color RED = new Color(200, 30, 30);
    public static final Color YELLOW = new Color(255, 183, 72);
    public static final Color BLUE = new Color(100, 146, 221);
    public static final Color LIGHT_GREY = new Color(239, 239, 239);

    private BufferedImage backgroundTile, logo, woodTable, settingsIcon;
    private BufferedImage englishFlag, catalanFlag, spanishFlag;
    private Font mainFont;

    private BufferedImage mainCursorImage, clickableCursorImage, writingCursorImage;
    private Cursor mainCursor, clickableCursor, writingCursor;


    private static final String BACKGROUND_TILE_IMAGE_PATH = "./resources/sprites/backgroundTile.png";
    private static final String LOGO_IMAGE_PATH = "./resources/sprites/logo.png";
    private static final String WOOD_TABLE_IMAGE_PATH = "./resources/sprites/woodTable.png";

    private static final String YOU_BLOCKHEAD_FONT_PATH = "./resources/fonts/YouBlockhead.ttf";

    private static final String SETTINGS_ICON_PATH = "./resources/sprites/settingsIcon.png";
    private static final String MAIN_CURSOR_ICON_PATH = "./resources/sprites/clickableCursor.png";
    private static final String CLICKABLE_CURSOR_ICON_PATH = "./resources/sprites/clickableCursor.png";
    private static final String WRITING_CURSOR_ICON_PATH = "./resources/sprites/customCursor.png";

    private static final String ENGLISH_FLAG_PATH = "./resources/sprites/englishFlag.png";
    private static final String SPANISH_FLAG_PATH = "./resources/sprites/spanishFlag.png";
    private static final String CATALAN_FLAG_PATH = "./resources/sprites/catalanFlag.png";


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
        settingsIcon = ImageIO.read(new File(SETTINGS_ICON_PATH));
        woodTable = ImageIO.read(new File(WOOD_TABLE_IMAGE_PATH));

        englishFlag = ImageIO.read(new File(ENGLISH_FLAG_PATH));
        spanishFlag = ImageIO.read(new File(SPANISH_FLAG_PATH));
        catalanFlag = ImageIO.read(new File(CATALAN_FLAG_PATH));

        mainCursorImage = ImageIO.read(new File(MAIN_CURSOR_ICON_PATH));
        clickableCursorImage = ImageIO.read(new File(CLICKABLE_CURSOR_ICON_PATH));
        writingCursorImage = ImageIO.read(new File(WRITING_CURSOR_ICON_PATH));

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(mainFont = Font.createFont(Font.TRUETYPE_FONT, new File(YOU_BLOCKHEAD_FONT_PATH)));
        mainFont = mainFont.deriveFont(16f);
        //TODO: Load other resources like fonts

        mainCursor = Toolkit.getDefaultToolkit().createCustomCursor(mainCursorImage, new Point(0, 0), "customCursor");
        clickableCursor = Toolkit.getDefaultToolkit().createCustomCursor(clickableCursorImage, new Point(0, 0), "customCursor");
        writingCursor = Toolkit.getDefaultToolkit().createCustomCursor(writingCursorImage, new Point(0, 0), "customCursor");
    }


    /**
     * Returns a new scaled {@link Image} with the same proportions of the image provided,
     * but with the height desired.
     * @param imgToScale Image that will be scaled
     * @param newHeight Height desired of the new image (in px)
     * @return New scaled image
     */
    public Image scaleImage(BufferedImage imgToScale, final int newHeight){
        final float proportions = (float)imgToScale.getWidth() / (float)imgToScale.getHeight();
        final int newWidth = (int)((float)newHeight * proportions);
        return imgToScale.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
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


    public BufferedImage getWoodTable(){
        return woodTable;
    }

    /**
     * Returns the main font of the application
     * @return Main font of the application
     */
    public Font getMainFont(){
        return mainFont;
    }


    public Image getSpanishFlag(){
        return spanishFlag;
    }

    public Image getEnglishFlag(){
        return englishFlag;
    }

    public Image getCatalanFlag(){
        return catalanFlag;
    }


    public Image getSettingsIcon(){
        return settingsIcon;
    }


    public Cursor getDefaultCursor(){
        return mainCursor;
    }

    public Cursor getClickableCursor(){
        return clickableCursor;
    }

    public Cursor getWritingCursor(){
        return writingCursor;
    }

}

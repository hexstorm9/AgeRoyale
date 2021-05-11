package presentation.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * MenuGraphics is a {@code static} class that will have all the graphics (images, fonts, colors, etc.) loaded
 * for drawing the menu.
 *
 * <p>To access them, use the public (static) getters provided
 *
 * <p>Before using any objects of the class, {@link MenuGraphics#load()} shall be called. Otherwise, all {@code getters} will return null.
 *
 *
 * @see Image
 * @see java.awt.image.BufferedImage
 * @see Font
 * @see Color
 * @version 1.0
 */
public class MenuGraphics {


    //Colors ------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------
    public static final Color RED = new Color(200, 30, 30);
    public static final Color YELLOW = new Color(255, 183, 72);
    public static final Color ORANGE = new Color(255, 165, 0);
    public static final Color BLUE = new Color(100, 146, 221);
    public static final Color LIGHT_GREY = new Color(200, 200, 200);
    //-------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------


    //References to Resources -------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------
    private static BufferedImage backgroundTile, logo, woodTable, woodTableLight, settingsIcon;
    private static BufferedImage englishFlag, catalanFlag, spanishFlag;
    private static BufferedImage cardTest;
    private static BufferedImage usernameLogo, crown, arenaGif, chest;
    private static Font mainFont;

    private static BufferedImage mainCursorImage, clickableCursorImage, writingCursorImage;
    private static Cursor mainCursor, clickableCursor, writingCursor;
    //-------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------


    //Paths -------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------
    private static final String BACKGROUND_TILE_IMAGE_PATH = "./resources/sprites/backgroundTile.png";
    private static final String LOGO_IMAGE_PATH = "./resources/sprites/logo.png";
    private static final String WOOD_TABLE_IMAGE_PATH = "./resources/sprites/woodTable.png";
    private static final String WOOD_TABLE_LIGHT_IMAGE_PATH = "./resources/sprites/woodTableLight.png";

    private static final String YOU_BLOCKHEAD_FONT_PATH = "./resources/fonts/YouBlockhead.ttf";

    private static final String SETTINGS_ICON_PATH = "./resources/sprites/settingsIcon.png";
    private static final String MAIN_CURSOR_ICON_PATH = "./resources/sprites/clickableCursor.png";
    private static final String CLICKABLE_CURSOR_ICON_PATH = "./resources/sprites/clickableCursor.png";
    private static final String WRITING_CURSOR_ICON_PATH = "./resources/sprites/customCursor.png";

    private static final String ENGLISH_FLAG_PATH = "./resources/sprites/englishFlag.png";
    private static final String SPANISH_FLAG_PATH = "./resources/sprites/spanishFlag.png";
    private static final String CATALAN_FLAG_PATH = "./resources/sprites/catalanFlag.png";

    private static final String CARD_TEST_PATH =  "./resources/sprites/cardTest.png";

    private static final String USERNAME_LOGO_PATH = "./resources/sprites/usernameLogo.png";
    private static final String CROWN_PATH = "./resources/sprites/crown.png";

    private static final String ARENA_GIF_PATH = "./resources/sprites/arena.png";
    private static final String CHEST_PATH = "./resources/sprites/chest.png";
    //-------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------



    /**
     * Loads all the resources into RAM so as to be able to get them later (with its getter methods).
     * @throws IOException Whenever a resource hasn't been found
     */
    public static void load() throws IOException, FontFormatException{
        //Reading Images -----------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------------------
        backgroundTile = ImageIO.read(new File(BACKGROUND_TILE_IMAGE_PATH));
        logo = ImageIO.read(new File(LOGO_IMAGE_PATH));
        settingsIcon = ImageIO.read(new File(SETTINGS_ICON_PATH));
        woodTable = ImageIO.read(new File(WOOD_TABLE_IMAGE_PATH));
        woodTableLight = ImageIO.read(new File(WOOD_TABLE_LIGHT_IMAGE_PATH));

        englishFlag = ImageIO.read(new File(ENGLISH_FLAG_PATH));
        spanishFlag = ImageIO.read(new File(SPANISH_FLAG_PATH));
        catalanFlag = ImageIO.read(new File(CATALAN_FLAG_PATH));

        cardTest = ImageIO.read(new File(CARD_TEST_PATH));

        mainCursorImage = ImageIO.read(new File(MAIN_CURSOR_ICON_PATH));
        clickableCursorImage = ImageIO.read(new File(CLICKABLE_CURSOR_ICON_PATH));
        writingCursorImage = ImageIO.read(new File(WRITING_CURSOR_ICON_PATH));

        usernameLogo = ImageIO.read(new File(USERNAME_LOGO_PATH));
        crown = ImageIO.read(new File(CROWN_PATH));

        arenaGif = ImageIO.read(new File(ARENA_GIF_PATH));
        chest = ImageIO.read(new File(CHEST_PATH));
        //-------------------------------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------------------


        //Loading Fonts------------------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------------------
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(mainFont = Font.createFont(Font.TRUETYPE_FONT, new File(YOU_BLOCKHEAD_FONT_PATH)));
        mainFont = mainFont.deriveFont(16f);
        //-------------------------------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------------------


        //Creating Cursors --------------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------------------
        mainCursor = Toolkit.getDefaultToolkit().createCustomCursor(mainCursorImage, new Point(0, 0), "customCursor");
        clickableCursor = Toolkit.getDefaultToolkit().createCustomCursor(clickableCursorImage, new Point(0, 0), "customCursor");
        writingCursor = Toolkit.getDefaultToolkit().createCustomCursor(writingCursorImage, new Point(0, 0), "customCursor");
        //-------------------------------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------------------
    }


    /**
     * Returns a new scaled {@link Image} with the same proportions of the image provided,
     * but with the height desired.
     * @param imgToScale Image that will be scaled
     * @param newHeight Height desired of the new image (in px)
     * @return New scaled image
     */
    public static Image scaleImage(BufferedImage imgToScale, final int newHeight){
        final float proportions = (float)imgToScale.getWidth() / (float)imgToScale.getHeight();
        final int newWidth = (int)((float)newHeight * proportions);
        return imgToScale.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
    }



    //Getters to all Images loaded -->
    //-------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------

    /**
     * Returns the background tile for the menu
     * @return Background tile Image
     */
    public static Image getBackgroundTile(){
        return backgroundTile;
    }


    /**
     * Returns the WarRoyale logo image
     * @return Logo image
     */
    public static BufferedImage getLogo(){
        return logo;
    }


    /**
     * Returns the WoodTable image in BufferedImage format
     * @return WoodTable image
     */
    public static BufferedImage getWoodTable(){
        return woodTable;
    }


    /**
     * Returns the WoodTableLight image in BufferedImage format
     * @return WoodTableLight image
     */
    public static BufferedImage getWoodTableLight(){
        return woodTableLight;
    }

    /**
     * Returns the main font of the application
     * @return Main font of the application
     */
    public static Font getMainFont(){
        return mainFont;
    }


    /**
     * Returns the Spanish Flag image
     * @return Spanish Flag image
     */
    public static BufferedImage getSpanishFlag(){
        return spanishFlag;
    }


    /**
     * Returns the English Flag image
     * @return English Flag image
     */
    public static BufferedImage getEnglishFlag(){
        return englishFlag;
    }

    /**
     * Returns the Catalan Flag image
     * @return Catalan Flag image
     */
    public static BufferedImage getCatalanFlag(){
        return catalanFlag;
    }


    /**
     * Returns the Settings icon
     * @return Settings Icon Image
     */
    public static BufferedImage getSettingsIcon(){
        return settingsIcon;
    }


    /**
     * Returns the Default Cursor of the application
     * @return Default cursor
     */
    public static Cursor getDefaultCursor(){
        return mainCursor;
    }

    /**
     * Returns the Clickable Cursor of the application
     * @return Clickable cursor
     */
    public static Cursor getClickableCursor(){
        return clickableCursor;
    }

    /**
     * Returns the Writing Cursor of the application
     * @return Writing cursor
     */
    public static Cursor getWritingCursor(){
        return writingCursor;
    }


    /**
     * Returns the Specified Card Image
     * @return Specified Card Image
     */
    public static BufferedImage getCardTest(){ return cardTest; }

    /**
     * Returns the Logo of the user
     * @return Logo of the user
     */
    public static BufferedImage getUsernameLogo() {
        return usernameLogo;
    }

    /**
     * Returns the Crowns Icon
     * @return Crowns Icon
     */
    public static BufferedImage getCrown() {
        return crown;
    }

    /**
     * Returns the Arena Gif specified
     * @param arena The arena number that we want
     * @return Specified Arena Gif
     */
    public static BufferedImage getArenaGif(int arena) {
        return arenaGif;
    }

    /**
     * Returns the Chest Image
     * @return Chest Image
     */
    public static BufferedImage getChest() {
        return chest;
    }
}

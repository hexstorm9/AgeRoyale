package presentation.graphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;


/**
 * BattleGraphics is a {@code static} class that will have all the graphics (images, fonts, colors, etc.) loaded
 * for drawing the {@link presentation.view.BattleScreen}.
 *
 * <p>To access them, use the public (static) getters provided
 *
 * <p>Before using any objects of the class, {@link BattleGraphics#load()} shall be called. Otherwise, all {@code getters} will return null.
 *
 *
 * @see Image
 * @see java.awt.image.BufferedImage
 * @version 1.0
 */
public class BattleGraphics {

    //References to Resources -------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------
    private static Image tile1a, tile1b, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9, tileA;

    //-------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------



    //Paths -------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------

    //-------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------



    /**
     * Loads all the resources into RAM so as to be able to get them later (with its getter methods).
     * @throws IOException Whenever a resource hasn't been found
     */
    public static void load() throws IOException{
        //Reading Images -----------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------------------
        try{
            tile1a = ImageIO.read(new File("./resources/sprites/mapTiles/1a.png"));
            tile1b = ImageIO.read(new File("./resources/sprites/mapTiles/1b.png"));
            tile2 = ImageIO.read(new File("./resources/sprites/mapTiles/2.png"));
            tile3 = ImageIO.read(new File("./resources/sprites/mapTiles/3.png"));
            tile4 = ImageIO.read(new File("./resources/sprites/mapTiles/4.png"));
            tile5 = ImageIO.read(new File("./resources/sprites/mapTiles/5.png"));
            tile6 = ImageIO.read(new File("./resources/sprites/mapTiles/6.png"));
            tile7 = ImageIO.read(new File("./resources/sprites/mapTiles/7.png"));
            tile8 = ImageIO.read(new File("./resources/sprites/mapTiles/8.png"));
            tile9 = ImageIO.read(new File("./resources/sprites/mapTiles/9.png"));
            tileA = ImageIO.read(new File("./resources/sprites/mapTiles/A.png"));
        }catch(IOException e){
            e.printStackTrace();
        }

        //-------------------------------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------------------
    }





    //Getters to all Images loaded -->
    //-------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------

    public static Image getMapTile(String tile){
        switch(tile){
            case "1": return new Random().nextBoolean() ? tile1a: tile1b;
            case "2": return tile2;
            case "3": return tile3;
            case "4": return tile4;
            case "5": return tile5;
            case "6": return tile6;
            case "7": return tile7;
            case "8": return tile8;
            case "9": return tile9;
            case "A": return tileA;
        }
        return null;
    }


}

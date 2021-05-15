package presentation.graphics;

import business.Card;
import business.entities.Cards;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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

    private static Image fence, fenceRightEnd, fenceLeftEnd;
    private static Image bridgeFence, bridgeUpperFence, bridgeRightEnd, bridgeLeftEnd, bridgeUpperRightEnd, bridgeUpperLeftEnd;
    private static Image flowers1, flowers2, flowers3, bush1, bush2;
    private static Image arrowSign, crossSign, deathSign;
    private static Image halfTree, stone, barrel, fireplace;

    private static Image tree1, tree2, tree3, tree4;

    private static Image[] carpiWalking;

    private static HashMap<Cards, Image> cardsImage;


    private static Image woodTile;
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
            woodTile = readImage("./resources/sprites/woodTile.png");

            tile1a = readImage("./resources/sprites/mapTiles/1a.png");
            tile1b = readImage("./resources/sprites/mapTiles/1b.png");
            tile2 = readImage("./resources/sprites/mapTiles/2.png");
            tile3 = readImage("./resources/sprites/mapTiles/3.png");
            tile4 = readImage("./resources/sprites/mapTiles/4.png");
            tile5 = readImage("./resources/sprites/mapTiles/5.png");
            tile6 = readImage("./resources/sprites/mapTiles/6.png");
            tile7 = readImage("./resources/sprites/mapTiles/7.png");
            tile8 = readImage("./resources/sprites/mapTiles/8.png");
            tile9 = readImage("./resources/sprites/mapTiles/9.png");
            tileA = readImage("./resources/sprites/mapTiles/A.png");

            fence = readImage("./resources/sprites/mapDecoration/fence.png");
            fenceRightEnd = readImage("./resources/sprites/mapDecoration/fenceRightEnd.png");
            fenceLeftEnd = readImage("./resources/sprites/mapDecoration/fenceLeftEnd.png");

            bridgeFence = readImage("./resources/sprites/mapDecoration/bridgeFence.png");
            bridgeUpperFence = readImage("./resources/sprites/mapDecoration/bridgeUpperFence.png");
            bridgeRightEnd = readImage("./resources/sprites/mapDecoration/bridgeRightEnd.png");
            bridgeLeftEnd = readImage("./resources/sprites/mapDecoration/bridgeLeftEnd.png");
            bridgeUpperRightEnd = readImage("./resources/sprites/mapDecoration/bridgeUpperRightEnd.png");
            bridgeUpperLeftEnd = readImage("./resources/sprites/mapDecoration/bridgeUpperLeftEnd.png");

            flowers1 = readImage("./resources/sprites/mapDecoration/flowers1.png");
            flowers2 = readImage("./resources/sprites/mapDecoration/flowers2.png");
            flowers3 = readImage("./resources/sprites/mapDecoration/flowers3.png");
            bush1 = readImage("./resources/sprites/mapDecoration/bush1.png");
            bush2 = readImage("./resources/sprites/mapDecoration/bush2.png");

            crossSign = readImage("./resources/sprites/mapDecoration/crossSign.png");
            deathSign = readImage("./resources/sprites/mapDecoration/deathSign.png");
            arrowSign = readImage("./resources/sprites/mapDecoration/arrowSign.png");


            barrel = readImage("./resources/sprites/mapDecoration/barrel.png");
            halfTree = readImage("./resources/sprites/mapDecoration/halfTree.png");
            stone = readImage("./resources/sprites/mapDecoration/stone.png");
            fireplace = readImage("./resources/sprites/mapDecoration/fireplace.png");

            tree1 = readImage("./resources/sprites/mapDecoration/tree1.png");
            tree2 = readImage("./resources/sprites/mapDecoration/tree2.png");
            tree3 = readImage("./resources/sprites/mapDecoration/tree3.png");
            tree4 = readImage("./resources/sprites/mapDecoration/tree4.png");

            carpiWalking = new Image[10];
            for(int i = 0; i < carpiWalking.length; i++){
                carpiWalking[i] = readImage("./resources/sprites/cards/carpi/walking" + i + ".png");
            }

            cardsImage = new HashMap<>();
            for(Cards c: Cards.values()){
                cardsImage.put(c, readImage("./resources/sprites/cardImages/" + c.toString() + ".png"));
            }

        }catch(IOException e){
            e.printStackTrace();
        }

        //-------------------------------------------------------------------------------------------------------
        //-------------------------------------------------------------------------------------------------------
    }





    //Getters to all Images loaded -->
    //-------------------------------------------------------------------------------------------------------
    //-------------------------------------------------------------------------------------------------------

    /**
     * Returns a Wood Tile
     * @return Wood Tile
     */
    public static Image getWoodTile(){
        return woodTile;
    }


    /**
     * Returns a tile image specified by the {@code char} introduced
     * @param tile The tile we want to be returned
     * @return A tile image that will depend on the {@code char} specified
     */
    public static Image getMapTile(char tile){
        switch(tile){
            case '1': return new Random().nextBoolean() ? tile1a: tile1b;
            case '2': return tile2;
            case '3': return tile3;
            case '4': return tile4;
            case '5': return tile5;
            case '6': return tile6;
            case '7': return tile7;
            case '8': return tile8;
            case '9': return tile9;
            case 'A': return tileA;
            default: return null;
        }
    }


    /**
     * Returns a decoration image specified by the {@code char} introduced
     * @param image The image we want to be returned
     * @return An image that will depend on the {@code char} specified
     */
    public static Image getDecorationImage(char image){
        switch(image){
            case 'F': return fence;
            case 'R': return fenceRightEnd;
            case 'L': return fenceLeftEnd;

            case 'A': return bridgeFence;
            case 'a': return bridgeUpperFence;
            case 'D': return bridgeRightEnd;
            case 'd': return bridgeUpperRightEnd;
            case 'E': return bridgeLeftEnd;
            case 'e': return bridgeUpperLeftEnd;

            case '1': return flowers1;
            case '2': return flowers2;
            case '3': return flowers3;
            case '5': return bush1;
            case '6': return bush2;

            case 'C': return crossSign;
            case 'Z': return deathSign;
            case 'z': return arrowSign;

            case 'S': return stone;
            case 'T': return halfTree;
            case 'B': return barrel;
            case 'b': return fireplace;

            default: return null;
        }

    }


    /**
     * Returns an outer map decoration image depending on the {@code int} provided.
     * <p>An outer map decoration image will always be 1:1
     * @param decoration The decoration that wants to be returned (from 0 to 3, there
     * are currently 4 different outer decorations).
     *
     * @return Decoration image depending on the parameter introduced
     */
    public static Image getOuterDecorationImage(int decoration){
        switch(decoration){
            case 0: return tree1;
            case 1: return tree2;
            case 2: return tree3;
            default: return tree4;
        }
    }


    /**
     * Returns, for every {@link business.Card.Orientation}, array of {@link Image} of the {@link Cards} provided, that will represent its {@link Card.State}
     *
     * @param card The card we want the images
     * @param state The state of that card
     * @param cardHeight The height of the images (images will be returned scaled)
     * @return For every {@link business.Card.Orientation}, an array of Images scaled, representing the {@code Cards} and {@code Card.State} provided
     */
    public static HashMap<Card.Orientation, Image[]> getSprites(Cards card, Card.State state, int cardHeight){
        HashMap<Card.Orientation, Image[]> hashMapSprites = new HashMap<>();

        Image[] rightSpritesToBeReturned; //The sprites to be returned looking at the Right
        Image[] leftSpritesToBeReturned; //The sprites to be returned looking at the Right

        /*switch(card){
            case CARPI:
                switch(state){
                }
        }*/

        rightSpritesToBeReturned = carpiWalking;

        //Scale images
        for(int i = 0; i < rightSpritesToBeReturned.length; i++)
            rightSpritesToBeReturned[i] = MenuGraphics.scaleImage(rightSpritesToBeReturned[i], cardHeight);

        //Save images to the Card.Orientation.RIGHT
        hashMapSprites.put(Card.Orientation.RIGHT, rightSpritesToBeReturned);


        //Fill the leftSpritesToBeReturned
        leftSpritesToBeReturned = new BufferedImage[rightSpritesToBeReturned.length];
        for(int i = 0; i < leftSpritesToBeReturned.length; i++)
            leftSpritesToBeReturned[i] = BattleGraphics.flipImageHorizontally(rightSpritesToBeReturned[i]);

        //Save images to the Card.Orientation.LEFT
        hashMapSprites.put(Card.Orientation.LEFT, leftSpritesToBeReturned);


        return hashMapSprites;
    }


    /**
     * Reads an image from the system provided a path
     * @param path Path of the image to read
     * @return A system compatible image from the specified path
     * @throws IOException When the path provided doesn't have any image
     */
    private static BufferedImage readImage(String path) throws IOException{
        BufferedImage img = ImageIO.read(new File(path));
        return toCompatibleImage(img);
    }


    /**
     * Returns a new image like the one provided but compatible if the provided was not.
     * @param image Image to be converted to compatible
     * @return The compatible image. Returns the same image if the provided image is already compatible
     */
    private static BufferedImage toCompatibleImage(BufferedImage image)
    {
        // obtain the current system graphical settings
        GraphicsConfiguration gfxConfig = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().
                getDefaultConfiguration();

        /*
         * if image is already compatible and optimized for current system
         * settings, simply return it
         */
        if (image.getColorModel().equals(gfxConfig.getColorModel()))
            return image;

        // image is not optimized, so create a new image that is
        BufferedImage newImage = gfxConfig.createCompatibleImage(
                image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = newImage.createGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // return the new optimized image
        return newImage;
    }


    /**
    * Returns a new image like the one provided but flipped horizontally
    * @param image Image to be flipped horizontally
    * @return The horizontally flipped image
    */
    private static Image flipImageHorizontally(Image img){
        BufferedImage image = (BufferedImage) img;

        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(-1, 1));
        at.concatenate(AffineTransform.getTranslateInstance(-image.getWidth(), 0));

        BufferedImage flippedImage = new BufferedImage(
                image.getWidth(), image.getHeight(),
                image.getType());

        Graphics2D g = flippedImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return flippedImage;
    }


    /**
     * Returns the desired Card Sprite (depending on the {@code Cards} provided)
     * @param c The card that indicates the sprite to return
     * @return The desired Card Sprite
     */
    public static Image getCardSprite(Cards c) {
        return cardsImage.get(c);
    }
}

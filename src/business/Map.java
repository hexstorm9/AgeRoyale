package business;

import presentation.graphics.BattleGraphics;
import presentation.graphics.MenuGraphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;


public class Map {

    private final int TILE_HEIGHT, TILE_WIDTH;
    private static final String[] mapTileInfo = {"11111111123111111111",
                                                 "11111111123111111111",
                                                 "165555555AA555555581",
                                                 "14111111123111111141",
                                                 "14111111123111111141",
                                                 "14111111123111111141",
                                                 "14111111123111111141",
                                                 "14111111123111111141",
                                                 "175555555AA555555591",
                                                 "11111111123111111111",
                                                 "11111111123111111111",
                                                 "11111111123111111111"};

    private static final String[] mapDecorationInfo = {"00305000000000001000",
                                                       "000000T0eaad00C00000",
                                                       "00000000EAAD00000000",
                                                       "00000000000000000000",
                                                       "0000b30600000T010000",
                                                       "00001000000B00000000",
                                                       "00000020000000S00000",
                                                       "00000z00eaadZ0000000",
                                                       "00000000EAAD00000000",
                                                       "00020003000000500320",
                                                       "000B1000000200003000",
                                                       "0LFFFFFFR00LFFFFFFR0"};

    private Image[][] mapTiles;
    private ArrayList<Tuple<Image, Vector2>> mapDecoration;
    private Dimension battlePanelDimension;

    private int firstTileColumnXPosition; //The map will be centered on the screen, so we'll need to calculate the firstTileColumnXPosition

    //Almost always the map on the battlePanel will not occupy it fully. The solid background will be seen in the right and left of the map.
    //That's why we'll have an "outer map". The user won't be able to put cards there, it only is for decoration purposes.
    //So we'll have an Array of outerMapTiles and another one of outerMapDecoration, initialized and filled on the constructor and painted when drawing the map
    private ArrayList<Tuple<Image, Vector2>> outerMapTiles;
    private ArrayList<Tuple<Image, Vector2>> outerMapDecoration;


    public Map(Dimension battlePanelDimension){
        this.battlePanelDimension = battlePanelDimension;

        final double exactTileHeight = (double)battlePanelDimension.height/mapTileInfo.length;
        TILE_HEIGHT = (int)Math.ceil(exactTileHeight); //Truncate to the closest higher integer
        TILE_WIDTH = TILE_HEIGHT;

        //Cache correctly scaled tiles in an array of images (mapTiles) for later faster use
        mapTiles = new Image[mapTileInfo.length][mapTileInfo[0].length()];
        for(int i = 0; i < mapTileInfo.length; i++){
            for(int j = 0; j < mapTileInfo[0].length(); j++){
                char tile = mapTileInfo[i].charAt(j);
                Image tileImg = BattleGraphics.getMapTile(tile);
                mapTiles[i][j] = MenuGraphics.scaleImage((BufferedImage)tileImg, TILE_HEIGHT);
            }
        }

        final int mapWidth = mapTileInfo[0].length() * TILE_WIDTH;
        firstTileColumnXPosition = (battlePanelDimension.width - mapWidth)/2;


        //Read mapDecorationInfo and extract values (scaled images and x,y image position) to the mapDecoration ArrayList
        mapDecoration = new ArrayList<>();
        for(int i = 0; i < mapDecorationInfo.length; i++){
            for(int j = 0; j < mapDecorationInfo[0].length(); j++){
                char currentDecoration = mapDecorationInfo[i].charAt(j);
                if(currentDecoration != '0'){ //If currentDecoration is '0', it means that there is no decoration in that point
                    Image decorationImg = BattleGraphics.getDecorationImage(currentDecoration);
                    Image scaledDecorationImg = MenuGraphics.scaleImage((BufferedImage)decorationImg, TILE_HEIGHT);
                    Vector2 decorationPosition = new Vector2(firstTileColumnXPosition + (j * TILE_WIDTH), i * TILE_HEIGHT);
                    mapDecoration.add(new Tuple<>(scaledDecorationImg, decorationPosition));
                }
            }
        }


        //Calculate outerMap tile information and put it into the outerMapTiles ArrayList
        outerMapTiles = new ArrayList<>();
        int necessaryOuterHorizontalTiles = ((battlePanelDimension.width - mapWidth) / TILE_WIDTH) + 2; //We add always 2 (one to the right and another to the left)

        Image tile = BattleGraphics.getMapTile('1');
        Image scaledTile = MenuGraphics.scaleImage((BufferedImage) tile, TILE_HEIGHT);
        for(int i = 0; i < mapTileInfo.length; i++){
            for(int j = 0; j < necessaryOuterHorizontalTiles/2; j++){ //Fill outer tiles to the right side of the map
                Vector2 tilePosition = new Vector2(firstTileColumnXPosition + mapWidth + (j * TILE_WIDTH), i * TILE_HEIGHT);
                outerMapTiles.add(new Tuple<>(scaledTile, tilePosition));
            }
            for(int j = 0; j < necessaryOuterHorizontalTiles/2; j++){ //Fill outer tiles to the left side of the map
                Vector2 tilePosition = new Vector2(firstTileColumnXPosition - TILE_WIDTH - (j * TILE_WIDTH), i * TILE_HEIGHT);
                outerMapTiles.add(new Tuple<>(tile, tilePosition));
            }
        }


        //Calculate outerMap decoration information and put it into the outerMapDecoration ArrayList
        //The outerMapDecoration will only have trees. We'll have 4 types of trees and they'll always occupy 4x4 tiles
        outerMapDecoration = new ArrayList<>();
        //if(necessaryOuterHorizontalTiles == 2) necessaryOuterHorizontalTiles = 4; //If we only have only one necessary tile to the right and one to the left,
                                                                                  // add another one to each side (so as to draw correctly the decorations)
        for(int i = 0; i < mapTileInfo.length; i++){
            for(int j = 0; j < necessaryOuterHorizontalTiles/2; j+=2){ //Fill outer decorations to the right side of the map
                Image decoration = BattleGraphics.getOuterDecorationImage(new Random().nextInt(4));
                Image scaledDecoration = MenuGraphics.scaleImage((BufferedImage) decoration, TILE_HEIGHT * 2);
                Vector2 decorationPosition = new Vector2(firstTileColumnXPosition + mapWidth + (j * TILE_WIDTH), i * TILE_HEIGHT);
                outerMapDecoration.add(new Tuple<>(scaledDecoration, decorationPosition));
            }
            for(int j = 0; j < necessaryOuterHorizontalTiles/2; j+=2){ //Fill outer decorations to the left side of the map
                Image decoration = BattleGraphics.getOuterDecorationImage(new Random().nextInt(4));
                Image scaledDecoration = MenuGraphics.scaleImage((BufferedImage) decoration, TILE_HEIGHT * 2);
                Vector2 decorationPosition = new Vector2(firstTileColumnXPosition - 2*TILE_WIDTH - (j * TILE_WIDTH), i * TILE_HEIGHT);
                outerMapDecoration.add(new Tuple<>(scaledDecoration, decorationPosition));
            }
        }
    }


    public void draw(Graphics g){

        //Drawing outer map tiles
        if(!outerMapTiles.isEmpty()) {
            for (Tuple<Image, Vector2> outerTile : outerMapTiles)
                g.drawImage(outerTile.firstField, outerTile.secondField.x, outerTile.secondField.y, null);
        }

        //Drawing outer map decoration
        if(!outerMapDecoration.isEmpty()){
            for(Tuple<Image, Vector2> outerDecoration: outerMapDecoration)
                g.drawImage(outerDecoration.firstField, outerDecoration.secondField.x, outerDecoration.secondField.y, null);
        }


        //Drawing map tiles
        for(int i = 0; i < mapTileInfo.length; i++) {
            for(int j = 0; j < mapTileInfo[0].length(); j++) {
                g.drawImage(mapTiles[i][j], firstTileColumnXPosition + (j * TILE_WIDTH), i * TILE_HEIGHT, null);
            }
        }


        //Drawing map decoration
        for(Tuple<Image, Vector2> decoration: mapDecoration)
            g.drawImage(decoration.firstField, decoration.secondField.x, decoration.secondField.y, null);
    }


}

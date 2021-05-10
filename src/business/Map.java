package business;

import presentation.graphics.BattleGraphics;
import presentation.graphics.MenuGraphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


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
                                                       "000B0003000000500320",
                                                       "0LFFFFFFR00LFFFFFFR0"};

    private Image[][] mapTiles;
    private ArrayList<Tuple<Image, Vector2>> mapDecoration;
    private Dimension battlePanelDimension;

    private int firstTileColumnXPosition; //The map will be centered on the screen, so we'll need to calculate the firstTileColumnXPosition



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

    }


    public void draw(Graphics g){
        for(int i = 0; i < battlePanelDimension.width; i++){
            for(int j = 0; j < battlePanelDimension.height; j++){
                //We'll draw here to the whole battlePanel, not the map but what's outside the map
                //g.drawImage(tilesMap[0][0], i * TILE_WIDTH, j * TILE_HEIGHT, null);
            }
        }

        for(int i = 0; i < mapTileInfo.length; i++){
            for(int j = 0; j < mapTileInfo[0].length(); j++){
                g.drawImage(mapTiles[i][j], firstTileColumnXPosition + (j * TILE_WIDTH), i * TILE_HEIGHT, null);
            }
        }


        for(Tuple<Image, Vector2> decoration: mapDecoration){
            g.drawImage(decoration.firstField, decoration.secondField.x, decoration.secondField.y, null);
        }
    }


}

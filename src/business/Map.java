package business;

import presentation.graphics.BattleGraphics;
import presentation.graphics.MenuGraphics;

import java.awt.*;
import java.awt.image.BufferedImage;

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

    private Image[][] tilesMap;
    private Dimension battlePanelDimension;

    private int firstTileColumnXPosition; //The map will be centered on the screen, so we'll need to calculate the firstTileColumnXPosition



    public Map(Dimension battlePanelDimension){
        this.battlePanelDimension = battlePanelDimension;

        final double exactTileHeight = (double)battlePanelDimension.height/mapTileInfo.length;
        TILE_HEIGHT = (int)Math.ceil(exactTileHeight); //Truncate to the closest higher integer
        TILE_WIDTH = TILE_HEIGHT;

        tilesMap = new Image[mapTileInfo.length][mapTileInfo[0].length()];
        for(int i = 0; i < mapTileInfo.length; i++){
            for(int j = 0; j < mapTileInfo[0].length(); j++){
                String tile = Character.toString(mapTileInfo[i].charAt(j));
                Image tileImg = BattleGraphics.getMapTile(tile);
                tilesMap[i][j] = MenuGraphics.scaleImage((BufferedImage)tileImg, TILE_HEIGHT);
            }
        }

        final int mapWidth = mapTileInfo[0].length() * TILE_WIDTH;
        firstTileColumnXPosition = (battlePanelDimension.width - mapWidth)/2;
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
                g.drawImage(tilesMap[i][j], firstTileColumnXPosition + (j * TILE_WIDTH), i * TILE_HEIGHT, null);
            }
        }
    }


}

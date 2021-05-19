package business;

import business.entities.Player;
import presentation.graphics.BattleGraphics;
import presentation.graphics.MenuGraphics;

import java.awt.*;
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
                mapTiles[i][j] = MenuGraphics.scaleImage(tileImg, TILE_HEIGHT);
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
                    Image scaledDecorationImg = MenuGraphics.scaleImage(decorationImg, TILE_HEIGHT);
                    Vector2 decorationPosition = new Vector2(firstTileColumnXPosition + (j * TILE_WIDTH), i * TILE_HEIGHT);
                    mapDecoration.add(new Tuple<>(scaledDecorationImg, decorationPosition));
                }
            }
        }


        //Calculate outerMap tile information and put it into the outerMapTiles ArrayList
        outerMapTiles = new ArrayList<>();
        int necessaryOuterHorizontalTiles = ((battlePanelDimension.width - mapWidth) / TILE_WIDTH) + 2; //We add always 2 (one to the right and another to the left)

        Image tile = BattleGraphics.getMapTile('1');
        Image scaledTile = MenuGraphics.scaleImage(tile, TILE_HEIGHT);
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
                Image scaledDecoration = MenuGraphics.scaleImage(decoration, TILE_HEIGHT * 2);
                Vector2 decorationPosition = new Vector2(firstTileColumnXPosition + mapWidth + (j * TILE_WIDTH), i * TILE_HEIGHT);
                outerMapDecoration.add(new Tuple<>(scaledDecoration, decorationPosition));
            }
            for(int j = 0; j < necessaryOuterHorizontalTiles/2; j+=2){ //Fill outer decorations to the left side of the map
                Image decoration = BattleGraphics.getOuterDecorationImage(new Random().nextInt(4));
                Image scaledDecoration = MenuGraphics.scaleImage(decoration, TILE_HEIGHT * 2);
                Vector2 decorationPosition = new Vector2(firstTileColumnXPosition - 2*TILE_WIDTH - (j * TILE_WIDTH), i * TILE_HEIGHT);
                outerMapDecoration.add(new Tuple<>(scaledDecoration, decorationPosition));
            }
        }
    }


    public void draw(Graphics g){

        //Drawing outer map tiles
        if(!outerMapTiles.isEmpty()) {
            for (Tuple<Image, Vector2> outerTile : outerMapTiles)
                g.drawImage(outerTile.firstField, (int)outerTile.secondField.x, (int)outerTile.secondField.y, null);
        }

        //Drawing outer map decoration
        if(!outerMapDecoration.isEmpty()){
            for(Tuple<Image, Vector2> outerDecoration: outerMapDecoration)
                g.drawImage(outerDecoration.firstField, (int)outerDecoration.secondField.x, (int)outerDecoration.secondField.y, null);
        }


        //Drawing map tiles
        for(int i = 0; i < mapTileInfo.length; i++) {
            for(int j = 0; j < mapTileInfo[0].length(); j++) {
                g.drawImage(mapTiles[i][j], firstTileColumnXPosition + (j * TILE_WIDTH), i * TILE_HEIGHT, null);
            }
        }


        //Drawing map decoration
        for(Tuple<Image, Vector2> decoration: mapDecoration)
            g.drawImage(decoration.firstField, (int)decoration.secondField.x, (int)decoration.secondField.y, null);
    }


    public Vector2 getTopLeftBridgeAccess(){
        int topLeftBridgeAccess = 1; //We want the first A
        return getBridgeAccess(topLeftBridgeAccess);
    }

    public Vector2 getTopRightBridgeAccess(){
        int topLeftBridgeAccess = 2; //We want the second A
        return getBridgeAccess(topLeftBridgeAccess);
    }

    public Vector2 getBottomLeftBridgeAccess(){
        int topLeftBridgeAccess = 3; //We want the third A
        return getBridgeAccess(topLeftBridgeAccess);
    }

    public Vector2 getBottomRightBridgeAccess(){
        int topLeftBridgeAccess = 4; //We want the fourth A
        return getBridgeAccess(topLeftBridgeAccess);
    }

    private Vector2 getBridgeAccess(int bridgeAccess){
        int bridgesCounter = 0;

        for(int i = 0; i < mapTileInfo.length; i++){
            for(int j = 0; j < mapTileInfo[i].length(); j++){
                if(mapTileInfo[i].charAt(j) == 'A'){
                    bridgesCounter++;
                    if(bridgesCounter == bridgeAccess){
                        final int x = j * TILE_WIDTH;
                        final int y = i * TILE_HEIGHT + TILE_HEIGHT/2;
                        return new Vector2(x + firstTileColumnXPosition, y);
                    }
                }
            }
        }

        return null;
    }


    public boolean isPositionOnTheLeftMap(Vector2 position){
        return position.x < firstTileColumnXPosition + (mapTileInfo[0].length()/2 - 1) * TILE_WIDTH
                && position.x > firstTileColumnXPosition;

    }

    public boolean isPositionOnTheRightMap(Vector2 position){
        return position.x > firstTileColumnXPosition + (mapTileInfo[0].length()/2 + 1) * TILE_WIDTH
                && position.x < firstTileColumnXPosition + mapTileInfo[0].length() * TILE_WIDTH;
    }


    /**
     * Returns where the tower should go depending on the {@link Card.Status} provided@link Card.Status} provided.
     * @param status The status of the tower we want the position for
     * @return The position of the tower
     *
     * @see Card.Status
     */
    public Vector2 getTowerPosition(Card.Status status){
        Vector2 towerPosition = new Vector2(0, 0);
        if(status == Card.Status.PLAYER){
            towerPosition.x = firstTileColumnXPosition + TILE_WIDTH/2;
            towerPosition.y = getTowerHeight() + (TILE_HEIGHT * 3);
        }
        else if(status  == Card.Status.ENEMY){
            towerPosition.x = firstTileColumnXPosition + ((TILE_WIDTH * (mapTileInfo[0].length() - 2)) - TILE_WIDTH/2);
            towerPosition.y = getTowerHeight() + (TILE_HEIGHT * 3);
        }

        return towerPosition;
    }



    /**
     * Returns the appropriate height of a Tower Card for the current map size
     * @return Appropriate tower card height
     */
    public int getTowerHeight(){
        //Card height will be the height of a tile + the height of a tile/2
        return TILE_HEIGHT * 4;
    }

    /**
     * Returns the appropriate height of a Card for the current map size
     * @return Appropriate card height
     */
    public int getCardHeight(){
        //Card height will be the height of a tile + the height of a tile/2
        return TILE_HEIGHT + TILE_HEIGHT/2;
    }
}

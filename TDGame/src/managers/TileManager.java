package managers;

import java.awt.image.BufferedImage;
import static helpz.Constants.Tiles.*;
import java.util.ArrayList;

import helpz.ImgFix;
import helpz.LoadSave;
import objects.Tile;

public class TileManager {
	public Tile GRASS;
	public Tile WATER,
	BL_WATER_CORNER, TL_WATER_CORNER, BR_WATER_CORNER, TR_WATER_CORNER,
	T_WATER, R_WATER, B_WATER, L_WATER,
	TL_ISLE, BR_ISLE, TR_ISLE, BL_ISLE;
	
	public Tile ROAD, ROAD_VERT, TL_ROAD_CORNER, TR_ROAD_CORNER, BR_ROAD_CORNER, BL_ROAD_CORNER;
	
	public BufferedImage atlas;
	public ArrayList<Tile> tiles = new ArrayList<>();
	
	public ArrayList<Tile> roadsS = new ArrayList<>();
	public ArrayList<Tile> roadsC = new ArrayList<>();
	public ArrayList<Tile> corners = new ArrayList<>();
	public ArrayList<Tile> beaches = new ArrayList<>();
	public ArrayList<Tile> islands = new ArrayList<>();
	

	public TileManager() 
	{
		loadAtlas();
		createTiles();
	}

	private void createTiles() {
		int id=0;
		
		
		//updated: instead of "" names of tiles we'll use typesof tiles
		//regular tiles grass and water
		tiles.add(GRASS = new Tile(getSprite(9,0), id++, GRASS_TILE));
		tiles.add(WATER = new Tile(getAniSprites(0,0),id++, WATER_TILE));
		
		//road based tiles
		//up and down
		roadsS.add(ROAD = new Tile(getSprite(8,0), id++, ROAD_TILE));
		roadsS.add(ROAD_VERT = new Tile(ImgFix.getRotImg(getSprite(8,0), 90), id++, ROAD_TILE));
		
		//corners
		roadsC.add(TL_ROAD_CORNER = new Tile(getSprite(7,0), id++, ROAD_TILE));
		roadsC.add(TR_ROAD_CORNER = new Tile(ImgFix.getRotImg(getSprite(7, 0), 90), id++, ROAD_TILE));
		roadsC.add(BR_ROAD_CORNER = new Tile(ImgFix.getRotImg(getSprite(7,0),180), id++, ROAD_TILE));
		roadsC.add(BL_ROAD_CORNER = new Tile(ImgFix.getRotImg(getSprite(7,0),270), id++, ROAD_TILE));

		
		//water based tiles
		//water corners
		
		corners.add(BL_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0,0), getSprite(5,0),0), id++, WATER_TILE));
		corners.add(TL_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0,0), getSprite(5,0),90), id++, WATER_TILE));
		corners.add(TR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0,0), getSprite(5,0),180), id++, WATER_TILE));
		corners.add(BR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0,0), getSprite(5,0),270), id++, WATER_TILE));		
		
//		corners.add(BL_WATER_CORNER = new Tile(ImgFix.buildImg(getImgs(0,0,5,0)), id++, "BL_Water_Corner"));
//		corners.add(TL_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,5,0), 90, 1), id++, "TL_Water_Corner"));
//		corners.add(TR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,5,0), 180, 1), id++, "TR_Water_Corner"));
//		corners.add(BR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,5,0), 270, 1), id++, "BR_Water_Corner"));

		beaches.add(T_WATER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0,0), getSprite(6,0),0), id++, WATER_TILE));
		beaches.add(R_WATER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0,0), getSprite(6,0),90), id++, WATER_TILE));
		beaches.add(B_WATER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0,0), getSprite(6,0),180), id++, WATER_TILE));
		beaches.add(L_WATER = new Tile(ImgFix.getBuildRotImg(getAniSprites(0,0), getSprite(6,0),270), id++, WATER_TILE));
		
//		beaches.add(T_WATER = new Tile(ImgFix.buildImg(getImgs(0,0,6,0)), id++, "T_Water"));
//		beaches.add(R_WATER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,6,0), 90, 1), id++, "R_WATER"));
//		beaches.add(B_WATER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,6,0), 180, 1), id++, "B_WATER"));
//		beaches.add(L_WATER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,6,0), 270, 1), id++, "L_WATER"));

		islands.add(TL_ISLE = new Tile(ImgFix.getBuildRotImg(getAniSprites(0,0), getSprite(4,0),0), id++, WATER_TILE));
		islands.add(TL_ISLE = new Tile(ImgFix.getBuildRotImg(getAniSprites(0,0), getSprite(4,0),90), id++, WATER_TILE));
		islands.add(TL_ISLE = new Tile(ImgFix.getBuildRotImg(getAniSprites(0,0), getSprite(4,0),180), id++, WATER_TILE));
		islands.add(TL_ISLE = new Tile(ImgFix.getBuildRotImg(getAniSprites(0,0), getSprite(4,0),270), id++, WATER_TILE));
		
//		islands.add(TL_ISLE = new Tile(ImgFix.buildImg(getImgs(0,0,4,0)), id++, "TL_Isle"));
//		islands.add(TL_ISLE = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,4,0), 90, 1), id++, "TR_Isle"));
//		islands.add(TL_ISLE = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,4,0), 180, 1), id++, "BR_Isle"));
//		islands.add(TL_ISLE = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,4,0), 270, 1), id++, "BL_Isle"));

		
		tiles.addAll(roadsS);
		tiles.addAll(roadsC);
		tiles.addAll(corners);
		tiles.addAll(beaches);
		tiles.addAll(islands);
		
		
	}

	private BufferedImage[] getImgs(int firstX, int firstY, int secondX, int secondY)
	{
		return new BufferedImage[] {getSprite(firstX, firstY), getSprite(secondX, secondY)};
	}
	
	private void loadAtlas() {
		atlas = LoadSave.getSpriteAtlas();
	}
	
	public Tile getTile(int id)
	{
		return tiles.get(id);
	}
	
	public BufferedImage getSprite(int id)
	{
		return tiles.get(id).getSprite();
	}
	
	//Be careful between this function and the one called "AniSprites"
	public BufferedImage getAniSprite(int id, int animationIndex)
	{
		return tiles.get(id).getSprite(animationIndex);
	}
	
	private BufferedImage getSprite(int xCord, int yCord)
	{
		return atlas.getSubimage(xCord*32, yCord*32, 32, 32);
	}

	//Be careful between this function and the one called "AniSprite"
	private BufferedImage[] getAniSprites(int xCord, int yCord) //stores the different sprite variants in a bufferedImage list
	//use this list to sort through and appear animated
	{
		BufferedImage[] arr = new BufferedImage[4];
		for(int i=0; i<4;i++)
		{
			arr[i] = getSprite(xCord+i,yCord);
		}
		return arr;
	}
	
	public boolean isSpriteAnimation(int spriteID)
	{
		return tiles.get(spriteID).isAnimation();
	}
	
	public ArrayList<Tile> getRoadsS() {
		return roadsS;
	}
	
	public ArrayList<Tile> getRoadsC() {
		return roadsC;
	}
	
	public ArrayList<Tile> getCorners() {
		return corners;
	}
	
	public ArrayList<Tile> getBeaches() {
		return beaches;
	}
	
	public ArrayList<Tile> getIslands() {
		return islands;
	}
}

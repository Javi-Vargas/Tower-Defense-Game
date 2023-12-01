package managers;

import java.awt.image.BufferedImage;
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
	
	public TileManager() 
	{
		loadAtlas();
		createTiles();
	}

	private void createTiles() {
		int id=0;
		
		//grass tiles
		tiles.add(GRASS = new Tile(getSprite(9,0), id++, "Grass"));
		
		//road based tiles
		tiles.add(ROAD = new Tile(getSprite(8,0), id++, "Road"));
		tiles.add(ROAD_VERT = new Tile(ImgFix.getRotImg(getSprite(8,0), 90), id++, "Road_Vert"));
		tiles.add(TL_ROAD_CORNER = new Tile(getSprite(7,0), id++, "TL_Road_Corner"));
		tiles.add(TR_ROAD_CORNER = new Tile(ImgFix.getRotImg(getSprite(7,0),90), id++, "TR_Road_Corner"));
		tiles.add(BR_ROAD_CORNER = new Tile(ImgFix.getRotImg(getSprite(7,0),180), id++, "BR_Road_Corner"));
		tiles.add(BL_ROAD_CORNER = new Tile(ImgFix.getRotImg(getSprite(7,0),270), id++, "BL_Road_Corner"));

		
		//water based tiles
		tiles.add(WATER = new Tile(getSprite(0,0),id++, "Water"));
		
		tiles.add(BL_WATER_CORNER = new Tile(ImgFix.buildImg(getImgs(0,0,5,0)), id++, "BL_Water_Corner"));
		tiles.add(TL_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,5,0), 90, 1), id++, "TL_Water_Corner"));
		tiles.add(BR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,5,0), 270, 1), id++, "BR_Water_Corner"));
		tiles.add(TR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,5,0), 180, 1), id++, "TR_Water_Corner"));
		
		tiles.add(T_WATER = new Tile(ImgFix.buildImg(getImgs(0,0,6,0)), id++, "T_Water"));
		tiles.add(R_WATER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,6,0), 90, 1), id++, "R_WATER"));
		tiles.add(B_WATER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,6,0), 180, 1), id++, "B_WATER"));
		tiles.add(L_WATER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,6,0), 270, 1), id++, "L_WATER"));
		
		tiles.add(TL_ISLE = new Tile(ImgFix.buildImg(getImgs(0,0,4,0)), id++, "TL_Isle"));
		tiles.add(TL_ISLE = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,4,0), 90, 1), id++, "TR_Isle"));
		tiles.add(TL_ISLE = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,4,0), 180, 1), id++, "BR_Isle"));
		tiles.add(TL_ISLE = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,4,0), 270, 1), id++, "BL_Isle"));

		
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
	
	private BufferedImage getSprite(int xCord, int yCord)
	{
		return atlas.getSubimage(xCord*32, yCord*32, 32, 32);
	}
}

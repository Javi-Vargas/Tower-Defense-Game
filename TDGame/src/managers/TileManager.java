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
		
		//regular tiles grass and water
		tiles.add(GRASS = new Tile(getSprite(9,0), id++, "Grass"));
		tiles.add(WATER = new Tile(getSprite(0,0),id++, "Water"));
		
		//road based tiles
		//up and down
		roadsS.add(ROAD = new Tile(getSprite(8,0), id++, "Road"));
		roadsS.add(ROAD_VERT = new Tile(ImgFix.getRotImg(getSprite(8,0), 90), id++, "Road_Vert"));
		
		//corners
		roadsC.add(TL_ROAD_CORNER = new Tile(getSprite(7,0), id++, "TL_Road_Corner"));
		roadsC.add(TR_ROAD_CORNER = new Tile(ImgFix.getRotImg(getSprite(7,0),90), id++, "TR_Road_Corner"));
		roadsC.add(BR_ROAD_CORNER = new Tile(ImgFix.getRotImg(getSprite(7,0),180), id++, "BR_Road_Corner"));
		roadsC.add(BL_ROAD_CORNER = new Tile(ImgFix.getRotImg(getSprite(7,0),270), id++, "BL_Road_Corner"));

		
		//water based tiles
		//water corners
		corners.add(BL_WATER_CORNER = new Tile(ImgFix.buildImg(getImgs(0,0,5,0)), id++, "BL_Water_Corner"));
		corners.add(TL_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,5,0), 90, 1), id++, "TL_Water_Corner"));
		corners.add(TR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,5,0), 180, 1), id++, "TR_Water_Corner"));
		corners.add(BR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,5,0), 270, 1), id++, "BR_Water_Corner"));
		
		beaches.add(T_WATER = new Tile(ImgFix.buildImg(getImgs(0,0,6,0)), id++, "T_Water"));
		beaches.add(R_WATER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,6,0), 90, 1), id++, "R_WATER"));
		beaches.add(B_WATER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,6,0), 180, 1), id++, "B_WATER"));
		beaches.add(L_WATER = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,6,0), 270, 1), id++, "L_WATER"));
		
		islands.add(TL_ISLE = new Tile(ImgFix.buildImg(getImgs(0,0,4,0)), id++, "TL_Isle"));
		islands.add(TL_ISLE = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,4,0), 90, 1), id++, "TR_Isle"));
		islands.add(TL_ISLE = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,4,0), 180, 1), id++, "BR_Isle"));
		islands.add(TL_ISLE = new Tile(ImgFix.getBuildRotImg(getImgs(0,0,4,0), 270, 1), id++, "BL_Isle"));

		
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
	
	private BufferedImage getSprite(int xCord, int yCord)
	{
		return atlas.getSubimage(xCord*32, yCord*32, 32, 32);
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

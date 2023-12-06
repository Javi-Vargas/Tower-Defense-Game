package scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import helpz.ImgFix;
import helpz.LoadSave;
import main.Game;
import managers.EnemyManager;
import managers.TileManager;
import objects.Tile;
import ui.ActionBar;
import ui.MyButton;

import static main.GameStates.*;

public class Playing extends GameScene implements SceneMethods{

	private int[][] lvl;
	private ActionBar bottomBar;
	private EnemyManager enemyManager;
	private int mouseX, mouseY;
	
	//private boolean drawSelect;	
	///private Tile selectedTile;
	//private int lastTileX, lastTileY, lastTileId;
	
	public Playing(Game game) {
		super(game);
		
		loadDefaultLevel();
		
		bottomBar = new ActionBar(0,640,640,100, this); //this is the location for the bottom 100 pixels for the bottom bar
		
		enemyManager = new EnemyManager(this);
	}

	private void loadDefaultLevel() {
		lvl = LoadSave.GetLevelData("new_level");
	}
	
	public void setLevel(int[][] lvl)
	{
		this.lvl = lvl;
	}
	
	public void update()
	{
		updateTick();
		enemyManager.update();
	}

	@Override
	public void render(Graphics g) {
		drawLevel(g);		
		bottomBar.draw(g);
		enemyManager.draw(g);
	}
	
	private void drawLevel(Graphics g)
	{
		for(int y=0; y<lvl.length;y++)
		{
			for(int x=0;x<lvl[y].length;x++)
			{
				int id = lvl[y][x];
				if(isAnimation(id))
				{
					g.drawImage(getSprite(id, animationIndex), x*32, y*32, null);
				}
				else
					g.drawImage(getSprite(id), x*32, y*32, null);
			}
		}
	}
	

	
	public int getTileType(int x, int y)
	{
		int xCord = x/32;
		int yCord = y/32;
		
		if(xCord <0 || yCord > 19)
		{
			return 0;
		}
		if(yCord <0 || xCord > 19)
		{
			return 0;
		}			//these checks are to account for enemies walking out of bounds
		
		int id = lvl[y/32][x/32];
		return game.getTileManager().getTile(id).getTileType();
	}

	@Override
	public void mouseClicked(int x, int y) {
		if(y>= 640)
		{
			bottomBar.mouseClicked(x, y);
		}
		else
			enemyManager.addEnemy(x, y);
	}


	@Override
	public void mouseMoved(int x, int y) {
		if(y>= 640)
		{
			bottomBar.mouseMoved(x, y);
			//drawSelect = false;
		}
		else
		{
			//drawSelect = true;
			mouseX=(x/32)*32;
			mouseY = (y/32)*32;	//makes the slected tile display snap onto the square so you can see where to place items later
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if(y>= 640)
		{
			bottomBar.mousePressed(x, y);
		}

	}

	@Override
	public void mouseReleased(int x, int y) {
		bottomBar.mouseReleased(x, y);
	}


	@Override
	public void mouseDragged(int x, int y) {
	}

}

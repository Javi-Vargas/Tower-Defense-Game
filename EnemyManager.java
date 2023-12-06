package managers;

import java.awt.Graphics;
import static helpz.Constants.Direction.*;
import static helpz.Constants.Tiles.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helpz.LoadSave;
import scenes.Playing;

public class EnemyManager {

	private Playing playing;
	private BufferedImage[] enemyImgs; //list to hold all our enemys
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private float speed = 0.5f;	//for enemy speed
	
	public EnemyManager(Playing playing)
	{
		this.playing = playing;
		enemyImgs = new BufferedImage[4]; //we'll have 4 enemies
		addEnemy(3*32,9*32);
		loadEnemyImgs();
	}
	
	private void loadEnemyImgs() {
		//this method loads the image once and remove it instead of accessing the atlas each time for an enemy
		//not a huge deal rn cuz we only have 4 enemies but when scaled can be a performance problem
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		enemyImgs[0] = atlas.getSubimage(0, 32, 32, 32);
		enemyImgs[1] = atlas.getSubimage(32*1, 32, 32, 32);
		enemyImgs[2] = atlas.getSubimage(32*2, 32, 32, 32);
		enemyImgs[3] = atlas.getSubimage(32*3, 32, 32, 32);
	}

	public void update()
	{
		//testEnemy.move(.5f, 0);
		for(Enemy e: enemies)
		{
			//check for enemy if next tile is road(pos, dir)
			if(isNextTileRoad(e))
			{
				//move enemy
				//setNewDirectionAndMove(e);
			}
		}
			
	}
	
	private boolean isNextTileRoad(Enemy e) {
		//e pos
		//e dir
		//tile at new possible position
		int newX = (int)(e.getX() + getSpeedAndWidth(e.getLastDir()));
		int newY = (int)(e.getY() + getSpeedAndHeight(e.getLastDir()));	//need to cast to int because we'll use whole numbers to check tiles

		if(getTileType(newX,newY) == ROAD_TILE)
		{	//keep moving in same direction
			e.move(speed, e.getLastDir());
			
		}
		else if(isAtEnd(e))
		{
			
		}
		else
		{
			//find new direction
			setNewDirectionAndMove(e);
		}	
		return false;
	}
	
	private void setNewDirectionAndMove(Enemy e) {
		int dir = e.getLastDir();
		
		//move into current tile till fully in it
		int xCord = (int) (e.getX() / 32);
		int yCord = (int) (e.getY() / 32);
		
		fixEnemyOffsetTile(e, dir, xCord, yCord);
		
		if(dir==LEFT || dir == RIGHT)
		{
			int newY = (int)(e.getY() + getSpeedAndHeight(UP));	//need to cast to int because we'll use whole numbers to check tiles
			if(getTileType((int) e.getX(), newY) == ROAD_TILE)
			{
				e.move(speed, UP);
			}
			else
			{
				e.move(speed, DOWN);
			}
		}
		else
		{
			int newX = (int)(e.getX() + getSpeedAndWidth(RIGHT));	//need to cast to int because we'll use whole numbers to check tiles
			if(getTileType(newX, (int) e.getY()) == ROAD_TILE)
			{
				e.move(speed, RIGHT);
			}
			else
			{
				e.move(speed, LEFT);
			}
		}
	}

	private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
		switch(dir)
		{
//		case LEFT: 
//			if(xCord > 0)
//				xCord--;
//			break;
//		case UP: 
//			if(yCord > 0)
//				yCord--;
//			break;
		//dont need the left and up adjustments 
		case RIGHT:
			if (xCord < 19)
				xCord++;
			break;
		case DOWN:
			if (yCord < 19)
				yCord++;
			break;	
		}
		
		e.setPos(xCord * 32, yCord * 32);
	}

	private boolean isAtEnd(Enemy e) {
		// TODO Auto-generated method stub
		return false;
	}

	private int getTileType(int x, int y)
	{
		return playing.getTileType(x, y);
	}

	private float getSpeedAndHeight(int dir) {
		if (dir == UP)
			return -speed;
		else if (dir == DOWN)
			return speed + 32;

		return 0;
	}

	private float getSpeedAndWidth(int dir) {
		if (dir == LEFT)
			return -speed;
		else if (dir == RIGHT)
			return speed + 32;

		return 0;
	}

	public void addEnemy(int x, int y)
	{
		enemies.add(new Enemy(x, y, 0, 0));	//create an enemy in the map		
	}
	
	public void draw(Graphics g) {
		for(Enemy e: enemies)	
			drawEnemy(e, g);
	}
	
	private void drawEnemy(Enemy e, Graphics g) {
		g.drawImage(enemyImgs[0], (int) e.getX(), (int) e.getY(), null);
	}
}

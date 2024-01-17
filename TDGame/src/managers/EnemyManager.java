package managers;

import java.awt.Color;
import java.awt.Graphics;
import static helpz.Constants.Enemies.*;
import static helpz.Constants.Direction.*;
import static helpz.Constants.Tiles.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Bat;
import enemies.Enemy;
import enemies.Knight;
import enemies.Orc;
import enemies.Wolf;
import helpz.LoadSave;
import objects.PathPoint;
import scenes.Playing;

public class EnemyManager {

	private Playing playing;
	private BufferedImage[] enemyImgs; //list to hold all our enemys
	private ArrayList<Enemy> enemies = new ArrayList<>();
	//private float speed = 0.5f;	//for enemy speed
	private PathPoint start,end;
	private int HPbarWidth = 20;
	private BufferedImage slowEffect;

	public EnemyManager(Playing playing, PathPoint start, PathPoint end)
	{
		this.playing = playing;
		enemyImgs = new BufferedImage[4]; //we'll have 4 enemies
		this.start = start;
		this.end = end;	// this updates the previous method of adding enemies to where we already provide where its gonna be 
		
		loadEffectImg();
//		addEnemy(ORC);
//		addEnemy(BAT);
//		addEnemy(KNIGHT);
//		addEnemy(WOLF);
		loadEnemyImgs();
	}
	
	public void update()
	{		
		for(Enemy e: enemies)
		{
			if(e.isAlive())
			{
				updateEnemyMove(e);
			}	
		}		
	}
	
	private void loadEffectImg() {
		slowEffect = LoadSave.getSpriteAtlas().getSubimage(32*9, 32*2, 32, 32);
	}

	public void draw(Graphics g) {
		for(Enemy e: enemies)
		{
			if(e.isAlive())
			{
				drawEnemy(e, g);
				drawHealthBar(e,g);
				drawEffects(e,g);
			}
		}
	}
	
	private void drawEffects(Enemy e, Graphics g) {
		if(e.isSlowed())
		{
			g.drawImage(slowEffect, (int)e.getX(), (int)e.getY(), null);
		}
	}

	private void loadEnemyImgs() {
		//this method loads the image once and remove it instead of accessing the atlas each time for an enemy
		//not a huge deal rn cuz we only have 4 enemies but when scaled can be a performance problem
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		for(int i=0; i<4;i++)
			enemyImgs[i] = atlas.getSubimage(i*32, 32, 32, 32);
	}
	
	private void updateEnemyMove(Enemy e) {
		if(e.getLastDir() == -1)
		{
			setNewDirectionAndMove(e);
		}
	
		//tile at new possible position
		int newX = (int)(e.getX() + getSpeedAndWidth(e.getLastDir(), e.getEnemyType()));
		int newY = (int)(e.getY() + getSpeedAndHeight(e.getLastDir(), e.getEnemyType()));	//need to cast to int because we'll use whole numbers to check tiles

		if(getTileType(newX,newY) == ROAD_TILE)
		{	//keep moving in same direction
			e.move(GetSpeed(e.getEnemyType()), e.getLastDir()); //the first arg in e.move is GetSpeed method from the Constants class.
			
		}
		else if(isAtEnd(e))
		{
			e.kill();
			playing.removeOneLife();
//			System.out.println("A life is lost!");
		}
		else
		{
			setNewDirectionAndMove(e);
		}
	}
	
	private void setNewDirectionAndMove(Enemy e) {
		int dir = e.getLastDir();
		
		//move into current tile till fully in it
		int xCord = (int) (e.getX() / 32);
		int yCord = (int) (e.getY() / 32);
		
		fixEnemyOffsetTile(e, dir, xCord, yCord);
		
		if(isAtEnd(e))
		{
			return;
		}
		
		if(dir==LEFT || dir == RIGHT)
		{
			int newY = (int)(e.getY() + getSpeedAndHeight(UP, e.getEnemyType()));	//need to cast to int because we'll use whole numbers to check tiles
			if(getTileType((int) e.getX(), newY) == ROAD_TILE)
			{
				e.move(GetSpeed(e.getEnemyType()), UP);
			}
			else
			{
				e.move(GetSpeed(e.getEnemyType()), DOWN);
			}
		}
		else
		{
			int newX = (int)(e.getX() + getSpeedAndWidth(RIGHT, e.getEnemyType()));	//need to cast to int because we'll use whole numbers to check tiles
			if(getTileType(newX, (int) e.getY()) == ROAD_TILE)
			{
				e.move(GetSpeed(e.getEnemyType()), RIGHT);
			}
			else
			{
				e.move(GetSpeed(e.getEnemyType()), LEFT);
			}
		}
	}

	private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
		switch(dir)
		{
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
		if(e.getX() == end.getxCord() * 32)
			if(e.getY() == end.getyCord()* 32)
				return true;
				
		return false;
	}

	private int getTileType(int x, int y)
	{
		return playing.getTileType(x, y);
	}

	private float getSpeedAndHeight(int dir, int enemyType) {
		if (dir == UP)
			return -GetSpeed(enemyType);
		else if (dir == DOWN)
			return GetSpeed(enemyType) + 32;

		return 0;
	}

	private float getSpeedAndWidth(int dir, int enemyType) {
		if (dir == LEFT)
			return -GetSpeed(enemyType);
		else if (dir == RIGHT)
			return GetSpeed(enemyType) + 32;

		return 0;
	}
	
	public void addEnemy(int enemyType)
	{//create an enemy in the map	
		
		int x = start.getxCord()*32;
		int y = start.getyCord()*32;
		
		switch(enemyType)
		{
		case ORC:
			enemies.add(new Orc(x, y, 0, this));		
			break;
		case BAT:
			enemies.add(new Bat(x, y, 0, this));		
			break;
		case KNIGHT:
			enemies.add(new Knight(x, y, 0, this));		
			break;
		case WOLF:
			enemies.add(new Wolf(x, y, 0, this));		
			break;
			
		}
	}
	
	public void spawnEnemy(int nextEnemy) {
		addEnemy(nextEnemy);
	}
	
	private void drawHealthBar(Enemy e, Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)e.getX()+16-(getNewBarWidth(e)/2), (int)e.getY()-10, getNewBarWidth(e), 3);
	}
	
	private int getNewBarWidth(Enemy e)	//method to update the healthbar as it takes dmg
	{
		return (int) (HPbarWidth * e.getHealthBarFloat());
	}

	private void drawEnemy(Enemy e, Graphics g) {
		g.drawImage(enemyImgs[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
	}
	
	public ArrayList<Enemy> getEnemies()
	{
		return enemies;
	}

	public int getAmountOfAliveEnemies() {
		int size = 0;
		for(Enemy e: enemies)
			if(e.isAlive())
				size++;
		return size;
	}

	public void rewardPlayer(int enemyType) {
		playing.rewardPlayer(enemyType);
	}
	
	public void reset()
	{
		enemies.clear();
	}
}

package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helpz.LoadSave;
import scenes.Playing;

public class EnemyManager {

	private Playing playing;
	private BufferedImage[] enemyImgs; //list to hold all our enemys
	private Enemy testEnemy;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	
	public EnemyManager(Playing playing)
	{
		this.playing = playing;
		enemyImgs = new BufferedImage[4]; //we'll have 4 enemies
		//enemies.add(new Enemy(32*3, 32*9,0,0));
		//testEnemy = new Enemy(32*3, 32*9,0,0);
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
			e.move(.5f,0);
	}
	
	public void addEnemy(int x, int y)
	{
		enemies.add(new Enemy(x,y,0,0));	//create an enemy in the map		
	}
	
	public void draw(Graphics g) {
		for(Enemy e: enemies)	
			drawEnemy(e, g);
	}
	
	private void drawEnemy(Enemy e, Graphics g) {
		g.drawImage(enemyImgs[0], (int)e.getX(), (int)e.getY(), null);
	}
}

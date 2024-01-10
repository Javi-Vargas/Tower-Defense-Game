package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import helpz.LoadSave;
import objects.Tower;
import scenes.Playing;

import static helpz.Constants.Towers.*;

//tower manager to give them functionality and control their behavior
public class TowerManager {

	private Playing playing;
	private BufferedImage[] towerImgs;
	private Tower tower;
	
	public TowerManager(Playing playing)
	{
		this.playing = playing;
		
		loadTowerImgs();
		initTowers();
	}

	private void initTowers() {
		tower = new Tower(3*32, 6*32, 0, ARCHER);
	}

	private void loadTowerImgs() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		
		towerImgs = new BufferedImage[3];
		//we need to load the 'towers' based off their position in the atlas file
		for(int i=0; i< 3; i++)
		{
			towerImgs[i] = atlas.getSubimage((4+i) * 32, 32, 32, 32);
			//this (4+1) comes from the first 4 in the atlas being the enemies so we need to look after those for the tower
		}
	}
	
	public void update()
	{
		
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(towerImgs[ARCHER], tower.getX(), tower.getY(), null);
	}
}

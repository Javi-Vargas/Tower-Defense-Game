package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helpz.LoadSave;
import objects.Tower;
import scenes.Playing;


//tower manager to give them functionality and control their behavior
public class TowerManager {

	private Playing playing;
	private BufferedImage[] towerImgs;
	private ArrayList<Tower> towers = new ArrayList<>();
	private int towerAmount = 0;
	
	public TowerManager(Playing playing)
	{
		this.playing = playing;
		loadTowerImgs();
	}

	private void loadTowerImgs() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		towerImgs = new BufferedImage[3];	//we need to load the 'towers' based off their position in the atlas file
		for(int i=0; i< 3; i++)
		{
			towerImgs[i] = atlas.getSubimage((4+i) * 32, 32, 32, 32);//this (4+1) comes from the first 4 in the atlas being the enemies so we need to look after those for the tower
		}
	}
	
	public void addTower(Tower selectedTower, int xPos, int yPos) {
		towers.add(new Tower(xPos, yPos, towerAmount++, selectedTower.getTowerType()));
	}
	
	public void update()
	{
		for(Tower t: towers)
		{
			t.update();
			attackEnemyIfClose(t);
		}
	}
	
	private void attackEnemyIfClose(Tower t) {
			for(Enemy e: playing.getEnemyManager().getEnemies())
			{
				if(e.isAlive())
				{
					if(isEnemyInRange(t,e))
					{	//if in range shoot enemy
						if(t.isCoolDownOver())
						{
							playing.shootEnemy(t, e);
							t.resetCooldown();
						}
						//playing.shootEnemy(t,e);
					}
					else	//dont shoot enemies
					{
						
					}					
				}
			}
	}

	private boolean isEnemyInRange(Tower t, Enemy e) {
		int range = helpz.Utilz.GetHypoDistnace(t.getX(), t.getY(), e.getX(), e.getY());
		return (range < t.getRange());
	}

	public BufferedImage[] getTowerImgs()
	{
		return towerImgs;
	}
	
	public void draw(Graphics g)
	{
		for (Tower t : towers)
			g.drawImage(towerImgs[t.getTowerType()], t.getX(), t.getY(), null);
		//g.drawImage(towerImgs[ARCHER], tower.getX(), tower.getY(), null);
	}

	public Tower getTowerAt(int x, int y) {
		for(Tower t: towers)
			if(t.getX() == x)
				if(t.getY() == y)
					return t;
		return null;
	}

	public void removeTower(Tower displayedTower) {
		for(int i=0;i<towers.size();i++)
			if(towers.get(i).getId() == displayedTower.getId())
			{
				towers.remove(i);
			}
				
	}

	public void upgradeTower(Tower displayedTower) {
		for(Tower t: towers)
			if(t.getId() == displayedTower.getId())
				t.upgradeTower();
	}
	
	public void reset()
	{
		towers.clear();
		towerAmount = 0;
	}

}

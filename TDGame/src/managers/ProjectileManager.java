package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helpz.LoadSave;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;
import static helpz.Constants.Towers.*;
import static helpz.Constants.Projectiles.*;


public class ProjectileManager {

	private Playing playing;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private BufferedImage[] proj_imgs;
	private int proj_id = 0;
	
	public ProjectileManager(Playing playing) {
		this.playing = playing;
		importImgs();
	}
	
	private void importImgs()
	{	//going off the atlas projectiles in second row and tiles 7,8,9 aka x:7,8,9 & y:2
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		proj_imgs = new BufferedImage[3];
		for(int i=0; i< 3; i++)
		{
			proj_imgs[i] = atlas.getSubimage((7+i)*32, 32, 32, 32);
		}
	}
	
	
	public void newProjectile(Tower t, Enemy e)
	{
		int type = getProjType(t);
		
		int xDist = (int)Math.abs(t.getX() - e.getX());
		int yDist = (int)Math.abs(t.getY() - e.getY());
		
		int totDist = xDist + yDist;
		
		float xPercent = (float) xDist / totDist;

		float xSpeed = xPercent * helpz.Constants.Projectiles.GetSpeed(type);
		float ySpeed = helpz.Constants.Projectiles.GetSpeed(type) - xSpeed;
		
		if(t.getX() > e.getX()) //making it so projectiles can shoot in the geometrical 'left' and 'up' directions
			xSpeed *= -1;
		if(t.getY() > e.getY())
			ySpeed *= -1;
		
		projectiles.add(new Projectile(t.getX()+16, t.getY()+16, xSpeed, ySpeed, t.getDmg(), proj_id++, type));
		
	}
	
	private int getProjType(Tower t) {
		switch(t.getTowerType())
		{
		case ARCHER:
			return ARROW;
		case CANNON:
			return BOMB;
		case WIZARD:
			return CHAINS;
		}
		return 0;
	}

	public void draw(Graphics g)
	{
		for(Projectile p : projectiles)
			if(p.isActive())	
				g.drawImage(proj_imgs[p.getProjectileType()], (int)p.getPos().x, (int)p.getPos().y, null);
	}
	
	public void update()
	{
		for(Projectile p : projectiles)
			if(p.isActive())
			{
				p.move();
				if(isProjHittingEnemy(p))
				{
					p.setActive(false);
				}
				else
				{
					
				}
					
			}
	}

	private boolean isProjHittingEnemy(Projectile p) {
		for(Enemy e: playing.getEnemyManager().getEnemies()) {
			if(e.getBounds().contains(p.getPos()))
			{
				e.hurt(p.getDmg());
				return true;
			}
		}
			
		return false;
	}
}

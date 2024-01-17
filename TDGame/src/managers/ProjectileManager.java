package managers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
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
	private BufferedImage[] proj_imgs, explo_imgs;
	private ArrayList<Explosion> explosions = new ArrayList<>();
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
		ImportExplosion(atlas);
	}
	
	
	private void ImportExplosion(BufferedImage atlas) {
		explo_imgs = new BufferedImage[7];
		
		for(int i=0; i<7;i++)
		{
			explo_imgs[i] = atlas.getSubimage(i*32, 32*2, 32, 32);
		}
	}

	public void newProjectile(Tower t, Enemy e)
	{
		int type = getProjType(t);
		
		int xDist = (int)(t.getX() - e.getX());
		int yDist = (int)(t.getY() - e.getY());
		
		int totDist = Math.abs(xDist) + Math.abs(yDist);
		
		float xPercent = (float) Math.abs(xDist) / totDist;

		float xSpeed = xPercent * helpz.Constants.Projectiles.GetSpeed(type);
		float ySpeed = helpz.Constants.Projectiles.GetSpeed(type) - xSpeed;
		
		if(t.getX() > e.getX()) //making it so projectiles can shoot in the geometrical 'left' and 'up' directions
			xSpeed *= -1;
		if(t.getY() > e.getY())
			ySpeed *= -1;
		
		float rotate = 0;
		
		if(type == ARROW)
		{
			float arcValue = (float)Math.atan(yDist / (float)xDist);
			rotate = (float)Math.toDegrees(arcValue);
		
			if(xDist < 0)
				rotate += 180;
		}

		
		projectiles.add(new Projectile(t.getX()+16, t.getY()+16, xSpeed, ySpeed, t.getDmg(), rotate, proj_id++, type));
		
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
	
	public class Explosion
	{
		private Point2D.Float pos;
		private int exploTick=0, exploIndex=0;
		public Explosion(Point2D.Float pos)
		{
			this.pos = pos;
		}
		
		public void update() {
				exploTick++;
				if(exploTick >= 12)
				{
					exploTick = 0;
					exploIndex++;
				}
		}
		
		public Point2D.Float getPos()
		{
			return pos;
		}
		
		public int getIndex()
		{
			return exploIndex;
		}
	}

	public void draw(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
			
		for(Projectile p : projectiles)	//add projectile rotation to be more realistic
			if(p.isActive()) {
				if(p.getProjectileType() == ARROW)
				{					
					g2d.translate(p.getPos().x, p.getPos().y);
					g2d.rotate(Math.toRadians(p.getRotation()));
					g2d.drawImage(proj_imgs[p.getProjectileType()], -16, -16, null);
					g2d.rotate(-Math.toRadians(p.getRotation()));
					g2d.translate(-p.getPos().x, -p.getPos().y);
				}
				else
					g2d.drawImage(proj_imgs[p.getProjectileType()],(int)p.getPos().x-16, (int)p.getPos().y-16, null);
			}
		
		drawExplosions(g2d);
		
	}
	
	private void drawExplosions(Graphics2D g2d) {
		for(Explosion ex: explosions)
			if(ex.getIndex() < 7)
			{
				g2d.drawImage(explo_imgs[ex.getIndex()], (int)ex.getPos().x-16, (int)ex.getPos().y-16, null);
			}
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
					if(p.getProjectileType() == BOMB)
						explosions.add(new Explosion(p.getPos()));
						explodeOnEnemies(p);
				}
				else if(isProjectileOutOfBounds(p))
				{
					p.setActive(false);
				}			
			}
		
		for(Explosion ex: explosions)
			if(ex.getIndex() < 7)
				ex.update();
	}

	private boolean isProjectileOutOfBounds(Projectile p) {
		if(p.getPos().x >= 0)
			if(p.getPos().x <=640)
				if(p.getPos().y >=0)
					if(p.getPos().y <= 800)
						return false;
		return true;
	}

	private void explodeOnEnemies(Projectile p) {
		for(Enemy e: playing.getEnemyManager().getEnemies())
		{
			if(e.isAlive())
			{
				float radius = 40.0f;
				float xDist = Math.abs(p.getPos().x - e.getX());
				float yDist = Math.abs(p.getPos().y - e.getY());
				
				float realDistance = (float) Math.hypot(xDist, yDist); //aka hypotenuse
				
				if(realDistance <= radius)
					e.hurt(p.getDmg());
			}
		}
	}

	private boolean isProjHittingEnemy(Projectile p) {
		for(Enemy e: playing.getEnemyManager().getEnemies()) {
			if(e.isAlive())
			{				
				if(e.getBounds().contains(p.getPos()))
				{
					e.hurt(p.getDmg());
					if(p.getProjectileType() == CHAINS)
					{
						e.slow();
					}
					return true;
				}
			}
		}
			
		return false;
	}
	
	public void reset()
	{
		projectiles.clear();
		explosions.clear();
		
		proj_id = 0;
	}
}

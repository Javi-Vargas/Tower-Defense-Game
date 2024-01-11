package enemies;

import java.awt.Rectangle;
import static helpz.Constants.Direction.*;

//we make the Enemies class an 'Abstract class'
//this is so that we cannot create enemy from enemy class only from orc, wolf,bat, etc
public abstract class Enemy {

	protected float x, y;
	protected Rectangle bounds;
	protected int health;
	protected int ID;
	protected int maxHealth;
	protected int enemyType;
	protected int lastDir;
	
	//can Pick up at 4min in the video episode 14
	public Enemy(float x, float y, int ID, int enemyType)
	{
		this.x=x;
		this.y =y;
		this.ID = ID;
		this.enemyType = enemyType;
		bounds = new Rectangle((int)x, (int)y, 32,32);
		lastDir = -1;
		setStartHealth();
	}
	
	private void setStartHealth()
	{
		health = helpz.Constants.Enemies.getStartHealth(enemyType);
		maxHealth = health;
	}
	
	public void move(float speed, int dir)
	{
		lastDir = dir;
		switch(dir)
		{
		case LEFT:
			this.x -= speed;
			break;
		case UP:
			this.y -= speed;
			break;
		case RIGHT:
			this.x += speed;
			break;
		case DOWN:
			this.y += speed;
			break;
		}
	}
	
	public void setPos(int x, int y)
	{
		//dont use this one for movement. It is for posFix
		this.x =x;
		this.y = y;
	}
	
	public float getHealthBarFloat()
	{
		return health/(float)maxHealth;	//cast to float just to ensure smooth division
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public int getHealth() {
		return health;
	}

	public int getID() {
		return ID;
	}

	public int getEnemyType() {
		return enemyType;
	}
	
	public int getLastDir() {
		return lastDir;
	}
	
	
}

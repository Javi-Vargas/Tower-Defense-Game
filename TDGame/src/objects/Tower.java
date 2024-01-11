package objects;

//created tower object class
public class Tower {
	
	private int x,y,id,towerType;
	private float dmg, range, cooldown;
	public Tower(int x, int y, int id, int towerType)
	{
		this.x=x;
		this.y=y;
		this.id = id;
		this.towerType = towerType;
		setDefaultDmg();
		setDefaultRange();
		setDefaultCooldown();
	}

	private void setDefaultDmg() {
		dmg = helpz.Constants.Towers.GetStartDmg(towerType);
	}

	private void setDefaultRange() {
		range = helpz.Constants.Towers.GetStartRange(towerType);
	}

	private void setDefaultCooldown() {
		cooldown = helpz.Constants.Towers.GetStartCooldown(towerType);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTowerType() {
		return towerType;
	}

	public void setTowerType(int towerType) {
		this.towerType = towerType;
	}

	public float getDmg() {
		return dmg;
	}

	public float getRange() {
		return range;
	}

	public float getCooldown() {
		return cooldown;
	}

}

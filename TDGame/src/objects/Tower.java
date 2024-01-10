package objects;

//created tower object class
public class Tower {
	
	private int x,y,id,towerType;
	
	public Tower(int x, int y, int id, int towerType)
	{
		this.x=x;
		this.y=y;
		this.id = id;
		this.towerType = towerType;
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
	
}

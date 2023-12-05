package objects;

import java.awt.image.BufferedImage;

public class Tile {

	private BufferedImage[] sprite;
	private int id;
	private String name;
	
	public Tile(BufferedImage sprite, int id, String name)
	{
		this.sprite = new BufferedImage[1];;
		this.sprite[0] = sprite;
		this.id = id;
		this.name = name;
	}
	
	public Tile(BufferedImage[] sprite, int id, String name)
	{
		this.sprite = sprite;
		this.id = id;
		this.name = name;
	}
	
	public boolean isAnimation()
	{
//		if(sprite.length > 1)
//			return true;
//		else
//			return false;
		return sprite.length >1;		
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getSprite(int animationIndex)//use animation index to get a specific sprite in the array to change to. Will seem animated
	{
		return sprite[animationIndex];
	}

	public BufferedImage getSprite()
	{
		return sprite[0];
	}
}

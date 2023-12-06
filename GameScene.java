package scenes;

import java.awt.image.BufferedImage;

import main.Game;

public class GameScene {
	protected Game game;
	protected int animationIndex;
	protected int ANIMATION_SPEED = 10;
	protected int tick;		//protected just means that any class that is a subclass of this one has access to the variables/methods
	
	
	public GameScene(Game game)
	{
		this.game = game;
	}
	
	public Game getGame()
	{
		return game;
	}
	
	protected boolean isAnimation(int spriteID) {
		
		return game.getTileManager().isSpriteAnimation(spriteID);
	}
	
	
	//added to this class because it is used in playing and editing to which this is the super class
	protected void updateTick() {
		tick++;
		if(tick>=ANIMATION_SPEED)	//change this number to adjust animation speed
			//the higher the # the slower the animation
		{
			tick=0;
			animationIndex++;
			if(animationIndex>=4)
			{animationIndex = 0;}
		}
	}
	
	
	protected BufferedImage getSprite(int spriteID) {
		return game.getTileManager().getSprite(spriteID);
	}

	protected BufferedImage getSprite(int spriteID, int animationIndex) {
		return game.getTileManager().getAniSprite(spriteID, animationIndex);
	}
}

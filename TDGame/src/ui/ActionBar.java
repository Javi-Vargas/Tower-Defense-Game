package ui;

import static main.GameStates.MENU;
import static main.GameStates.SetGameState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import objects.Tile;
import scenes.Playing;

public class ActionBar extends Bar{
	
	private MyButton bMenu;
	private Playing playing;
	private Tile selectedTile;
	
	private ArrayList<MyButton> tileButtons = new ArrayList<>();
	
	public ActionBar(int x, int y, int width, int height, Playing playing)
	{
		super(x,y,width,height);
		this.playing = playing;
		
		initButtons();
	}
	
	
	private void initButtons() {
		bMenu = new MyButton("Menu",  2,642, 100,30);
	}
	
	private void drawButtons(Graphics g) {
		bMenu.draw(g);
	}
			
	public void draw(Graphics g)
	{
		//background
		g.setColor(new Color(220,123,14));
		g.fillRect(x, y, width, height);
		
		//buttons
		drawButtons(g);
	}
	
	public void mouseClicked(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			SetGameState(MENU);		
	}

//	private void saveLevel() {
//		playing.saveLevel();
//	}


	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		if(bMenu.getBounds().contains(x,y))
		{
			bMenu.setMouseOver(true);
		}
	}

	public void mousePressed(int x, int y) {
		if(bMenu.getBounds().contains(x,y))
		{
			bMenu.setMousePressed(true);
		}
		
	}

	public void mouseReleased(int x, int y) {
		bMenu.resetBooleans();
		
	}
}

package scenes;

import static main.GameStates.*;
import static main.GameStates.SetGameState;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import main.Game;
import ui.MyButton;

public class Menu extends GameScene implements SceneMethods{

	//private BufferedImage img;	//creates a BI obj to make use of images in the game
	//private ArrayList<BufferedImage> sprites = new ArrayList<>();	//the arrayList is here to hold sprites from the image we're using. Essentially sectioning off parts of the image (as x,y coordinates) and adding them to the array so that we can call on them later
	//private Random random;	//create an object that we'll use to randomize values	
	
	private MyButton bPlaying, bEdit, bSettings, bQuit;
	
	public Menu(Game game) {
		super(game);
		//importImage();	//a created method to get the image you want to use from a specific path you provide
		//loadSprites();
		initButtons();
	}

	private void initButtons() {
		int w = 150;
		int h = w / 3;
		int x = 640 / 2 - w / 2;
		int y = 150;
		int yOffset = 100;

		bPlaying = new MyButton("Play", x, y, w, h);
		bEdit = new MyButton("Edit", x,y+yOffset,w, h);
		bSettings = new MyButton("Settings", x, y + yOffset*2, w, h);
		bQuit = new MyButton("Quit", x, y + yOffset * 3, w, h);
		
//		int x = 100;
//		int y = 100;
//		int w = 100;
//		int h = 50;
//		
//		bPlaying = new MyButton("Play", x, y, w, h);
//		bSettings = new MyButton("Settngs",x,y*2, w, h);
//		bQuit = new MyButton("Quit", x,y*3, w, h);
	}

	@Override
	public void render(Graphics g) {
		drawButtons(g);
		
	}
	
	private void drawButtons(Graphics g) {
		bPlaying.draw(g);
		bEdit.draw(g);
		bSettings.draw(g);
		bQuit.draw(g);
		
	}

/*	private void loadSprites() {	//function that loads the sprites into an array so that you can call specific elements/positions when you want a certain sprite
		for(int y = 0; y<10;y++)
		{
			for(int x =0; x<10; x++)
			{
				sprites.add(img.getSubimage(x*32, y*32, 32,32));	//adds those positions to the sprites list in a loop
			}
		}
	}
	
	private void importImage() {	//gets image you want to use from a specified path you give it
		InputStream is = getClass().getResourceAsStream("/spriteatlas.png");
		
		try {	//needs to use try catch to throw an error if there is no image found in the location
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/

	@Override
	public void mouseClicked(int x, int y) {

		if (bPlaying.getBounds().contains(x, y)) {
			SetGameState(PLAYING);
		} 
		else if (bEdit.getBounds().contains(x, y)) {
			SetGameState(EDIT);
		}
		else if (bSettings.getBounds().contains(x, y)) {
			SetGameState(SETTINGS);
		} else if (bQuit.getBounds().contains(x, y))
			System.exit(0);
	}

	@Override
	public void mouseMoved(int x, int y) {
		bPlaying.setMouseOver(false);
		bSettings.setMouseOver(false);
		bQuit.setMouseOver(false);

		if (bPlaying.getBounds().contains(x, y)) {
			bPlaying.setMouseOver(true);
		} else if (bEdit.getBounds().contains(x, y)) {
			bEdit.setMouseOver(true);
		} else if (bSettings.getBounds().contains(x, y)) {
			bSettings.setMouseOver(true);
		} else if (bQuit.getBounds().contains(x, y)) {
			bQuit.setMouseOver(true);
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (bPlaying.getBounds().contains(x, y)) {
			bPlaying.setMousePressed(true);
		} else if (bEdit.getBounds().contains(x, y)) {
			bEdit.setMousePressed(true);
		} else if (bSettings.getBounds().contains(x, y)) {
			bSettings.setMousePressed(true);
		} else if (bQuit.getBounds().contains(x, y)) {
			bQuit.setMousePressed(true);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	private void resetButtons() {
		bPlaying.resetBooleans();
		bEdit.resetBooleans();
		bSettings.resetBooleans();
		bQuit.resetBooleans();
	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub
		
	}
}

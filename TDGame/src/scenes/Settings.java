package scenes;

import java.awt.Color;
import java.awt.Graphics;

import ui.MyButton;
import main.Game;

import static main.GameStates.*;

public class Settings extends GameScene implements SceneMethods{

	private MyButton bMenu;
	
	public Settings(Game game) {
		super(game);
		initButtons();
		// TODO Auto-generated constructor stub
	}

	private void initButtons() {
		bMenu = new MyButton("Menu", 2, 2, 100,30);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);	
		g.fillRect(0, 0, 640, 640);		
		
		bMenu.draw(g);
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			SetGameState(MENU);
		
	}

	@Override
	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
		
	}

	@Override
	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
		
	}

	@Override
	public void mouseReleased(int x, int y) {
		resetButtons();
	}

	private void resetButtons() {
		bMenu.resetBooleans();

	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}

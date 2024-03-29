package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.Game;
import ui.MyButton;
import static main.GameStates.*;

public class GameOver extends GameScene implements SceneMethods {

	private MyButton bReplay, bGameOverMenu;

	public GameOver(Game game) {
		super(game);
		initButtons();
	}

	private void initButtons() {

		int w = 150;
		int h = w / 3;
		int x = 640 / 2 - w / 2;
		int y = 300;
		int yOffset = 100;

		bGameOverMenu = new MyButton("Menu", x, y, w, h);
		bReplay = new MyButton("Replay", x, y + yOffset, w, h);

	}

	@Override
	public void render(Graphics g) {
		drawGameOverStuff(g);	
	}
	
	private void drawGameOverStuff(Graphics g)
	{
		if(gameState == GAME_OVER)
		{						
			// buttons
			g.setFont(new Font("LucidaSans", Font.BOLD, 20));
			bGameOverMenu.draw(g);
			bReplay.draw(g);
			
			// game over text
			g.setFont(new Font("LucidaSans", Font.BOLD, 50));
			g.setColor(Color.red);
			g.drawString("Game Over!", 160, 80);
		}
	}

	private void replayGame() {
		// reset everything
		resetAll();

		// change state to playing
		SetGameState(PLAYING);

	}

	private void resetAll() {
		game.getPlaying().resetEverything();
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (bGameOverMenu.getBounds().contains(x, y)) {
			SetGameState(MENU);
			resetAll();
		} else if (bReplay.getBounds().contains(x, y))
			replayGame();
	}

	@Override
	public void mouseMoved(int x, int y) {
		bGameOverMenu.setMouseOver(false);
		bReplay.setMouseOver(false);

		if (bGameOverMenu.getBounds().contains(x, y))
			bGameOverMenu.setMouseOver(true);
		else if (bReplay.getBounds().contains(x, y))
			bReplay.setMouseOver(true);
	}

	@Override
	public void mousePressed(int x, int y) {
		if (bGameOverMenu.getBounds().contains(x, y))
			bGameOverMenu.setMousePressed(true);
		else if (bReplay.getBounds().contains(x, y))
			bReplay.setMousePressed(true);

	}

	@Override
	public void mouseReleased(int x, int y) {
		bGameOverMenu.resetBooleans();
		bReplay.resetBooleans();

	}

	@Override
	public void mouseDragged(int x, int y) {
		// TODO Auto-generated method stub

	}

}

package main;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import helpz.LoadSave;
import inputs.KeyboardListener;
import inputs.MyMouseListener;
import managers.TileManager;
import scenes.Editing;
import scenes.GameOver;
import scenes.GameWin;
import scenes.Menu;
import scenes.Playing;
import scenes.Settings;

public class Game extends JFrame implements Runnable{	//the class that will run the game doens't hold all the code for the game but it will run the game object

	private GameScreen gameScreen;	//create a private gamescreen obj that will be called on
	private Thread gameThread;
	
	private final double FPS_SET = 120.0;
	private final double UPS_SET = 60.0;
	
	//classes
	private Render render;
	private Menu menu;
	private Playing playing;
	private Settings settings;
	private Editing editing;
	private TileManager tileManager;
	private GameOver gameOver;
	private GameWin gameWin;
	
	public static void main(String[] args) {
		Game game = new Game();
		game.gameScreen.initInputs();
		game.start();
	}


	public Game() {		
		initClasses();
		createDefaultLevel();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);	//makes it so that 'x-ing out' of the window/frame closes the program as well
        setLocation(500, 50);
		//setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Elias Tower Defense 1");
		add(gameScreen);	//adds it to the JFrame
		pack();
		setVisible(true);	//makes it visible
	}
	
	private void initClasses() {
		tileManager = new TileManager();
		render = new Render(this);
		gameScreen = new GameScreen(this);	//initializes the gamescreen obj	
		menu = new Menu(this);
		playing = new Playing(this);
		settings = new Settings(this);
		editing = new Editing(this);
		gameOver = new GameOver(this);
		gameWin = new GameWin(this);
	}

	private void createDefaultLevel() {
		int[] arr = new int[400];	//creates a level with the dimensions 
		for(int i=0; i<arr.length; i++)
		{
			arr[i] = 0;	//0 == grass tiles so the default levels are all grass
		}
		
		LoadSave.CreateLevel("new_level", arr);
	}
	
	private void start() {
		gameThread = new Thread(this){};
		gameThread.start();
	}
	
	private void updateGame() {
		switch(GameStates.gameState)
		{
		case EDIT:
			editing.update();
			break;
		case MENU:
			break;
		case PLAYING:
			playing.update();
			break;
		case SETTINGS:
			break;
		default:
			break;
		
		}
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0/FPS_SET;	//one second = 1billion nanoseconds;
		double timePerUpdate = 1000000000.0/UPS_SET;
		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long lastTimeCheck = System.currentTimeMillis();
		int frames=0;
		int updates=0;
		long now;
		
		while(true)
		{		
			now = System.nanoTime();
			//Render
			if(now - lastFrame >= timePerFrame)
			{
				repaint();
				lastFrame = now;
				frames++;
			}
			if(now - lastUpdate >= timePerUpdate)//Update
			{
				updateGame();
				lastUpdate = now;
				updates++;
			}
			if(System.currentTimeMillis() - lastTimeCheck >= 1000)
			{
				//System.out.println("FPS: " + frames + ": |  UPS: " + updates);
				frames=0;
				updates=0;
				lastTimeCheck = System.currentTimeMillis();
			}
		}
	}
	
	//getters and setters
	public Render getRender()
	{
		return render;
	}

	public Menu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing;
	}

	public Settings getSettings() {
		return settings;
	}
	
	public Editing getEditor() {
		return editing;
	}

	public TileManager getTileManager()
	{
		return tileManager;
	}
	
	public GameOver getGameOver() {
		return gameOver;
	}
	
	public GameWin getGameWin() {
		return gameWin;
	}	
	
}

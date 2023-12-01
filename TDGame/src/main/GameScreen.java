package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import inputs.KeyboardListener;
import inputs.MyMouseListener;

public class GameScreen extends JPanel{	//a class for the gamescreen so we can give it all the properties and functions we want	
	private Dimension size;
	private Game game;
	private MyMouseListener myMouseListener;
	private KeyboardListener keyboardListener;
	private long lastTime;
	private int frames;
	
	
	
	public GameScreen(Game game) {	//The gamescreen obj, we import the img here because it has to do with threading/game loops
		this.game = game;
		setPanelSize();
	}
	
	public void initInputs()
	{
		myMouseListener = new MyMouseListener(game);
		keyboardListener = new KeyboardListener();
		
		addMouseListener(myMouseListener);
		addMouseMotionListener(myMouseListener);
		addKeyListener(keyboardListener);
		
		requestFocus();
	}
	
	private void setPanelSize() {
		size = new Dimension(640,740);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
	}



	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.getRender().render(g);
	}

/*
//	private void callFPS()
//	{	
//		frames++;
//		if(System.currentTimeMillis() - lastTime >= 1000)
//		{
//			System.out.println("FPS: " + frames);
//			frames = 0;
//			lastTime= System.currentTimeMillis();
//		}
//	}
	

	
//	private Color getRndColor() {
//		int r = random.nextInt(256);
//		int g = random.nextInt(256);
//		int b = random.nextInt(256);
//		
//		return new Color(r,g,b);
//	} 


 */
}
package ui;

import static main.GameStates.*;
import static main.GameStates.SetGameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import helpz.Constants.Towers;
import objects.Tower;
import scenes.Playing;

public class ActionBar extends Bar{
	
	private MyButton bMenu, bPause;
	private Playing playing;
	
	private MyButton[] towerButtons;
	private MyButton sellTower, upgradeTower;
	private Tower selectedTower;
	private Tower displayedTower;
	
	private DecimalFormat formatter;
	private int gold = 100;
	private int lives = 25;
	
	private boolean showTowerCost;
	private int towerCostType;
		
	public ActionBar(int x, int y, int width, int height, Playing playing)
	{
		super(x,y,width,height);
		this.playing = playing;
		formatter = new DecimalFormat("0.0");
		
		initButtons();
	}
	
	
	private void initButtons() {
		bMenu = new MyButton("Menu",  2,642, 100,30);
		bPause = new MyButton("Pause",  2,682, 100,30);
		
		towerButtons = new MyButton[3];
		int w =50;
		int h=50;
		int xStart = 110;
		int yStart = 650; 
		int xOffset = (int)(w*1.1f);
		
		for (int i = 0; i < towerButtons.length; i++) 
			towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, w, h, i);
		
		sellTower = new MyButton("Sell", 420, 702, 80, 25);
		upgradeTower = new MyButton("Upgrade", 545, 702,80,25);
	}
	
	private void drawButtons(Graphics g) {
		bMenu.draw(g);
		bPause.draw(g);
		
		for(MyButton b: towerButtons)
		{
			g.setColor(Color.gray);
			g.fillRect(b.x, b.y, b.width, b.height);
			g.drawImage(playing.getTowerManager().getTowerImgs()[b.getID()], b.x, b.y, b.width, b.height, null);
			drawButtonFeedback(g,b);
		}
	}
			
	public void draw(Graphics g)
	{
		//background
		g.setColor(new Color(220,123,14));
		g.fillRect(x, y, width, height);
		
		//buttons
		drawButtons(g);
		
		//DisplayedTOwer
		drawDisplayedTower(g);
		
		//Wave Info
		drawWaveInfo(g);
		
		//gold info
		drawGoldAmt(g);
		
		if(showTowerCost)
			drawTowerCost(g);
		
		//game paused text
		if(playing.isGamePaused())
		{	g.drawString("Game is Paused", 50, 770);}
		
		//lives
		g.setColor(Color.black);
		g.drawString("Lives: " + lives, 110, 750);
	}
	
	private void drawTowerCost(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(280, 650, 120, 50);
		g.setColor(Color.black);
		g.drawRect(280, 650, 120, 50);
		g.drawString("" + getTowerCostName(), 285, 670);
		g.drawString("Cost: " + getTowerCostCost() + "g", 285, 695);
		
		//message when player can't afford tower
		if(insufficientFunds())
		{
			g.setColor(Color.BLUE);
			g.drawString("Insufficient Funds..", 270, 725);
			g.setColor(Color.black);
		}
	}

	private boolean insufficientFunds() {
		return getTowerCostCost() > gold;
	}

	private int getTowerCostCost() {
		return helpz.Constants.Towers.GetTowerCost(towerCostType);
	}

	private String getTowerCostName() {
		return helpz.Constants.Towers.GetName(towerCostType);
	}

	private void drawGoldAmt(Graphics g) {
		g.drawString("Gold: " + gold, 110, 725);
	}

	private void drawWaveInfo(Graphics g) {
		g.setColor(Color.black);
		g.setFont(new Font("LucidaSans", Font.BOLD, 20));
		drawWaveTimerInfo(g);
		drawEnemiesLeftInfo(g);
		drawWavesLeftInfo(g);
	}
	
	private void drawWavesLeftInfo(Graphics g) {
		int current = playing.getWaveManager().getWaveIndex();
		int size = playing.getWaveManager().getWaves().size();
		g.drawString("Wave " + (current + 1) + " / " + size, 425, 770);
	}


	private void drawEnemiesLeftInfo(Graphics g) {
		int remaining = playing.getEnemyManager().getAmountOfAliveEnemies();
		g.drawString("Enemies Left: " + remaining, 420, 790);
	}


	private void drawWaveTimerInfo(Graphics g) {
		if (playing.getWaveManager().isWaveTimerStarted()) {
			float timeLeft = playing.getWaveManager().getTimeLeft();
			String formattedText = formatter.format(timeLeft);
			g.drawString("Time Left: " + formattedText, 425, 750);
		}
	}

	private void drawDisplayedTower(Graphics g) {
		if(displayedTower != null)
		{
			g.setColor(Color.gray);
			g.fillRect(410,  645,  220, 85);
			g.setColor(Color.black);
			g.drawRect(410, 645, 220, 85);
			g.drawRect(420, 650, 50, 50);
			g.drawImage(playing.getTowerManager().getTowerImgs()[displayedTower.getTowerType()],420, 650, 50, 50, null);
			g.setFont(new Font("LucidSans", Font.BOLD, 15));
			g.drawString("" + Towers.GetName(displayedTower.getTowerType()), 480, 660);
			g.drawString("ID: " + displayedTower.getId(), 480, 675);
			g.drawString("Tier: " + displayedTower.getTier(), 560, 660);
			
			drawDisplayedTowerBorder(g);
			drawDisplayedTowerRange(g);
			
			//sell tower button
			sellTower.draw(g);
			drawButtonFeedback(g,sellTower);
			
			//upgrade tower button
			if(displayedTower.getTier() < 3 && gold >=getUpgradeAmount(displayedTower))
			{
				upgradeTower.draw(g);
				drawButtonFeedback(g,upgradeTower);				
			}
			
			if(sellTower.isMouseOver())
			{
				g.setColor(Color.red);
				g.drawString("Sell for: " + getSellAmount(displayedTower) + "g", 480, 695);
			}
			else if(upgradeTower.isMouseOver() && gold >=getUpgradeAmount(displayedTower))
			{
				g.setColor(Color.green);
				g.drawString("Upgrade for: " + getUpgradeAmount(displayedTower) + "g", 480, 695);
			}
		}
	}

	private int getUpgradeAmount(Tower displayedTower) {
		return (int)(helpz.Constants.Towers.GetTowerCost(displayedTower.getTowerType()) * .3f);
	}


	private int getSellAmount(Tower displayedTower) {
		int upgradeCost = (displayedTower.getTier()-1) * getUpgradeAmount(displayedTower);
		upgradeCost *= .5f;
		return (helpz.Constants.Towers.GetTowerCost(displayedTower.getTowerType())/2) + upgradeCost;
	}


	private void drawDisplayedTowerRange(Graphics g) {
		g.setColor(Color.white);
		g.drawOval(displayedTower.getX()+ 16 - (int)displayedTower.getRange()*2/2, displayedTower.getY()+16 - (int)displayedTower.getRange()/2
				*2, (int)displayedTower.getRange()*2,(int)displayedTower.getRange()*2);
	}

	private void drawDisplayedTowerBorder(Graphics g) {
		g.setColor(Color.blue);
		g.drawRect(displayedTower.getX(), displayedTower.getY(), 32, 32);
	}

	private boolean canAffordTower(int towerType) {
		return helpz.Constants.Towers.GetTowerCost(towerType) <= gold;
	}
	
	public void mouseClicked(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			SetGameState(MENU);
		else if(bPause.getBounds().contains(x,y))
		{
			togglePause();
		}
		else {
			
			if(displayedTower != null)
			{
				if(sellTower.getBounds().contains(x,y))
				{
					sellTowerClicked();
					return;
				}
				else if(upgradeTower.getBounds().contains(x,y) && displayedTower.getTier() < 3 && gold >=getUpgradeAmount(displayedTower)) 
				{
					upgradeTowerClicked();
					return;
				}
			}
			
			for(MyButton b : towerButtons) {
				if(b.getBounds().contains(x, y)) {
					if(!canAffordTower(b.getID()))
						return;
					selectedTower = new Tower(0,0,-1,b.getID());
					playing.setSelectedTower(selectedTower);
					return;
				}
			}
		}
	}

	private void togglePause() {
		playing.setGamePaused(!playing.isGamePaused());
		
		//now toggle button text to say pause/unpause accordingly
		if(playing.isGamePaused())
			bPause.setText("Unpause");
		else
			bPause.setText("Pause");
	}


	private void upgradeTowerClicked() {
		playing.upgradeTower(displayedTower);
		gold -= getUpgradeAmount(displayedTower);
	}


	private void sellTowerClicked() {
		playing.removeTower(displayedTower);
		gold += helpz.Constants.Towers.GetTowerCost(displayedTower.getTowerType()) / 2;	//sells tower for half of its cost price
		
		int upgradeCost = (displayedTower.getTier()-1) * getUpgradeAmount(displayedTower);
		upgradeCost *= .5f;
		gold+= upgradeCost;
		displayedTower = null;
	}


	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		bPause.setMouseOver(false);
		showTowerCost = false;
		sellTower.setMouseOver(false);
		upgradeTower.setMouseOver(false);
		
		for (MyButton b : towerButtons)
			b.setMouseOver(false);

		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
		else if(bPause.getBounds().contains(x,y))
		{
			bPause.setMouseOver(true);
		}
		else {
			
			if(displayedTower != null)
			{
				if(sellTower.getBounds().contains(x,y))
				{
					sellTower.setMouseOver(true);
					return;
				}
				else if(upgradeTower.getBounds().contains(x,y) && displayedTower.getTier() < 3) 
				{
					upgradeTower.setMouseOver(true);
					return;
				}
			}
			
			for (MyButton b : towerButtons)
				if (b.getBounds().contains(x, y)) {
					b.setMouseOver(true);
					showTowerCost = true;
					towerCostType = b.getID();
					return;
				}
			}
		}

	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
		else if(bPause.getBounds().contains(x,y))
		{
			bPause.setMousePressed(true);
		}
		else
		{
			if(displayedTower != null)
			{
				if(sellTower.getBounds().contains(x,y)) {
					sellTower.setMousePressed(true);
					return;
				}
				else if(upgradeTower.getBounds().contains(x,y) && displayedTower.getTier() < 3) {
					upgradeTower.setMousePressed(true);
					return;					
				}
			}
			
			for (MyButton b : towerButtons)
				if (b.getBounds().contains(x, y)) {
					b.setMousePressed(true);
					return;
				}
		}

	}

	public void mouseReleased(int x, int y) {
		bMenu.resetBooleans();
		bPause.resetBooleans();
		for (MyButton b : towerButtons)
			b.resetBooleans();
		sellTower.resetBooleans();
		upgradeTower.resetBooleans();
	}


	public void displayTower(Tower t) {
		displayedTower = t;
	}


	public void purchaseTower(int towerType) {
		this.gold -= helpz.Constants.Towers.GetTowerCost(towerType);
	}


	public void addFunds(int getReward) {
		this.gold += getReward;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void removeOneLife()
	{
		lives--;
		if(lives <=0 )
			SetGameState(GAME_OVER);
			//System.out.println("GAME OVER!! :(");
	}


	public void resetEverything() {
		lives = 25;
		towerCostType = 0;
		showTowerCost = false;
		gold = 100;
		
		selectedTower = null;
		displayedTower = null;
	}
}

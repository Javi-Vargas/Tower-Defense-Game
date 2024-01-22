package scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enemies.Enemy;
import helpz.LoadSave;
import main.Game;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.PathPoint;
import objects.Tower;
import ui.ActionBar;
import static helpz.Constants.Tiles.GRASS_TILE;

public class Playing extends GameScene implements SceneMethods{

	private int[][] lvl;
	private ActionBar actionBar;
	private int mouseX, mouseY;
	private EnemyManager enemyManager;
	private TowerManager towerManager;
	private ProjectileManager projManager;
	private WaveManager waveManager;
	private PathPoint start,end;
	private Tower selectedTower;
	private int goldTick;
	private boolean gamePaused = false;
	
	public Playing(Game game) {
		super(game);
		loadDefaultLevel();
		
		actionBar = new ActionBar(0,640,640,160, this); //this is the location for the bottom 100 pixels for the bottom bar
		
		enemyManager = new EnemyManager(this, start, end);
		towerManager = new TowerManager(this);
		projManager = new ProjectileManager(this);
		waveManager = new WaveManager(this);
		
		
	}

	private void loadDefaultLevel() {
		lvl = LoadSave.GetLevelData("new_level");
		ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints("new_level");
		start = points.get(0);
		end = points.get(1);
	}
	
	public void setLevel(int[][] lvl)
	{
		this.lvl = lvl;
	}
	
	public void update()
	{
		if(!gamePaused)
		{
			updateTick();
			waveManager.update();
			
			//passive gold income for player to spend aka 'gold tick'
			goldTick++;
			if(goldTick % (60*3) == 0)
				actionBar.addFunds(1);
			
			if(isAllEnemiesDead())
			{
				if(isThereMoreWaves())
				{//check wave timer
					waveManager.startWaveTimer();
					if(isWaveTimerOver())
					{
						waveManager.increaseWaveIndex();
						enemyManager.getEnemies().clear();
						waveManager.resetEnemyIndex();
					}				
				}
				//System.out.println(actionBar.getLives() + ", " + actionBar.getWavesLeft());
			}
			
			if(isTimeForNewEnemy())
			{
				spawnEnemy();
			}
			
			enemyManager.update();
			towerManager.update();	//created an update method for the towerManager
			projManager.update();			
		}
	}
	
	private boolean isWaveTimerOver() {
		return waveManager.isWaveTimerOver();
	}

	private boolean isThereMoreWaves() {
		return waveManager.isThereMoreWaves();
	}

	private boolean isAllEnemiesDead() {
		if(waveManager.isThereMoreEnemiesInWave())
			return false;
		
		for(Enemy e: enemyManager.getEnemies())
			if(e.isAlive())
				return false;
		return true;
	}

	private void spawnEnemy() {
		enemyManager.spawnEnemy(waveManager.getNextEnemy());
	}

	private boolean isTimeForNewEnemy() {
		if(waveManager.isTimeForNewEnemy())
			if(waveManager.isThereMoreEnemiesInWave())
				return true;
		return false;
	}
	
	public void setSelectedTower(Tower selectedTower) {
		this.selectedTower = selectedTower;
	}

	@Override
	public void render(Graphics g) {
		drawLevel(g);		
		actionBar.draw(g);
		enemyManager.draw(g);
		towerManager.draw(g);	//added this to draw enemies on the level
		projManager.draw(g);
		
		drawSelectedTower(g);
		drawHighlight(g);
		drawWaveInfos(g);
	}
	
	private void drawWaveInfos(Graphics g) {

	}

	private void drawHighlight(Graphics g) {
		g.setColor(Color.yellow);
		g.drawRect(mouseX, mouseY, 32, 32);
		
	}

	private void drawSelectedTower(Graphics g) {
		if(selectedTower!= null)
			g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType()], mouseX, mouseY, null);
	}

	private void drawLevel(Graphics g)
	{
		for(int y=0; y<lvl.length;y++)
		{
			for(int x=0;x<lvl[y].length;x++)
			{
				int id = lvl[y][x];
				if(isAnimation(id))
				{
					g.drawImage(getSprite(id, animationIndex), x*32, y*32, null);
				}
				else
					g.drawImage(getSprite(id), x*32, y*32, null);
			}
		}
	}
	

	
	public int getTileType(int x, int y)
	{
		int xCord = x/32;
		int yCord = y/32;
		
		if(xCord <0 || yCord > 19)
		{
			return 0;
		}
		if(yCord <0 || xCord > 19)
		{
			return 0;
		}			//these checks are to account for enemies walking out of bounds
		
		int id = lvl[y/32][x/32];
		return game.getTileManager().getTile(id).getTileType();
	}
	
	public void shootEnemy(Tower t, Enemy e) {
		projManager.newProjectile(t, e);		
	}

	@Override
	public void mouseClicked(int x, int y) {
		if(y>= 640)
		{
			actionBar.mouseClicked(x, y);
		}
		else
			if(selectedTower!=null)
			{
				if(isTileGrass(mouseX,mouseY))
				{
					//can place a check here to see if can afford the tower before even placing it
					if(getTowerAt(mouseX, mouseY) == null)
					{
						towerManager.addTower(selectedTower, mouseX, mouseY);
						purchaseTower(selectedTower.getTowerType()); 	//remove gold
						selectedTower = null;	
					}
				}
			}
			else
			{
				//get tower if exists on x,y
				Tower t = getTowerAt(mouseX, mouseY);
				actionBar.displayTower(t);
			}
	}


	private void purchaseTower(int towerType) {
		actionBar.purchaseTower(towerType);
	}

	private Tower getTowerAt(int x, int y) {
		return towerManager.getTowerAt(x,y);
	}

	private boolean isTileGrass(int x, int y) {
		int id = lvl[y / 32][x / 32];
		int tileType = game.getTileManager().getTile(id).getTileType();
		return tileType == GRASS_TILE;
	}

	@Override
	public void mouseMoved(int x, int y) {
		if(y>= 640 && !gamePaused)
		{
			actionBar.mouseMoved(x, y);
		}
		else
		{
			mouseX=(x/32)*32;
			mouseY = (y/32)*32;	//makes the slected tile display snap onto the square so you can see where to place items later
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if(y>= 640 && !gamePaused)
		{
			actionBar.mousePressed(x, y);
		}

	}

	@Override
	public void mouseReleased(int x, int y) {
		actionBar.mouseReleased(x, y);
	}


	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_ESCAPE)
			selectedTower = null;
	}

	@Override
	public void mouseDragged(int x, int y) {
	}
	
	public void rewardPlayer(int enemyType)
	{
		actionBar.addFunds(helpz.Constants.Enemies.GetReward(enemyType));
	}
	
	public void setGamePaused(boolean gamePaused)
	{
		this.gamePaused = gamePaused;
	}
	
	public TowerManager getTowerManager()
	{
		return towerManager;
	}
	
	public EnemyManager getEnemyManager()
	{
		return enemyManager;
	}

	public WaveManager getWaveManager()
	{
		return waveManager;
	}
	
	public boolean isGamePaused()
	{
		return gamePaused;
	}

	public void removeTower(Tower displayedTower) {
		towerManager.removeTower(displayedTower);
	}

	public void upgradeTower(Tower displayedTower) {
		towerManager.upgradeTower(displayedTower);
	}

	public void removeOneLife() {
		actionBar.removeOneLife();
	}

	public void resetEverything() {
		actionBar.resetEverything();
		enemyManager.reset();
		towerManager.reset();
		projManager.reset();
		waveManager.reset();
		
		mouseX = 0;
		mouseY = 0;
		goldTick = 0;
		
		selectedTower = null;
		gamePaused = false;
	}
}

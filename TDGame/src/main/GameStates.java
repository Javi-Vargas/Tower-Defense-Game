package main;

public enum GameStates {	//an enum is a special class that stores constant values
	
	PLAYING,
	MENU,
	EDIT,
	SETTINGS;
	
	public static GameStates gameState = MENU;
	
	public static void SetGameState(GameStates state)
	{
		gameState = state;
	}
	
}

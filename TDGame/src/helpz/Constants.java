package helpz;

public class Constants {

	//added tower constants to be easily called upon
	public static class Towers {
		public static final int CANNON = 0;
		public static final int ARCHER = 1;
		public static final int WIZARD = 2;
		
		public static String GetName(int towerType)
		{
			switch(towerType)
			{
			case CANNON:
				return "Cannon";
			case ARCHER:
				return "Archer";
			case WIZARD:
				return "Wizard";
			}
			return "";
		}
		
		public static float GetStartDmg(int towerType)
		{
			switch(towerType)
			{
			case CANNON:
				return 25;
			case ARCHER:
				return 15;
			case WIZARD:
				return 5;
			}
			return 0;
		}
		
		public static float GetStartRange(int towerType)
		{
			switch(towerType)
			{
			case CANNON:
				return 100;
			case ARCHER:
				return 100;
			case WIZARD:
				return 100;
			}
			return 0;
		}
		
		public static float GetStartCooldown(int towerType)
		{
			switch(towerType)
			{
			case CANNON:
				return 10;
			case ARCHER:
				return 10;
			case WIZARD:
				return 10;
			}
			return 0;
		}
	}
	
	public static class Direction {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class Tiles{
		public static final int WATER_TILE = 0;
		public static final int GRASS_TILE = 1;
		public static final int ROAD_TILE = 2;
	}
	
	public static class Enemies{
		public static final int ORC = 0;
		public static final int BAT = 1;
		public static final int KNIGHT = 2;
		public static final int WOLF = 3;
		
		public static float GetSpeed(int enemyType) //to vary speed of each enemy
		{
			switch(enemyType)
			{
			case ORC:
				return 0.5f;
			case BAT:
				return .65f;
			case KNIGHT:
				return .3f;
			case WOLF:
				return .75f;
			}
			
			return 0;
		}

		public static int getStartHealth(int enemyType) {
			switch(enemyType)
			{
			case ORC:
				return 100;
			case BAT:
				return 60;
			case KNIGHT:
				return 250;
			case WOLF:
				return 85;
			}
				
			return 0;
		}
	}
	
	public static class Projectiles{
		public static final int ARROW = 0;
		public static final int BOMB = 1;
		public static final int CHAINS = 2;
		
		public static float GetSpeed(int type)
		{
			switch(type)
			{
			case ARROW:
				return 3f;
			case BOMB:
				return 1f;
			case CHAINS:
				return 2f;
			}
				
			return 0f;
		}
	}
	
}

package helpz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class LoadSave {
	
	
	public static BufferedImage getSpriteAtlas()
	{
		BufferedImage img = null;
		InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");
		
		try {	//needs to use try catch to throw an error if there is no image found in the location
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	//txt file
	public static void CreateFile()
	{
		File txtFile = new File("res/testTextFile.txt");
		try {
			txtFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void CreateLevel(String name, int[] idArr)
	{
		File newLevel = new File("res/" + name + ".txt");
		
		if(newLevel.exists())
		{
			//System.out.println("File: " + name + " already exists");
			return;
		}
		else
		{
			try {
				newLevel.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			WriteToFile(newLevel, idArr);
		}
	}
	
	private static void WriteToFile(File f, int[] idArr)
	{
		try {
			PrintWriter pw = new PrintWriter(f);
			for(Integer i: idArr)
				pw.println(i);
			
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static ArrayList<Integer> ReadFromFile(File file)
	{
		ArrayList<Integer> list = new ArrayList<>();
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine())
			{
				list.add(Integer.parseInt(sc.nextLine()));
				//System.out.println(sc.nextLine());
			}
			sc.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return list;
	}
	
	public static int[][] GetLevelData(String name)
	{
		File lvlFile = new File("res/" + name + ".txt");
		//System.out.println(lvlFile);
		
		if(lvlFile.exists())
		{
			//System.out.println(lvlFile.exists());
			ArrayList<Integer> list = ReadFromFile(lvlFile);
			return Utilz.ArrayListTo2Dint(list, 20, 20);
		}
		else
		{
			System.out.println("File: " + name + " does not exist");
			return null;
		}
	}
	
	public static void SaveLevel(String name, int[][] idArr)
	{
		File levelFile = new File("res/" + name + ".txt");
		if(levelFile.exists())
		{
			WriteToFile(levelFile, Utilz.TwoDto1DintArr(idArr));
		}
		else
		{
			System.out.println("File " + name + " does not exist");
			return;
		}
	}
	
	//save 2d int array to a file
	
	//load int array from file
	
	//create a new lvl with default values
	
	
	
}

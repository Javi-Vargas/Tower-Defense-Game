package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class MyButton {

	public int x,y,width,height, id;
	private String text;
	private Rectangle bounds;
	private boolean mouseOver;
	private boolean mousePressed;
	
	//for normal buttons
	public MyButton(String text, int x, int y, int width, int height)
	{
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = -1;
		
		initBounds();
	}
	
	//for tile buttons
	public MyButton(String text, int x, int y, int width, int height, int id)
	{
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		
		initBounds();
	}
	
	public void initBounds()
	{
		this.bounds = new Rectangle(x,y,width,height);
	}
	
	public void draw(Graphics g)
	{
		drawBody(g);

		//border
		drawBorder(g);
		
		//text
		drawText(g);	//this is to get the height and width of the actual text so that we can center it in the button
		//g.drawString(text, x+width/2, y+height/2);
	}
	
	private void drawBorder(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		if(mousePressed)
		{
			g.drawRect(x+1, y+1, width-2,  height-2);
			g.drawRect(x+2, y+2, width-4, height-2);
		}
	}

	private void drawBody(Graphics g) {
		if(mouseOver) {
			g.setColor(Color.gray);
		}
		else
		{
			//body
			g.setColor(Color.white);
			g.fillRect(x,y,width,height);			
		}
		
		
	}

	private void drawText(Graphics g) {
		int w = g.getFontMetrics().stringWidth(text);
		int h = g.getFontMetrics().getHeight();
		g.drawString(text,  x-w/2 + width/2,  y+h/2 + height/2);
	}
	
	public void resetBooleans()
	{
		this.mouseOver = false;
		this.mousePressed = false;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public void setMouseOver(boolean mouseOver)
	{
		this.mouseOver = mouseOver;
	}
	
	public boolean isMouseOver()
	{
		return mouseOver;
	}
	
	public void setMousePressed(boolean mousePressed)
	{
		this.mousePressed = mousePressed;
	}
	
	public boolean isMousePressed()
	{
		return mousePressed;
	}

	public Rectangle getBounds()
	{
		return bounds;
	}
	
	public int getID()
	{
		return id;
	}
}

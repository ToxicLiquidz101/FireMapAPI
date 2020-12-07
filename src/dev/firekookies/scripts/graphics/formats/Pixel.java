package dev.firekookies.scripts.graphics.formats;

import javax.annotation.Nonnull;

public class Pixel {
	private byte color;
	
	private int x;
	private int y;
	
	public Pixel() {
		return;
	}
	
	public Pixel(@Nonnull byte color, @Nonnull int x, @Nonnull int y) {
		this.x = x;
		this.y = y;
		
		this.color = color;
	}
	
	
	
	public void setX(@Nonnull int x) {
		this.x = x;
	}
	
	public void setY(@Nonnull int y) {
		this.y = y;
	}
	
	public void setColor(@Nonnull byte color) {
		this.color = color;
	}
	
	
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public byte getColor() {
		return this.color;
	}
}

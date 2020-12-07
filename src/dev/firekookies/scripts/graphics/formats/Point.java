package dev.firekookies.scripts.graphics.formats;

import javax.annotation.Nonnull;

public class Point {
	private int x;
	private int y;
	
	public Point() {
		return;
	}
	
	public Point(@Nonnull int x, @Nonnull int y) {
		this.x = x;
		this.y = y;
	}
	
	
	
	public void setX(@Nonnull int x) {
		this.x = x; 
	}
	
	public void setY(@Nonnull int y) {
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}

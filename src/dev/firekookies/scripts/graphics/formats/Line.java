package dev.firekookies.scripts.graphics.formats;

import javax.annotation.Nonnull;

public class Line {
	private Point point_1 = new Point();
	private Point point_2 = new Point();
	
	private byte color;
	
	
	
	public Line() {
		return;
	}
	
	public Line(@Nonnull byte color, @Nonnull int x1, @Nonnull int y1, @Nonnull int x2, @Nonnull int y2) {
		this.color = color;
		
		this.point_1.setX(x1);
		this.point_1.setY(y1);
		
		this.point_2.setX(x2);
		this.point_2.setY(y2);
	}
	
	
	
	public void setPoint_1(@Nonnull Point point) {
		this.point_1 = point;
	}
	
	public void setPoint_2(@Nonnull Point point) {
		this.point_2 = point;
	}
	
	public void setColor(@Nonnull byte color) {
		this.color = color;
	}
	
	
	
	public Point getPoint_1() {
		return this.point_1;
	}
	
	public Point getPoint_2() {
		return this.point_2;
	}
	
	public byte getColor() {
		return this.color;
	}


}

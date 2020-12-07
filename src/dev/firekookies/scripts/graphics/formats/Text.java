package dev.firekookies.scripts.graphics.formats;

import javax.annotation.Nonnull;

@Nonnull
public class Text {
	private Point point = new Point(); // change to pixel
	private String text;
	
	
	
	public Text() {
		return;
	}
	
	public Text(Point point, String text) {
		this.point = point;
		this.text = text;
	}
	
	
	
	public void setPoint(Point point) {
		this.point = point;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	
	
	public Point getPoint() {
		return this.point;
	}
	
	public String getText() {
		return this.text;
	}
}

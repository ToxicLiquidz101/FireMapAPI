package dev.firekookies.scripts.graphics.formats;

public class Image {
	private Point point = new Point();
	private java.awt.Image image;
	
	public Image() {
		return;
	}
	
	public Image(int x, int y, java.awt.Image image) {
		this.point.setX(x);
		this.point.setY(y);
		
		this.image = image;
	}
	
	public void setPoint(Point point) {
		this.point = point;
	}

	public void setImage(java.awt.Image image) {
		this.image = image;
	}
	
	
	
	public Point getPoint() {
		return this.point;
	}
	
	public java.awt.Image getImage() {
		return this.image;
	}
}

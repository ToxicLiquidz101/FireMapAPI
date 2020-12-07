package dev.firekookies.scripts.canvas.processing;

import java.util.LinkedList;

import javax.annotation.Nonnull;

import dev.firekookies.scripts.DeveloperConsole;
import dev.firekookies.scripts.canvas.color.MapColor;
import dev.firekookies.scripts.graphics.formats.Image;
import dev.firekookies.scripts.graphics.formats.Pixel;

/*
 * Document
 * 
 * This class handles Canvas Processing.
 * Such content include shape drawing
 * - Pixels
 * - Lines
 * - Rectangles
 * - etc.
 * 
 *  Image Processing is done in this class.
 *  Pixel removal process is also dont in this class to prvent memory leaks.
 *  
 */

@Nonnull
public class CanvasProcesser extends DeveloperConsole {
	private LinkedList<Pixel> pixels = new LinkedList<>();
	private LinkedList<Image> images = new LinkedList<>();

	private boolean fill = false;
	
	
	public void drawLine(String byteCode, int x1, int y1, int x2, int y2) {
		int temp;
		int m, p;
		int x, y;
		int dx, dy;

		if ((x2 - x1) == 0)
			m = (y2 - y1);
		else
			m = (y2 - y1) / (x2 - x1);

		if (Math.abs(m) < 1) {
			if (x1 > x2) {
				temp = x1;
				x1 = x2;
				x2 = temp;

				temp = y1;
				y1 = y2;
				y2 = temp;
			}

			dx = Math.abs(x2 - x1);
			dy = Math.abs(y2 - y1);

			p = 2 * dy - dx;

			x = x1;
			y = y1;

			while (x <= x2) {
				this.addPixel(new Pixel(MapColor.getByteColor(byteCode), x, y));
				x = x + 1;

				if (p >= 0) {
					if (m < 1)
						y = y + 1;
					else
						y = y - 1;
					p = p + 2 * dy - 2 * dx;
				} else {
					p = p + 2 * dy;
				}
			}
		}

		if (Math.abs(m) >= 1) {
			if (y1 > y2) {
				temp = x1;
				x1 = x2;
				x2 = temp;

				temp = y1;
				y1 = y2;
				y2 = temp;
			}

			dx = Math.abs(x2 - x1);
			dy = Math.abs(y2 - y1);

			p = 2 * dx - dy;

			x = x1;
			y = y1;

			while (y <= y2) {
				this.addPixel(new Pixel(MapColor.getByteColor(byteCode), x, y));
				y = y + 1;

				if (p >= 0) {
					if (m >= 1)
						x = x + 1;
					else
						x = x - 1;
					p = p + 2 * dx - 2 * dy;
				} else {
					p = p + 2 * dx;
				}
			}
		}
	}

	public void drawRectangle(String byteCode, int x, int y, int width, int height) {

		// Outline of rectangle!
		this.drawLine(byteCode, x, y, x + width, y);
		this.drawLine(byteCode, x, y, x, y + height);

		this.drawLine(byteCode, x + width, y, x + width, y + height);
		this.drawLine(byteCode, x, y + height, x + width, y + height);

		// Fill rectangel?
		if (fill)
			for (int y1 = y + 1; y1 < y + height; y1++)
				this.drawLine(byteCode, x, y1, x + width, y1);

	}


	
	public void removePixel(Pixel pixel) {
		this.pixels.remove(pixel);
	}
	
	public void removeImage(Image image) {
		this.images.remove(image);
	}

	
	
	public void addPixel(Pixel pixel) {
		if (getPixels().size() > 128 * 128) {
			for (Pixel tempPixel : getPixels()) {
				if (tempPixel.getX() == pixel.getX() && tempPixel.getY() == pixel.getY()) {
					this.pixels.remove(tempPixel);
					
					this.developerLog("Processer/Canvas", "Overlap Removing pixel -> X: " + tempPixel.getX() + " Y: " + tempPixel.getY());
					break;
				}
			}
		}

		this.pixels.push(pixel);
	}
	
	
	public void addImage(int x, int y, java.awt.Image image) {
		this.images.add(new Image(x, y, image));
	}

	

	public void setPixels(LinkedList<Pixel> pixels) {
		this.pixels = pixels;
	}
	
	public void setImages(LinkedList<Pixel> pixels) {
		this.pixels = pixels;
	}
	
	
	
	public LinkedList<Pixel> getPixels() {
		return this.pixels;
	}

	public LinkedList<Image> getImages() {
		return this.images;
	}

	

	public void clearPixels() {
		this.pixels.clear();
	}

	public void clearImages() {
		this.images.clear();
	}
	
	
	
	public void setFill(boolean fill) {
		this.fill = fill;
	}

	public boolean getFill() {
		return this.fill;
	}
}

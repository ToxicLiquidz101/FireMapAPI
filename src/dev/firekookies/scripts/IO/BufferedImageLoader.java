package dev.firekookies.scripts.IO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class BufferedImageLoader {	
	public BufferedImage loadImageResource(String directory, String fileName) {
		return null;
	}
	
	public BufferedImage loadImageFileStream(String directory, String fileName) {
		try {
			File file = new File(directory + File.separator + fileName);
			
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public BufferedImage loadImageURL(String directory, String fileName) {
		try {
			URL url = new URL(directory + File.separator + fileName);
			
			return ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

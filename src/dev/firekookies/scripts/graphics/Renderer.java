package dev.firekookies.scripts.graphics;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapFont;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

import dev.firekookies.scripts.MapAPI;
import dev.firekookies.scripts.canvas.CanvasMap;
import dev.firekookies.scripts.graphics.formats.Image;
import dev.firekookies.scripts.graphics.formats.Pixel;
import dev.firekookies.scripts.graphics.formats.Text;

/*
 * Document
 * 
 * This class handles the rendering of pixels and text.
 * Images are not convertable through this class.
 * For more information about Image, look into the Canvas Processor class.
 * 
 */

public class Renderer extends MapRenderer {
	private boolean render = true;
	private final CanvasMap canvasMap;


	public Renderer(CanvasMap canvasMap) {
		this.canvasMap = canvasMap;
	}

	// TODO add Animation file to render frames or a custom animation class file
	// instance to render objects.
	
	public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
		Map<Player, List<Integer>> maps = canvasMap.getMaps();

		try {
			if (maps.containsKey(player))
				if (render) {
					mapCanvas = drawBackground(mapCanvas);

					mapCanvas = renderInformation(mapCanvas);

					// Stop renderering
					this.render = false;
				}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} 
	}

	
	
	public void addRender(MapView mapView, Renderer renderer) {
		mapView.addRenderer(renderer);
	}

	public void removeRender(MapView mapView, Renderer renderer) {
		mapView.removeRenderer(renderer);
	}

	public void removeAllRenders(MapView mapView) {
		for (MapRenderer mapRenderer : mapView.getRenderers())
			mapView.removeRenderer(mapRenderer);
	}

	
	
	public void update() {
		this.render = true;
	}

	public boolean isUpdate() {
		return this.render;
	}
	
	
	
	private MapCanvas renderInformation(MapCanvas mapCanvas) {
		// Render Image []
		if (canvasMap.getCanvasProcessor().getImages() != null)
			for (Image image : canvasMap.getCanvasProcessor().getImages())
				mapCanvas.drawImage(image.getPoint().getX(), image.getPoint().getY(), image.getImage());

		// Render Pixel *
		if (canvasMap.getCanvasProcessor().getPixels() != null)
			for (Pixel pixel : canvasMap.getCanvasProcessor().getPixels())
				mapCanvas.setPixel(pixel.getX(), pixel.getY(), pixel.getColor());

		// Render Text 'A'
		if (canvasMap.getWordProcessor().getTexts() != null)
			for (Text text : canvasMap.getWordProcessor().getTexts())
				mapCanvas.drawText(text.getPoint().getX(), text.getPoint().getY(),
						(MapFont) MinecraftFont.Font, text.getText());

		return mapCanvas;
	}
	
	private MapCanvas drawBackground(MapCanvas mapCanvas) {
		try {
			mapCanvas.drawImage(0, 0, ImageIO.read(new File(MapAPI.imageFolder + File.separator + "frame.png")));
			
			return mapCanvas;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

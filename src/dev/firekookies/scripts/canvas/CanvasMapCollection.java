package dev.firekookies.scripts.canvas;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;

/*
 * Document 
 * 
 * This class contains the contents of map collections of canvas.
 * 
 */

@Nonnull
public class CanvasMapCollection {
	private static Map<String, CanvasMap> canvasList = new HashMap<>();

	public void addCanvas(String fileName, CanvasMap canvasMap) {
		canvasList.put(fileName, canvasMap);
	}

	public void removeCanvas(String fileName) {
		if (canvasList.containsKey(fileName))
			canvasList.remove(fileName);
	}

	public void removeCanvas(CanvasMap canvasMap) {
		if (canvasList.containsValue(canvasMap)) {
			canvasList.remove(getFileName(canvasMap));
		}
	}

	public String getFileName(CanvasMap canvasMap) {
		if (canvasList.containsValue(canvasMap))
			for (String fileName : canvasList.keySet())
				if (canvasList.get(fileName) == canvasMap)
					return fileName;
		return null;
	}

	public boolean exist(String fileName) {
		if (canvasList.containsKey(fileName))
			return true;
		return false;
	}

	public boolean exist(CanvasMap canvasMap) {
		if (canvasList.containsValue(canvasMap))
			return true;
		return false;
	}

	public boolean exist(Player player) {
		if (getCanvasMap(player) != null)
			return true;
		return false;
	}
	
	public CanvasMap getCanvasMap(String fileName) {
		if (canvasList.containsKey(fileName))
			return canvasList.get(fileName);
		return null;
	}
	
	public CanvasMap getCanvasMap(Player player) {
		for (CanvasMap canvasMap : canvasList.values())
			for (Player findPlayer : canvasMap.getMaps().keySet())
				if (findPlayer == player)
					return canvasMap;
		return null;
	}
		


	public static Map<String, CanvasMap> getCanvasMapList() {
		return canvasList;
	}
	
	public void clear() {
		canvasList.clear();
	}
}

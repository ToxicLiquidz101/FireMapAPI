package dev.firekookies.scripts.canvas;

import dev.firekookies.scripts.canvas.processing.CanvasProcesser;
import dev.firekookies.scripts.canvas.processing.WordProcesser;
import dev.firekookies.scripts.graphics.Renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;

@Nonnull
public class CanvasMap extends CanvasMapCollection {
	private CanvasProcesser canvasProcessor;
	private WordProcesser wordProcessor;
	private Renderer renderer = new Renderer(this);
	
	private Map<Player, List<Integer>> map; 
	private boolean draw = false;

	
	public CanvasMap() {
		this.canvasProcessor = new CanvasProcesser();
		this.wordProcessor = new WordProcesser();

		this.map = new HashMap<>();
	}

	
	
	public CanvasMapCollection getMapCollection() {
		return (CanvasMapCollection) CanvasMapCollection.getCanvasMapList();
	}

	public CanvasProcesser getCanvasProcessor() {
		return this.canvasProcessor;
	}

	public WordProcesser getWordProcessor() {
		return this.wordProcessor;
	}

	
	
	public void clearMaps() {
		this.map.clear();
	}
	
	public void addMap(Player player, int id) {
		List<Integer> mapViews = new ArrayList<>();
		
		if (map.containsKey(player)) 
			mapViews = map.get(player);
		
		mapViews.add(id);
		
		this.map.put(player, mapViews);
	}
	
	public void removeMap(Player player) {
		this.map.remove(player);
	}
	
	public void setMaps(Map<Player, List<Integer>> map) {
		this.map = map;
	}
	
	public Map<Player, List<Integer>> getMaps() {
		return this.map;
	}
	
	

	public List<Player> getPlayers() {
		List<Player> players = new ArrayList<Player>();
		
		for (Player player : map.keySet())
			players.add(player);
		
		return players;
	}
	
	public Renderer getRenderer() {
		return this.renderer;
	}
	
	
	
	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public boolean getDraw() {
		return this.draw;
	}
}
package dev.firekookies.scripts.canvas;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;

import dev.firekookies.scripts.DeveloperConsole;
import dev.firekookies.scripts.MapAPI;
import dev.firekookies.scripts.IO.JsonReader;

/*
 * Document
 * 
 * Manages load, unload and reload json file.
 * This class also allow you to create a canvas that will be able to access these properties.
 * 
 */

@Nonnull
@SuppressWarnings({ "deprecation", "static-access" })
public class MapManager extends DeveloperConsole {
	private final CanvasMapCollection collection = new CanvasMapCollection();
	
	public void createCanvas(Player player, String fileName) {
		ItemStack itemStack = new ItemStack(Material.FILLED_MAP, 1);
		player.setItemInHand(itemStack);

		if (collection.getCanvasMapList().containsKey(fileName)) {
			CanvasMap canvasMap = collection.getCanvasMap(fileName);

			canvasMap.addMap(player, getMaxMapId());
		}
	}

	
	
	public boolean load(String fileName) {
		fileName = this.getFileByName(fileName);

		if (CanvasMapCollection.getCanvasMapList().containsKey(fileName))
			return false;

		final File file = new File(MapAPI.canvasFolder + File.separator + fileName + ".json");

		if (file.exists()) {
			if (!this.collection.exist(fileName)) {
				JsonReader reader = new JsonReader(file.getName());
				CanvasMap canvasMap = reader.loadCanvas();
	
				this.collection.addCanvas(file.getName().substring(0, file.getName().indexOf(".")), canvasMap);
	
				if (!canvasMap.getMaps().isEmpty()) {
					final Map<Player, List<Integer>> mapSet = canvasMap.getMaps();
					
					for (Player player : canvasMap.getPlayers())
						for (Integer id : mapSet.get(player)) {
							MapView mapView = Bukkit.getMap(id);
						
							canvasMap.getRenderer().removeAllRenders(mapView);
							canvasMap.getRenderer().addRender(mapView, canvasMap.getRenderer());
						}
				}
				
				this.developerLog("Manager", "Loaded -> &8[&7FileName: &c" + fileName + "&7, Serialized: &b" + getSerializeName(canvasMap.toString()) + "&8]");
				
				return true;
			}
		}

		return false;
	}

	public boolean unload(String fileName) {
		fileName = this.getFileByName(fileName);
		
		if (this.collection.exist(fileName)) {
			final CanvasMap canvasMap = collection.getCanvasMap(fileName);

			if (!canvasMap.getMaps().isEmpty()) {
				final Map<Player, List<Integer>> mapSet = canvasMap.getMaps();
				
				for (Player player : canvasMap.getPlayers())
					for (Integer id : mapSet.get(player))
						canvasMap.getRenderer().removeAllRenders(Bukkit.getMap(id));
					
			}
			
			this.collection.removeCanvas(canvasMap);
			
			this.developerLog("Manager", "Unloaded -> &8[&7FileName: &c" + fileName + "&7, Serialized: &b" + getSerializeName(canvasMap.toString()) + "&8]");
		
			return true;
		}

		return false;

	}

	public boolean reload(String fileName) {
		fileName = this.getFileByName(fileName);

		if (collection.exist(fileName)) {
			/*this.unload(fileName);
			this.load(fileName);*/
			
			if (unload(fileName))
				if (load(fileName))
					return true;
		}
		
		return false;
	}

	
	
	public void loadAll() {
		for (File file : MapAPI.canvasFolder.listFiles())
			this.load(file.getName());
	}

	public void unloadAll() {
		this.collection.clear();
	}


	
	public String getSerializeName(String name) {
		return name.substring(name.indexOf("@"), name.length());
	}
	
	public String getFileByName(String fileName) {
		if (fileName.contains("."))
			fileName = fileName.substring(0, fileName.indexOf("."));
		
		return fileName;
	}
	
	
	
	public int getMaxMapId() {
		int id = 0;
		while (isMapIdExist(id))
			id++;

		return id;
	}
	
	public boolean isMapIdExist(int id) {
		try {
			return Bukkit.getMap(id) != null;
		} catch (Throwable ex) {
			return false;
		}
	}
}

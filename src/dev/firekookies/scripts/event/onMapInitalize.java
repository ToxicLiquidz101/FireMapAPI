package dev.firekookies.scripts.event;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;

import dev.firekookies.scripts.IO.FileRender;
import dev.firekookies.scripts.canvas.CanvasMap;
import dev.firekookies.scripts.canvas.CanvasMapCollection;
import dev.firekookies.scripts.canvas.MapManager;

public class onMapInitalize implements Listener {
	private CanvasMapCollection collection = new CanvasMapCollection();

	@EventHandler
	public void onMapInitializeEvent(MapInitializeEvent event) {
		this.addMap(event.getMap()); // Add the map to the json File
	}
	
	private void addMap(MapView mapView) {
		for (CanvasMap canvasMap : CanvasMapCollection.getCanvasMapList().values())
			if (canvasMap.getMaps() != null) {
				final Map<Player, List<Integer>> maps = canvasMap.getMaps();
				
				for (Player player : maps.keySet())
					try {
						if (maps.get(player).contains(mapView.getId())) {
							final String fileName = this.collection.getFileName(canvasMap);
							
							FileRender fileRender = new FileRender(fileName);
							MapManager manager = new MapManager();
							
							manager.unload(fileName);
							
							fileRender.load();
							fileRender.addMap(player, mapView.getId());
							fileRender.save();

							manager.load(fileName);
							
						}
					} catch (NullPointerException e) {
					}
			}
	}
}

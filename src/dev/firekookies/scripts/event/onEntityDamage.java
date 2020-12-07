package dev.firekookies.scripts.event;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftItem;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import dev.firekookies.scripts.IO.FileRender;
import dev.firekookies.scripts.canvas.CanvasMap;
import dev.firekookies.scripts.canvas.CanvasMapCollection;
import dev.firekookies.scripts.canvas.MapManager;

@SuppressWarnings("deprecation")
public class onEntityDamage implements Listener {
	private CanvasMapCollection collection = new CanvasMapCollection();
	
	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent event) {
		if (event.getEntityType() == EntityType.DROPPED_ITEM) {
			CraftItem craftItem = (CraftItem) event.getEntity();
			ItemStack itemStack = craftItem.getItemStack();

			if (itemStack.getType() == Material.FILLED_MAP) 
				this.removeMap((MapMeta) itemStack.getItemMeta());
		}
	}
	
	private void removeMap(MapMeta mapMeta) {
		loop: for (CanvasMap canvasMap : CanvasMapCollection.getCanvasMapList().values())
			for (Player player : canvasMap.getPlayers())
				if (canvasMap.getMaps().get(player).contains(mapMeta.getMapId()))
					for (Integer id : canvasMap.getMaps().get(player))
						if (id == mapMeta.getMapId()) {
							final String fileName = this.collection.getFileName(canvasMap);

							FileRender fileRender = new FileRender(fileName);
							MapManager manager = new MapManager();
							
							manager.unload(fileName);

							fileRender.load();
							fileRender.removeMap(player, id);
							fileRender.save();
							
							manager.load(fileName);

							break loop;
						}
	}
}

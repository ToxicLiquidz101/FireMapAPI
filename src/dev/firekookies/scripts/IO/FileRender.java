package dev.firekookies.scripts.IO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/* Document
 * 
 * MapCanvas access. Able to write line of instruction
 * that will be exacuted on FireMap API when load or reloaded.
 * 
 */

@Nonnull
public class FileRender {
	private JsonReader reader;
	private JsonWriter writer;

	private LinkedList<JSONObject> listOfObjects;

	private Map<Player, List<Integer>> maps;

	public FileRender(String fileName) {
		this.reader = new JsonReader(fileName);
		this.writer = new JsonWriter(fileName);

		this.listOfObjects = new LinkedList<>();
		this.maps = new HashMap<>();
	}

	public void draw(boolean draw) {
		this.listOfObjects.add(writer.setDraw(draw));
	}

	public void drawText(int x, int y, int xb, String text) {
		this.listOfObjects.add(writer.setText(x, y, xb, text));
	}

	public void drawPixel(String color, int x, int y) {
		this.listOfObjects.add(writer.setPixel(color, x, y));
	}

	public void drawLine(String color, int x1, int y1, int x2, int y2) {
		this.listOfObjects.add(writer.setLine(color, x1, y1, x2, y2));
	}

	public void lineSpacing(double lineSpacing) {
		this.listOfObjects.add(writer.setLineSpacing(lineSpacing));
	}

	public void addMap(Player player, int id) {
		List<Integer> ids = new ArrayList<>();

		if (maps.containsKey(player))
			ids = new ArrayList<>(maps.get(player));

		ids.add(id);

		if (maps.containsKey(player))
			this.maps.replace(player, ids);
		else
			this.maps.put(player, ids);
	}

	public void addMap(Player player, List<Integer> id) {
		if (maps.containsKey(player))
			this.maps.replace(player, id);
	}

	public void setMaps(Map<Player, List<Integer>> maps) {
		this.maps = maps;
	}

	public void removeMap(Player player, int id) {
		if (maps.containsKey(player)) {
			List<Integer> ids = new ArrayList<>(maps.get(player));

			if (ids.contains(id)) {
				ids.remove(ids.indexOf(id));

				maps.replace(player, ids);
			}
			
			if (ids.isEmpty())
				maps.remove(player);
		}
	}

	public void removeMap(Player player) {
		if (maps.containsKey(player))
			this.maps.remove(player);
	}

	public void load() {
		for (JSONObject object : reader.load())
			if (object.get("Maps") == null)
				this.listOfObjects.add(object);

		if (reader.exist("Maps"))
			maps = reader.loadMap((JSONArray) reader.loadObject("Maps"));
	}

	public void save() {
		this.listOfObjects.add(writer.saveMap(maps));
		this.writer.save(listOfObjects);
	}
}

package dev.firekookies.scripts.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dev.firekookies.scripts.DeveloperConsole;
import dev.firekookies.scripts.MapAPI;
import dev.firekookies.scripts.canvas.CanvasMap;
import dev.firekookies.scripts.canvas.MapManager;
import dev.firekookies.scripts.canvas.color.MapColor;
import dev.firekookies.scripts.graphics.formats.Pixel;
import dev.firekookies.scripts.graphics.formats.Point;

public class JsonReader extends DeveloperConsole {
	private CanvasMap canvasMap = new CanvasMap();
	private File file;

	public JsonReader() {
		return;
	}
	
	public JsonReader(String fileName) {
		fileName = new MapManager().getFileByName(fileName);
		
		this.file = new File(MapAPI.canvasFolder + File.separator + fileName + ".json");
	}

	
	public CanvasMap loadCanvas() {
		JSONArray executionObjects = this.parseFile();

		for (int executionIndex = 0; executionIndex < executionObjects.size(); executionIndex++) {
			JSONObject executionObject = (JSONObject) executionObjects.get(executionIndex);

			// List of execution
			if (executionObject.get("Drawing") != null)
				this.loadDrawing(executionObject);

			if (executionObject.get("DrawText") != null)
				this.loadText((JSONObject) executionObject.get("DrawText"));

			if (executionObject.get("DrawPixel") != null)
				this.loadPixel((JSONObject) executionObject.get("DrawPixel"));

			if (executionObject.get("DrawLine") != null)
				this.loadLine((JSONObject) executionObject.get("DrawLine"));

			if (executionObject.get("LineSpacing") != null)
				this.loadLineSpacing(executionObject);

			if (executionObject.get("Maps") != null) {
				final Map<Player, List<Integer>> mapHash = loadMap((JSONArray) executionObject.get("Maps"));
				
				if (!mapHash.isEmpty())
					for (Player player : mapHash.keySet())
						for (Integer id : mapHash.get(player))
							this.canvasMap.addMap(player, id);
			}
			
			this.developerLog("JSON/Reader", "Loading Instruction " + executionIndex + " -> &e" + executionObject.toJSONString());
		}

		if (canvasMap != null) {
			this.developerLog("JSON/Reader", "CanvasMap: &b" + new MapManager().getSerializeName(canvasMap.toString()) + " &7has been generated.");

			return this.canvasMap;
		}

		return null;
	}


	
	public LinkedList<JSONObject> load() {
		LinkedList<JSONObject> listOfObjects = new LinkedList<>();
		JSONArray executionObjects = this.parseFile();
		
		for (int executionIndex = 0; executionIndex < executionObjects.size(); executionIndex++) {
			JSONObject executionObject = (JSONObject) executionObjects.get(executionIndex);
			listOfObjects.add(executionObject);
			
			this.developerLog("JSON/Reader", "Loading Instruction " + executionIndex + " -> &e" + executionObject.toJSONString());
		}
		
		return listOfObjects;
	}
	
	public Object loadObject(String key) {
		JSONArray executionObjects = this.parseFile();
		
		for (int executionIndex = 0; executionIndex < executionObjects.size(); executionIndex++) {
			JSONObject executionObject = (JSONObject) executionObjects.get(executionIndex);
			
			if (executionObject.get(key) != null)
				return executionObject.get(key);
		}
		
		return null;
	}
	
	public boolean exist(String key) {
		if (loadObject(key) != null)
			return true;
		return false;
	}

	public JSONArray parseFile() {
		if (!file.exists())
			return null;
		
		try {
			JSONObject parsedObject = (JSONObject) new JSONParser().parse(new FileReader(file));

			return (JSONArray) parsedObject.get("CanvasMap");
		} catch (FileNotFoundException event) {
			this.developerLog("JSON/Reader", "&4Error &9FileNotFoundException: &c" + event.getStackTrace());
			event.printStackTrace();
		} catch (IOException event) {
			this.developerLog("JSON/Reader", "&4Error &9IOException: &c" + event.getStackTrace());
			event.printStackTrace();
		} catch (ParseException event) {
			this.developerLog("JSON/Reader", "&4Error &9ParseException: &c" + event.getStackTrace());
			event.printStackTrace();
		}

		return null;
	}

	
	
	public void loadDrawing(JSONObject object) {
		this.canvasMap.setDraw((Boolean) object.get("Drawing"));
	}

	public void loadText(JSONObject object) {
		Point point = getPoint((JSONObject) object.get("Point"));
		String text = (String) object.get("Text");
		int xb = Math.toIntExact((Long) object.get("Boundary"));

		this.canvasMap.getWordProcessor().drawText(point.getX(), point.getY(), xb, text);
	}

	public void loadPixel(JSONObject object) {
		Pixel pixel = new Pixel();
		Point point = getPoint((JSONObject) object.get("Point"));

		pixel.setColor(MapColor.getByteColor((String) object.get("MapColor")));
		pixel.setX(point.getX());
		pixel.setY(point.getY());

		this.canvasMap.getCanvasProcessor().addPixel(pixel);
	}

	public void loadLine(JSONObject object) {
		Point point_1 = getPoint((JSONObject) object.get("Point-1"));
		Point point_2 = getPoint((JSONObject) object.get("Point-2"));
		String color = (String) object.get("MapColor");

		this.canvasMap.getCanvasProcessor().drawLine(color, point_1.getX(), point_1.getY(), point_2.getX(), point_2.getY());
	}

	public void loadLineSpacing(JSONObject object) {
		this.canvasMap.getWordProcessor().setLineSpacing((Double) object.get("LineSpacing"));
	}
	
	
	
	public Map<Player, List<Integer>> loadMap(JSONArray objectArray) {
		if (objectArray == null)
			return null;
		
		final Map<Player, List<Integer>> maps = new HashMap<>();
		for (int x = 0; x < objectArray.size(); x++) {
			JSONObject idObject = (JSONObject) objectArray.get(x);
			String key = idObject.keySet().toString().replace("[", "").replace("]", "");
			JSONArray idArray = (JSONArray) idObject.get(key);
			
			Player player = Bukkit.getPlayer(UUID.fromString(key));
			
			final List<Integer> ids = new ArrayList<>();
			for (int y = 0; y < idArray.size(); y++)
				ids.add(Math.toIntExact((Long) idArray.get(y)));
			maps.put(player, ids);
		}
		
		return maps;
	}

	public Point getPoint(JSONObject object) {
		Point point = new Point();

		point.setX(Math.toIntExact((Long) object.get("x")));
		point.setY(Math.toIntExact((Long) object.get("y")));

		return point;
	}
}

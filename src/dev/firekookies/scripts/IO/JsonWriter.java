package dev.firekookies.scripts.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dev.firekookies.scripts.DeveloperConsole;
import dev.firekookies.scripts.MapAPI;
import dev.firekookies.scripts.canvas.MapManager;

@Nonnull
@SuppressWarnings("unchecked")
public class JsonWriter extends DeveloperConsole {
	private File file;

	public JsonWriter() {
		return;
	}
	
	public JsonWriter(String fileName) {
		fileName = new MapManager().getFileByName(fileName);
		
		this.file = new File(MapAPI.canvasFolder + File.separator + fileName + ".json");
	}

	
	
	public void save(LinkedList<JSONObject> listOfObjects) {
		JSONObject object = new JSONObject();
		JSONArray objectArray = new JSONArray();

		for (int executionIndex = 0; executionIndex < listOfObjects.size(); executionIndex++) {
			objectArray.add(listOfObjects.get(executionIndex));
			
			this.developerLog("JSON/Writer", "Saving Instruction " + executionIndex + " -> &e" + objectArray.get(executionIndex).toString());
		}

		object.put("CanvasMap", objectArray);
		
		try (FileWriter fileWriter = new FileWriter(file)) {
			fileWriter.write(object.toJSONString());
			fileWriter.flush();

			this.developerLog("JSON/Writer", "JSON was save successfully to " + file.getAbsoluteFile());
		} catch (IOException event) {
			this.developerLog("JSON/Writer", "&4Error &9IOException: &c" + event.getStackTrace());
			event.printStackTrace();
		}
	}

	
	
	public JSONObject setDraw(boolean draw) {
		return this.getHeader("Drawing", draw);
	}

	public JSONObject setLineSpacing(double lineSpacing) {
		return this.getHeader("LineSpacing", lineSpacing);
	}

	public JSONObject setPoint(int x, int y) {
		JSONObject object = new JSONObject();

		object.put("x", x);
		object.put("y", y);

		return object;
	}

	public JSONObject saveMap(Map<Player, List<Integer>> maps) {
		JSONArray objectArray = new JSONArray();
		
		for (Player player : maps.keySet()) {
			JSONArray idArray = new JSONArray();
			
			for (Integer id : maps.get(player)) 
				idArray.add(id);
			objectArray.add(getHeader(player.getUniqueId().toString(), idArray));
		}

		return getHeader("Maps", objectArray);
	}

	public JSONObject setText(int x, int y, int xb, String text) {
		JSONObject object = new JSONObject();

		object.put("Text", text);
		object.put("Boundary", xb);
		object.put("Point", setPoint(x, y));

		return this.getHeader("DrawText", object);
	}

	public JSONObject setPixel(String color, int x, int y) {
		JSONObject object = new JSONObject();

		object.put("MapColor", color);
		object.put("Point", setPoint(x, y));

		return this.getHeader("DrawPixel", object);
	}

	public JSONObject setLine(String color, int x1, int y1, int x2, int y2) {
		JSONObject object = new JSONObject();

		object.put("MapColor", color);
		object.put("Point-1", setPoint(x1, y1));
		object.put("Point-2", setPoint(x2, y2));

		return this.getHeader("DrawLine", object);
	}

	private JSONObject getHeader(String header, Object data) {
		JSONObject object = new JSONObject();

		object.put(header, data);

		return object;
	}
}

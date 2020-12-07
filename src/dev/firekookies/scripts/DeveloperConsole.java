package dev.firekookies.scripts;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import dev.firekookies.scripts.IO.Configuration;
import dev.firekookies.scripts.IO.Options;


public class DeveloperConsole {
	public void developerLog(String directory, String message) {
		Configuration configuration = new Configuration(MapAPI.configurationFile);
		
		configuration.load();
		
		boolean developerOption = false;
		switch(directory) {
		case "Processer/Canvas":
			developerOption = (boolean) configuration.read("debug.canvasProcesser");
			
			break;
		case "Processer/Word":
			developerOption = (boolean) configuration.read("debug.wordProcesser");
			
			break;
		case "JSON/Reader":
			developerOption = (boolean) configuration.read("debug.jsonReader");
			
			break;
		case "JSON/Writer":
			developerOption = (boolean) configuration.read("debug.jsonWriter");
			
			break;
		case "Manager":
			developerOption = (boolean) configuration.read("debug.manager");
			
			break;
		}
		
		if (developerOption)
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[&6FireMapAPI&9] &8DeveloperConsole/" + directory + "&6$&8/> &7" + message));
	}
	
	public static void developerConsole(String directory, String message) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[&6FireMapAPI&9] &8DeveloperConsole/" + directory + "&6$&8/> &7" + message));
	}
	
	
	
	public void toggle(Options options, boolean toggle) {		
		Configuration configuration = new Configuration(MapAPI.configurationFile);
				
		configuration.load();
		
		switch (options) {
		case CanvasProcesser:
			configuration.write("debug.canvasProcesser", toggle);
			
			break;
		case WordProcesser:
			configuration.write("debug.wordProcesser", toggle);
			
			break;
		case JsonReader:
			configuration.write("debug.jsonReader", toggle);
			
			break;
		case JsonWriter:
			 configuration.write("debug.jsonWriter", toggle);
			 
			break;
		case Manager:
			configuration.write("debug.manager", toggle);		
			
			break;
		}
	}
}

package dev.firekookies.scripts;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import dev.firekookies.scripts.IO.Configuration;
import dev.firekookies.scripts.IO.JsonReader;
import dev.firekookies.scripts.IO.JsonWriter;
import dev.firekookies.scripts.canvas.MapManager;
import dev.firekookies.scripts.event.onEntityDamage;
import dev.firekookies.scripts.event.onItemDespawn;
import dev.firekookies.scripts.event.onMapInitalize;

public class MapAPI extends JavaPlugin {
	public static File dataFolder = null;
	public static File resourceFolder = null;
	public static File canvasFolder = null;
	public static File imageFolder = null;

	public static File configurationFile = null;

	private String command = "firemap";

	public void onEnable() {
		this.registerEvents();
		this.reigsterCommands();
		this.registerConfiguration();
	}

	public void onDisable() {
		// Disable
	}

	public void registerEvents() {
		// this.getServer().getPluginManager().registerEvents(new onInventoryClick(),  this);
		this.getServer().getPluginManager().registerEvents(new onEntityDamage(), this);
		this.getServer().getPluginManager().registerEvents(new onItemDespawn(), this);
		this.getServer().getPluginManager().registerEvents(new onMapInitalize(), this);
	}

	public void reigsterCommands() {
		this.getCommand(command).setExecutor(new onMapCommands());
	}

	public void registerConfiguration() {
		try {
			String fileLocation = MapAPI.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();

			dataFolder = new File(fileLocation.substring(fileLocation.indexOf(":") - 1, fileLocation.lastIndexOf("/")) + File.separator + "FireMap");
			resourceFolder = new File(dataFolder + File.separator + "resources");
			canvasFolder = new File(resourceFolder + File.separator + "canvas");
			imageFolder = new File(resourceFolder + File.separator + "images");

			configurationFile = new File(dataFolder + File.separator + "debugConfig.yml");

			
			if (!dataFolder.exists()) {
				dataFolder.mkdir();

				DeveloperConsole.developerConsole("Configuration", "Created Directory -> " + dataFolder.getAbsolutePath());
			}

			if (!resourceFolder.exists()) {
				resourceFolder.mkdir();

				DeveloperConsole.developerConsole("Configuration", "Created Directory -> " + resourceFolder.getAbsolutePath());
			}

			if (!canvasFolder.exists()) {
				canvasFolder.mkdir();

				DeveloperConsole.developerConsole("Configuration", "Created Directory -> " + canvasFolder.getAbsolutePath());
			}

			if (!imageFolder.exists()) {
				imageFolder.mkdir();

				DeveloperConsole.developerConsole("Configuration", "Created Directory -> " + imageFolder.getAbsolutePath());
			}

			if (!configurationFile.exists())
				try {
					configurationFile.createNewFile();

					Configuration configuration = new Configuration(configurationFile);
							
					configuration.write("debug.canvasProcesser", false);
					configuration.write("debug.wordProcesser", false);
					configuration.write("debug.jsonReader", true);
					configuration.write("debug.jsonWriter", true);
					configuration.write("debug.manager", true);		
					
					DeveloperConsole.developerConsole("Configuration", "Created File -> " + configurationFile.getAbsoluteFile());
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			// Load canvas from resources as canvasMap
			new MapManager().loadAll();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}

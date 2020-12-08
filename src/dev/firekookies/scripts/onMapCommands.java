package dev.firekookies.scripts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import dev.firekookies.scripts.IO.Options;
import dev.firekookies.scripts.canvas.MapManager;

public class onMapCommands extends DeveloperConsole implements TabExecutor {
	private final MapManager manager = new MapManager();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		try {
			switch (args[0].toUpperCase()) {
			case "DEBUG":
				switch (args[1].toUpperCase()) {
				case "PROCESSOR":
					switch (args[2].toUpperCase()) {
					case "WORD":
						this.toggle(Options.WordProcesser, 4, args[3], args, sender);

						break;
					case "CANVAS":
						this.toggle(Options.CanvasProcesser, 4, args[3], args, sender);
						break;
					}

					break;
				case "MANAGER":
					this.toggle(Options.Manager, 3, args[2], args, sender);

					break;
				case "JSON":
					switch (args[2].toUpperCase()) {
					case "READER":
						this.toggle(Options.JsonReader, 4, args[3], args, sender);

						break;
					case "WRITER":
						this.toggle(Options.JsonWriter, 4, args[3], args, sender);

						break;
					}

					break;
				}

				break;
			case "RELOAD":
				switch (args.length) {
				case 1:
					// Reload the api

					sender.sendMessage("This part is still under construction.");

					break;
				case 2:
					manager.reload(args[1]);

					if (sender instanceof Player)
						sender.sendMessage(ChatColor.GREEN + "Reloaded canvas check console for more information");

					break;
				}

				break;
			case "UNLOAD":
				manager.unload(args[1]);

				if (sender instanceof Player)
					sender.sendMessage(ChatColor.GREEN + "Unloaded canvas check console for more information!");

				break;
			case "LOAD":
				manager.load(args[1]);

				if (sender instanceof Player)
					sender.sendMessage(ChatColor.GREEN + "Loaded canvas check console for more information!");

				break;
			case "LIST":
				for (String fileName : getMapFileList())
					sender.sendMessage(ChatColor.GOLD + "Founded " + fileName);

				break;
			case "HELP":
				
				//this.manager.createCanvas((Player) sender, "map");
				break;
			}
			
			return true;
		} catch (NullPointerException e) {
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		
		sender.sendMessage(ChatColor.RED + "Argument was not specified.");
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String name, String[] args) {
		List<String> argumentList = new ArrayList<>();

		switch (args.length) {
		case 1:
			argumentList.add("debug");
			argumentList.add("reload");
			argumentList.add("load");
			argumentList.add("unload");
			argumentList.add("list");
			argumentList.add("help");

			break;
		case 2:
			switch (args[0].toUpperCase()) {
			case "DEBUG":
				argumentList.add("processor");
				argumentList.add("manager");
				argumentList.add("json");

				break;
			case "RELOAD":
				for (String fileName : getMapFileList())
					argumentList.add(fileName);

				break;
			case "LOAD":
				for (String fileName : getMapFileList())
					argumentList.add(fileName);

				break;
			case "UNLOAD":
				for (String fileName : getMapFileList())
					argumentList.add(fileName);

				break;
			}

			break;
		case 3:
			switch (args[1].toUpperCase()) {
			case "PROCESSOR":
				argumentList.add("word");
				argumentList.add("canvas");

				break;
			case "JSON":
				argumentList.add("reader");
				argumentList.add("writer");
				
				break;
			case "MANAGER":
				argumentList.add("true");
				argumentList.add("false");
			}
		case 4:
			switch (args[2].toUpperCase()) {
			case "WORD":
				argumentList.add("true");
				argumentList.add("false");
				
				break;
			case "CANVAS":
				argumentList.add("true");
				argumentList.add("false");
				
				break;
			case "READER":
				argumentList.add("true");
				argumentList.add("false");
				
				break;
			case "WRITER":
				argumentList.add("true");
				argumentList.add("false");
				
				break;
			}
		}

		return argumentList;
	}

	private void toggle(Options options, int length, String debugBool, String[] args, CommandSender sender) {

		// Check if args is true or false
		final boolean option;
		if (debugBool.toUpperCase().equals("TRUE"))
			option = true;
		else if (debugBool.toUpperCase().equals("FALSE"))
			option = false;
		else {
			sender.sendMessage(ChatColor.RED + "Invalid boolean input");

			return;
		}

		// Render out the developer debug message toggle
		if (args.length == length)
			if (option)
				this.toggle(options, true);
			else
				this.toggle(options, false);

		sender.sendMessage(ChatColor.GREEN + "Debug for " + options.toString() + " has been set to " + option + ".");
	}

	private List<String> getMapFileList() {
		List<String> mapNameList = new ArrayList<>();

		for (String fileName : new File(MapAPI.canvasFolder.getAbsolutePath()).list())
			mapNameList.add(fileName);

		return mapNameList;
	}
}

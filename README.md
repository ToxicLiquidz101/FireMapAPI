# FireMapAPI
Minecraft API that render maps in custom.


Use this API add this API to the build path.
In any class type "FileRender <preferredReferrence> = new FileRender(<FileName>);
In any class to render reloads etc and even create maps type "MapManager <referredReferrence> = new MapManager();
	
	manager.createCanvas(player, <FileName>); // Will create a canvas givinn to the player with the corresponding map file instructions
	manager.reload(<FileName>); // reload specific file ps there ingame commands 
	manager.load(<FileName);
	manager.unload(<FileName>);
	
Now you can access this file and create new information FYI I havent created a load function this will overright existing file information. 
// TODO Fix override I believe this is fix by the load() function would have to double check.
The Following Method will create any graphics you desire and let the API handle the rest.

		fileRender.load();
		fileRender.addMap(player, id);
		fileRender.draw(draw);
		fileRender.drawLine(color, x1, y1, x2, y2);
		fileRender.drawPixel(color, x, y);
		fileRender.drawText(x, y, xb, text);
		fileRender.lineSpacing(lineSpacing);
		fileRender.removeMap(player);
		fileRender.removeMap(player, id);
		fileRender.setMaps(maps);
		fileRender.save();
		
		manager.createCanvas((Player) sender, "map");
    
Theses are all the submethods that are use threw out the program.
    
	NOTE:
      Do not touch unless you know what you are doing
      
      fileRender.addMap(player, id); // Add the player to the file with a set map ID.
      fileRender.removeMap(player); // Remove the player from the file with set of IDs.
	  fileRender.removeMap(player, id); // Rremove the player ID from set without tampering with anyother id that the player has.
	  fileRender.setMaps(maps); // Set a set of Hash<Player, List<Integer>> this is basicly the whole set of information.
    
 	If I am not allow to touch those function what can I play with?
   
      fileRender.load(); // I dont know what this does excally yet but it should load the information if not then PM me when I shall fix the issue
	  fileRender.draw(draw); // I order to render this information switch to true else switch to false.
	  fileRender.drawLine(color, x1, y1, x2, y2); // color is the color of the line, x1 and x2 are the point position of starting or ending same for x2 and y1
      fileRender.drawPixel(color, x, y); // X and y is the position of the pixel and color is the color of the pixel. ...
	  fileRender.drawText(x, y, xb, text); // x and y are the start position when rendering the text NOTE left upper conner. xb is the x boundery before indenting to the next line.
	  fileRender.lineSpacing(lineSpacing); // Linespacing is the space between text when using the WordProcesser preferred spacing is 1.5
      fileRender.save(); // This function is very very important inorder to save your information



// Developer Note

In the config you will be able to change debug mode to switch on logs to view information if you need to debug.

Also add a default png 128x128 named "frame.png" in images for background clear else just leave it clear this is optional unless your creating an animation. Animation isnt yet implemented.

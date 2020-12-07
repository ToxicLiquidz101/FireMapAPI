package dev.firekookies.scripts.canvas.color;

import javax.annotation.Nonnull;

import org.bukkit.map.MapPalette;


public interface MapColor {
	final String startDelimiter = "§";
	final String endDelimiter = ";";
	
	
	final String BLACK = mixColor(0, 0, 0);
	final String DARK_BLUE = mixColor(0, 0, 170);
	final String DARK_GREEN = mixColor(0, 170, 0);
	final String DARK_AQUA = mixColor(0, 170, 170);
	final String DARK_RED = mixColor(170, 0, 0);
	final String DARK_PURPLE = mixColor(170, 0, 170);
	final String GOLD = mixColor(255, 170, 0);
	final String GRAY = mixColor(170, 170, 170);
	final String DARK_GRAY = mixColor(85, 85, 85);
	final String BLUE = mixColor(85, 85, 255);
	final String GREEN = mixColor(85, 255, 85);
	final String AQUA = mixColor(85, 255, 255);
	final String RED = mixColor(255, 85, 85);
	final String LIGHT_PURPLE = mixColor(255, 85, 255);
	final String YELLOW = mixColor(255, 255, 85);
	final String WHITE = mixColor(255, 255, 255);
	final String RESET = startDelimiter + "-49" + endDelimiter;

	
	
	@SuppressWarnings("deprecation")
	static String mixColor(@Nonnull int red, @Nonnull int blue, @Nonnull int green) {
		if (!(red > 255 || green > 255 || blue > 255))
			return startDelimiter + MapPalette.matchColor(red, blue, green) + endDelimiter;
		return RESET;
	}
	
	static String getByteCode(@Nonnull String byteCode) {
		if (containsColor(byteCode))
			return (String) byteCode.substring(byteCode.indexOf(startDelimiter), byteCode.indexOf(endDelimiter) + 1);
		return RESET;
	}
	
	static String getByteCode(@Nonnull byte byteCode) {
		return (String) startDelimiter + String.valueOf(byteCode) + endDelimiter;
	}
	
	static byte getByteColor(@Nonnull String byteCode) {
		if (containsColor(byteCode)) 
			return (byte) Byte.parseByte(byteCode.substring(byteCode.indexOf(startDelimiter) + 1, byteCode.indexOf(endDelimiter)));
		return -49;
	}

	static boolean containsColor(@Nonnull String byteCode) {
		if (byteCode.contains(startDelimiter) && byteCode.contains(endDelimiter))
			if (byteCode.indexOf(startDelimiter) < byteCode.indexOf(endDelimiter))
				return true;
		return false;
	}
}

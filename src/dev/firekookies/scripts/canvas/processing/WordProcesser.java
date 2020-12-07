package dev.firekookies.scripts.canvas.processing;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.map.MinecraftFont;

import dev.firekookies.scripts.DeveloperConsole;
import dev.firekookies.scripts.canvas.color.MapColor;
import dev.firekookies.scripts.graphics.formats.Point;
import dev.firekookies.scripts.graphics.formats.Text;

/*
 * Document
 * 
 * This class handles word processing.
 * And color mapping to charactersets.
 * 
 */

@Nonnull
public class WordProcesser extends DeveloperConsole {

	// Execute in parallel squence
	private List<Character> characters = new ArrayList<>();
	private List<Byte> byteCodes = new ArrayList<>();
	
	private List<Text> texts = new ArrayList<>();

	private double lineSpacing = 0;

	// Stripe the text of byteCode and add it a parallel list
	public void parseColor(String text) {
		this.clearParrallelList();

		byte byteCode = MapColor.getByteColor(MapColor.RESET);

		for (int charIndex = 0; charIndex < text.length(); charIndex++) {
			char character = text.charAt(charIndex);

			// Note to self fix MapColor to work with chars not strings
			if (String.valueOf(character).equals(MapColor.startDelimiter)) {
				if (text.substring(charIndex, text.length()).contains(MapColor.endDelimiter)) {
					int endDelimiterIndex = text.indexOf(MapColor.endDelimiter, charIndex);
					String tempByteCode = text.substring(charIndex + 1, endDelimiterIndex);

					byteCode = Byte.parseByte(tempByteCode);
					charIndex += tempByteCode.length() + 1;
				}
			} else {
				this.developerLog("Processer/Word", "ByteCode -> " +  MapColor.getByteCode(byteCode) + " &7Text -> " + text.substring(charIndex, charIndex + 1));

				this.characters.add(character);
				this.byteCodes.add(byteCode);
			}
		}
	}

	// Render the stripe text and add the byte the parallel list sequence the text
	// then format the color.
	// This function also position the text in the render.
	public void renderText(int x, int y, int xb) {
		List<String> tempTexts = new ArrayList<>(concatenateWords(concatenateCharacters(), x, xb));

		this.concatenateColorInWord(tempTexts, x, y);

		this.clearParrallelList();
	}

	public void drawText(int x, int y, int xb, String text) {
		this.parseColor(text);
		this.renderText(x, y, xb);
	}

	public void addText(int x, int y, String text) {
		this.texts.add(new Text(new Point(x, y), text));
	}

	public void setTexts(List<Text> texts) {
		this.texts = texts;
	}

	public List<Text> getTexts() {
		return this.texts;
	}

	public int getFontWidth(String text) {
		return MinecraftFont.Font.getWidth(text);
	}

	public int getFontHeight() {
		return MinecraftFont.Font.getHeight();
	}

	public void setLineSpacing(double lineSpace) {
		this.lineSpacing = lineSpace;
	}

	public double getLineSpacing() {
		return this.lineSpacing;
	}

	private void clearParrallelList() {
		this.characters = new ArrayList<>();
		this.byteCodes = new ArrayList<>();
	}

	public void clearText() {
		this.texts.clear();
	}

	// concatenate
	private List<String> concatenateWords(String text, int x, int xb) {
		List<String> texts = new ArrayList<>();

		String builder = "";
		for (String word : text.split(" ")) {
			int textWidth = getFontWidth(builder + " " + word);

			if (xb - x < textWidth) {
				this.developerLog("Processer/Word", builder);

				texts.add(builder);

				builder = word + " ";
			} else
				builder += word + " ";
		}

		this.developerLog("Processer/Word", builder);

		texts.add(builder);

		return texts;
	}

	private void concatenateColorInWord(List<String> texts, int x, int y) {
		String builder = "";

		int charIndex = 0;
		for (String builderText : texts) {
			Text text = new Text();

			text.getPoint().setX(x);
			text.getPoint().setY(y);

			builder = "";
			for (int index = 0; index < builderText.length() - 1; index++)
				builder += MapColor.startDelimiter + byteCodes.get(charIndex + index) + MapColor.endDelimiter + characters.get(charIndex + index);
			text.setText(builder);

			y += getFontHeight() * getLineSpacing();
			this.texts.add(text);

			charIndex += builderText.length();
		}
	}
	
	private String concatenateCharacters() {
		String text = "";
		
		for (Character character : characters)
			text += character;
		
		return text;
	}
}
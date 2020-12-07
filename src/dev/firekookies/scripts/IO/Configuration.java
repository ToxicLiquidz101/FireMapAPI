package dev.firekookies.scripts.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Configuration {
	private FileConfiguration fileConfig;

	private File file;

	public Configuration(File file) {
		this.fileConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
		this.file = file;
	}

	public void save() {
		try {
			this.fileConfig.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {
		try {
			this.fileConfig.load(this.file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void write(String path, Object data) {
		this.fileConfig.set(path, data);
		save();
	}

	public Object read(String path) {
		return this.fileConfig.get(path);
	}

	public void writeList(String path, List<?> data) {
		write(path, data);
	}

	public List<Boolean> readListBoolean(String path) {
		return this.fileConfig.getBooleanList(path);
	}

	public List<Byte> readListByte(String path) {
		return this.fileConfig.getByteList(path);
	}

	public List<Double> readListBouble(String path) {
		return this.fileConfig.getDoubleList(path);
	}

	public List<Float> readListFloate(String path) {
		return this.fileConfig.getFloatList(path);
	}

	public List<Integer> readListInteger(String path) {
		return this.fileConfig.getIntegerList(path);
	}

	public List<String> readListString(String path) {
		return this.fileConfig.getStringList(path);
	}

	public List<Long> readListLong(String path) {
		return this.fileConfig.getLongList(path);
	}
}

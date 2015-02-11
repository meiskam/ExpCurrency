package org.shininet.bukkit.expcurrency;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jnbt.CompoundTag;
import org.jnbt.FloatTag;
import org.jnbt.IntTag;
import org.jnbt.NBTInputStream;
import org.jnbt.NBTOutputStream;
import org.jnbt.Tag;

public final class PlayerNBT {
	//vars

	private Map<String, Tag> tagMap;
	private OfflinePlayer playerOff;
	private File playerFile;

	private int exp;
	private int level;
	private float percent;

	private static File playerdataFolderFile = new File(Bukkit.getServer().getWorlds().get(0).getWorldFolder(), "playerdata");
	private static String keyLevel = "XpLevel";
	private static String keyPercent = "XpP";
	private static String keyTotal = "XpTotal";

	//constructor

	public PlayerNBT(OfflinePlayer playerOffline) throws PlayerNBTException {
		playerOff = playerOffline;

		if (!playerOff.hasPlayedBefore()) {
			throw new PlayerNBTException("Player does not have an account");
		}

		playerFile = new File(playerdataFolderFile, playerOff.getUniqueId().toString().concat(".dat"));

		NBTInputStream playerNBTStream;
		try {
			playerNBTStream = new NBTInputStream(new FileInputStream(playerFile));
		} catch (IOException e) {
			throw new PlayerNBTException("Error opening NBT stream: " + playerFile.getName(), e);
		}

		Tag tagInput = null;
		try {
			tagInput = playerNBTStream.readTag();
		} catch (EOFException e) {
			// break while, end of file
		} catch (IOException e) {
			throw new PlayerNBTException("Error reading from NBT stream: " + playerFile.getName(), e);
		} finally {
			try {
				playerNBTStream.close();
			} catch (IOException e) {
				throw new PlayerNBTException("Error closing NBT stream: " + playerFile.getName(), e);
			}
		}
		CompoundTag cTagInput = null;
		try {
			cTagInput = (CompoundTag)tagInput;
		} catch (ClassCastException e) {
			throw new PlayerNBTException("NBT file did not contain a compound", e);
		}

		tagMap = new LinkedHashMap<String, Tag>(cTagInput.getValue());

		IntTag XpLevel;
		try {
			XpLevel = (IntTag) tagMap.get(keyLevel);
			level = XpLevel.getValue();
		} catch (ClassCastException e) {
			tagMap.remove(keyLevel);
		}
		if (!tagMap.containsKey(keyLevel)) {
			tagMap.put(keyLevel, new IntTag(keyLevel, 0));
			level = 0;
		}

		FloatTag XpP;
		try {
			XpP = (FloatTag) tagMap.get(keyPercent);
			percent = XpP.getValue();
		} catch (ClassCastException e) {
			tagMap.remove(keyPercent);
		}
		if (!tagMap.containsKey(keyPercent)) {
			tagMap.put(keyPercent, new FloatTag(keyPercent, 0F));
			percent = 0F;
		}

		exp = ExpCurrency.getTotalExp(level, percent);
	}
	
	//method

	void calculateExp() {
		setExp(ExpCurrency.getTotalExp(level, percent));
	}

	public int getLevel() {
		return level;
	}

	public float getP() {
		return percent;
	}

	public int getExp() {
		return exp;
	}

	void _setLevel(int amount) {
		level = amount;
		tagMap.put(keyLevel, new IntTag(keyLevel, amount));
	}

	public void setLevel(int amount) {
		_setLevel(amount);
		calculateExp();
	}

	void _setP(float amount) {
		percent = amount;
		tagMap.put(keyPercent, new FloatTag(keyPercent, amount));
	}

	public void setP(float amount) {
		_setP(amount);
		calculateExp();
	}

	public void setExp(int amount) {
		if (amount < 0) {
			amount = 0;
		}

		tagMap.put(keyTotal, new IntTag(keyTotal, amount));

		int level = 0;
		float percent = 0;
		int expNextLevel = 0;

		while ((expNextLevel = ExpCurrency.getExpNextLevel(level)) < amount) {
			level++;
			amount -= expNextLevel;
		}
		percent = (float) amount / (float) expNextLevel;

		_setLevel(level);
		_setP(percent);
	}

	public void addExp(int amount) {
		setExp(exp + amount);
	}

	public void removeExp(int amount) {
		setExp(exp - amount);
	}

	public void save() throws PlayerNBTException {
		NBTOutputStream playerNBTStream;
		try {
			playerNBTStream = new NBTOutputStream(new FileOutputStream(playerFile));
		} catch (IOException e) {
			throw new PlayerNBTException("Error opening NBT stream: " + playerFile.getName(), e);
		}

		try {
			playerNBTStream.writeTag(new CompoundTag("", tagMap));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			playerNBTStream.close();
		} catch (IOException e) {
			throw new PlayerNBTException("Error closing NBT stream: " + playerFile.getName(), e);
		}
	}
}

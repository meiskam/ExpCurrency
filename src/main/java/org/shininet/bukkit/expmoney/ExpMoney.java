package org.shininet.bukkit.expmoney;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jnbt.NBTInputStream;
import org.jnbt.Tag;

public class ExpMoney extends JavaPlugin {
	private ExpMoneyCommandExecutor commandExecutor;
	private ExpMoneyListener listener;
	private File playersFolderFile;
	private File playerdataFolderFile;

	@Override
	public void onEnable() {
		listener = new ExpMoneyListener(this);
		commandExecutor = new ExpMoneyCommandExecutor(this);
		getServer().getPluginManager().registerEvents((Listener) listener, (Plugin) this);
		getCommand("ExpMoney").setExecutor(commandExecutor);
		playersFolderFile = new File(getServer().getWorlds().get(0).getWorldFolder(), "players");
		playerdataFolderFile = new File(getServer().getWorlds().get(0).getWorldFolder(), "playerdata");
	}

	@SuppressWarnings("deprecation")
	public Object getPlayerNBTMap(String player) {
		return getPlayerNBTMap(getServer().getOfflinePlayer(player));
	}

	public Object getPlayerNBTMap(OfflinePlayer player) {
		return getPlayerNBTMap(player, true);
	}

	private Object getPlayerNBTMap(OfflinePlayer player, boolean isUUIDFilename) {
		if (!player.hasPlayedBefore()) {
			return null;
		}

		File playerFile = new File(playerdataFolderFile, ((isUUIDFilename)?(player.getUniqueId().toString()):(player.getName())).concat(".dat"));
		NBTInputStream playerNBTStream;
		try {
			playerNBTStream = new NBTInputStream(
					new FileInputStream(playerFile));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		Map<String, Tag> tagsMap = new LinkedHashMap<String, Tag>();

		try {
			Tag tag;
			while (true) {
				tag = playerNBTStream.readTag();
				tagsMap.put(tag.getName(), tag);
			}
		} catch (EOFException e) {
			// break while, end of file
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				playerNBTStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return tagsMap;
	}
}

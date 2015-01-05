package org.shininet.bukkit.expmoney;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
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
		
		if (getServer().getPluginManager().getPlugin("Vault") != null){
			final ServicesManager sm = getServer().getServicesManager();
			sm.register(Economy.class, new VaultInterface(this), this, ServicePriority.Highest);
			getLogger().info("Registered Vault interface.");
		}
	}

	@SuppressWarnings("deprecation")
	protected Object getPlayerNBTMap(String player) {
		return getPlayerNBTMap(getServer().getOfflinePlayer(player));
	}

	protected File getPlayerFile(OfflinePlayer player) {
		return new File(playerdataFolderFile, player.getUniqueId().toString().concat(".dat"));
	}

	private Object getPlayerNBTMap(OfflinePlayer player) {
		if (!player.hasPlayedBefore()) {
			return null;
		}

		File playerFile = getPlayerFile(player);
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

	protected boolean isPlayerOnline(String player) {
		return (getServer().getPlayer(player) != null);
	}

	protected boolean isPlayerOnline(OfflinePlayer player) {
		return (player.getPlayer() != null);
	}
}

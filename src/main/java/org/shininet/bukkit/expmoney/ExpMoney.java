package org.shininet.bukkit.expmoney;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ExpMoney extends JavaPlugin {
	private ExpMoneyCommandExecutor commandExecutor;
	//private ExpMoneyListener listener;
	VaultInterface vaultInterface;

	@Override
	public void onEnable() {
		//listener = new ExpMoneyListener(this);
		commandExecutor = new ExpMoneyCommandExecutor(this);
		//getServer().getPluginManager().registerEvents((Listener) listener, (Plugin) this);
		getCommand("ExpMoney").setExecutor(commandExecutor);

		if (getServer().getPluginManager().getPlugin("Vault") != null){
			final ServicesManager sm = getServer().getServicesManager();
			sm.register(Economy.class, vaultInterface = new VaultInterface(this), this, ServicePriority.Highest);
			getLogger().info("Registered Vault interface.");
		}
	}

	boolean isPlayerOnline(String player) {
		return (getServer().getPlayer(player) != null);
	}

	boolean isPlayerOnline(OfflinePlayer player) {
		return (player.getPlayer() != null);
	}

	static int getExpNextLevel(int level) {// http://minecraft.gamepedia.com/Experience#Leveling_up
		if (level >= 31) {
			return (9 * level) - 158;
		} else if (level >= 16) {
			return (5 * level) - 38;
		} else {
			return (2 * level) + 7;
		}
	}

	static int getTotalExp(int level) {
		return getTotalExp(level, (float) 0);
	}

	static int getTotalExp(int level, float percent) {// http://minecraft.gamepedia.com/Experience#Leveling_up
		if (level >= 31) {
			return (int) ((4.5 * level * level) - (162.5 * level) + 2220 + (percent * getExpNextLevel(level)));
		} else if (level >= 16) {
			return (int) ((2.5 * level * level) - (40.5 * level) + 360 + (percent * getExpNextLevel(level)));
		} else {
			return (int) ((level * level) + (6 * level) + (percent * getExpNextLevel(level)));
		}
	}
}

package org.shininet.bukkit.expmoney;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ExpMoney extends JavaPlugin {
	private ExpMoneyCommandExecutor commandExecutor;
	private ExpMoneyListener listener;

	@Override
	public void onEnable() {
		listener = new ExpMoneyListener(this);
		commandExecutor = new ExpMoneyCommandExecutor(this);
		getServer().getPluginManager().registerEvents((Listener) listener, (Plugin) this);
		getCommand("ExpMoney").setExecutor(commandExecutor);
	}
}

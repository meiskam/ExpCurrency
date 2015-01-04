package org.shininet.bukkit.expmoney;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ExpMoneyCommandExecutor implements CommandExecutor {
	ExpMoney plugin;

	public ExpMoneyCommandExecutor(ExpMoney expMoney) {
		plugin = expMoney;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}
}

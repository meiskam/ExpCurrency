package org.shininet.bukkit.expmoney;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpMoneyCommandExecutor implements CommandExecutor {
	ExpMoney plugin;

	public ExpMoneyCommandExecutor(ExpMoney expMoney) {
		plugin = expMoney;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("ExpMoney")) {
            return false;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
            	sender.sendMessage("This command must be used by a player");
            	return true;
            }
            sender.sendMessage("Balance: " + plugin.vaultInterface.format(plugin.vaultInterface.getBalance((Player) sender)));
            return true;
        }
		return false;
	}
}

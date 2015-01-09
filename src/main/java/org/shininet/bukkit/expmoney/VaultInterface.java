package org.shininet.bukkit.expmoney;

import java.text.DecimalFormat;
import java.util.List;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class VaultInterface implements Economy {
	private static DecimalFormat formatter = new DecimalFormat("#,###");
	private static EconomyResponse notImpl = new EconomyResponse(0D, 0D, ResponseType.NOT_IMPLEMENTED, (String)null);
	private ExpMoney plugin;

	public VaultInterface(ExpMoney expMoney) {
		plugin = expMoney;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getName() {
		return plugin.getName();
	}

	@Override
	public boolean hasBankSupport() {
		return false; //TODO add bank support
	}

	@Override
	public int fractionalDigits() {
		return 0;
	}

	@Override
	public String format(double amount) {
		return formatter.format(amount);
	}

	@Override
	public String currencyNamePlural() {
		return "Experience";
	}

	@Override
	public String currencyNameSingular() {
		return "Experience";
	}

	@Deprecated
	@Override
	public boolean hasAccount(String playerName) {
		return hasAccount(plugin.getServer().getOfflinePlayer(playerName));
	}

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		if (plugin.isPlayerOnline(player)) {
			return true;
		}
		return player.hasPlayedBefore();
	}

	@Deprecated
	@Override
	public boolean hasAccount(String playerName, String worldName) {
		return hasAccount(playerName);
	}

	@Override
	public boolean hasAccount(OfflinePlayer playerOff, String worldName) {
		return hasAccount(playerOff);
	}

	@Deprecated
	@Override
	public double getBalance(String playerName) {
		return getBalance(plugin.getServer().getOfflinePlayer(playerName));
	}

	@SuppressWarnings("static-access")
	@Override
	public double getBalance(OfflinePlayer playerOff) {
		if (playerOff.isOnline()) {
			Player playerOn = (Player)playerOff;
			return plugin.getTotalExp(playerOn.getLevel(), playerOn.getExp());
		} else {
			try {
				return new PlayerNBT(playerOff).getExp();
			} catch (PlayerNBTException e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	@Deprecated
	@Override
	public double getBalance(String playerName, String world) {
		return getBalance(playerName);
	}

	@Override
	public double getBalance(OfflinePlayer playerOff, String world) {
		return getBalance(playerOff);
	}

	public boolean setBalance(OfflinePlayer playerOff, double amountD) {
		int amount = (int) amountD;

		if (playerOff.isOnline()) {
			Player playerOn = (Player)playerOff;

			playerOn.setTotalExperience(0);
			playerOn.setLevel(0);
			playerOn.setExp(0);
			playerOn.giveExp(amount);

			return true;
		} else {
			try {
				PlayerNBT playerNBT = new PlayerNBT(playerOff);
				playerNBT.setExp(amount);
				playerNBT.save();
				return true;
			} catch (PlayerNBTException e) {
				e.printStackTrace();
				return false;
			}
		}
	}

	@Deprecated
	@Override
	public boolean has(String playerName, double amountD) {
		return has(plugin.getServer().getOfflinePlayer(playerName), amountD);
	}

	@Override
	public boolean has(OfflinePlayer playerOff, double amountD) {
		return (getBalance(playerOff) >= amountD);
	}

	@Deprecated
	@Override
	public boolean has(String playerName, String worldName, double amountD) {
		return has(playerName, amountD);
	}

	@Override
	public boolean has(OfflinePlayer playerOff, String worldName, double amountD) {
		return has(playerOff, amountD);
	}

	@Deprecated
	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amountD) {
		return withdrawPlayer(plugin.getServer().getOfflinePlayer(playerName), amountD);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer playerOff, double amountD) {
		if (hasAccount(playerOff)) {
			if (has(playerOff, amountD)) {
				double balanceNew = getBalance(playerOff) - amountD;
				if (balanceNew < 0) {
					balanceNew = 0;
				}
				if (setBalance(playerOff, balanceNew)) {
					return new EconomyResponse(amountD, balanceNew, ResponseType.SUCCESS, null);
				} else {
					return new EconomyResponse(0D, 0D, ResponseType.FAILURE, null);
				}
			} else {
				return new EconomyResponse(0D, getBalance(playerOff), ResponseType.FAILURE, "Insufficient funds");
			}
		} else {
			return new EconomyResponse(0D, 0D, ResponseType.FAILURE, "Player does not exist");
		}
	}

	@Deprecated
	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amountD) {
		return withdrawPlayer(playerName, amountD);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer playerOff, String worldName, double amountD) {
		return withdrawPlayer(playerOff, amountD);
	}

	@Deprecated
	@Override
	public EconomyResponse depositPlayer(String playerName, double amountD) {
		return depositPlayer(plugin.getServer().getOfflinePlayer(playerName), amountD);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer playerOff, double amountD) {
		if (hasAccount(playerOff)) {
			double balanceNew = getBalance(playerOff) + amountD;
			if (setBalance(playerOff, balanceNew)) {
				return new EconomyResponse(amountD, balanceNew, ResponseType.SUCCESS, null);
			} else {
				return new EconomyResponse(0D, 0D, ResponseType.FAILURE, null);
			}
		} else {
			return new EconomyResponse(0D, 0D, ResponseType.FAILURE, "Player does not exist");
		}
	}

	@Deprecated
	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
		return depositPlayer(playerName, amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer playerOff, String worldName, double amount) {
		return depositPlayer(playerOff, amount);
	}

	@Deprecated
	@Override
	public EconomyResponse createBank(String name, String playerName) {
		return createBank(name, plugin.getServer().getOfflinePlayer(playerName));
	}

	@Override
	public EconomyResponse createBank(String name, OfflinePlayer player) {
		// TODO Auto-generated method stub
		return notImpl;
	}

	@Override
	public EconomyResponse deleteBank(String name) {
		// TODO Auto-generated method stub
		return notImpl;
	}

	@Override
	public EconomyResponse bankBalance(String name) {
		// TODO Auto-generated method stub
		return notImpl;
	}

	@Override
	public EconomyResponse bankHas(String name, double amount) {
		// TODO Auto-generated method stub
		return notImpl;
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {
		// TODO Auto-generated method stub
		return notImpl;
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount) {
		// TODO Auto-generated method stub
		return notImpl;
	}

	@Deprecated
	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
		return isBankOwner(name, plugin.getServer().getOfflinePlayer(playerName));
	}

	@Override
	public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
		// TODO Auto-generated method stub
		return notImpl;
	}

	@Deprecated
	@Override
	public EconomyResponse isBankMember(String name, String playerName) {
		return isBankMember(name, plugin.getServer().getOfflinePlayer(playerName));
	}

	@Override
	public EconomyResponse isBankMember(String name, OfflinePlayer player) {
		// TODO Auto-generated method stub
		return notImpl;
	}

	@Override
	public List<String> getBanks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Deprecated
	@Override
	public boolean createPlayerAccount(String playerName) {
		return createPlayerAccount(plugin.getServer().getOfflinePlayer(playerName));
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player) {
		return plugin.isPlayerOnline(player);
	}

	@Deprecated
	@Override
	public boolean createPlayerAccount(String playerName, String worldName) {
		return createPlayerAccount(playerName);
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer playerOff, String worldName) {
		return createPlayerAccount(playerOff);
	}

}

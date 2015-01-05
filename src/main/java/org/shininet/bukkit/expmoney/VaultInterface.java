package org.shininet.bukkit.expmoney;

import java.text.DecimalFormat;
import java.util.List;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

import org.bukkit.OfflinePlayer;

public class VaultInterface implements Economy {
	private static DecimalFormat formatter = new DecimalFormat("#,###");
	private static EconomyResponse notImpl = new EconomyResponse((double)0, (double)0, ResponseType.NOT_IMPLEMENTED, (String)null);
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
		return "ExpMoney";
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

	@SuppressWarnings("deprecation")
	@Override
	public boolean hasAccount(String playerName) {
		return hasAccount(plugin.getServer().getOfflinePlayer(playerName));
	}

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		if (plugin.isPlayerOnline(player)) {
			return true;
		}
		return plugin.getPlayerFile(player).exists();
	}

	@Override
	public boolean hasAccount(String playerName, String worldName) {
		return hasAccount(playerName);
	}

	@Override
	public boolean hasAccount(OfflinePlayer player, String worldName) {
		return hasAccount(player);
	}

	@SuppressWarnings("deprecation")
	@Override
	public double getBalance(String playerName) {
		return getBalance(plugin.getServer().getOfflinePlayer(playerName));
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBalance(String playerName, String world) {
		return getBalance(playerName);
	}

	@Override
	public double getBalance(OfflinePlayer player, String world) {
		return getBalance(player);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean has(String playerName, double amount) {
		return has(plugin.getServer().getOfflinePlayer(playerName), amount);
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean has(String playerName, String worldName, double amount) {
		return has(playerName, amount);
	}

	@Override
	public boolean has(OfflinePlayer player, String worldName, double amount) {
		return has(player, amount);
	}

	@SuppressWarnings("deprecation")
	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		return withdrawPlayer(plugin.getServer().getOfflinePlayer(playerName), amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
		return withdrawPlayer(playerName, amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
		return withdrawPlayer(player, amount);
	}

	@SuppressWarnings("deprecation")
	@Override
	public EconomyResponse depositPlayer(String playerName, double amount) {
		return depositPlayer(plugin.getServer().getOfflinePlayer(playerName), amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
		return depositPlayer(playerName, amount);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
		return depositPlayer(player, amount);
	}

	@SuppressWarnings("deprecation")
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

	@SuppressWarnings("deprecation")
	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
		return isBankOwner(name, plugin.getServer().getOfflinePlayer(playerName));
	}

	@Override
	public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
		// TODO Auto-generated method stub
		return notImpl;
	}

	@SuppressWarnings("deprecation")
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

	@SuppressWarnings("deprecation")
	@Override
	public boolean createPlayerAccount(String playerName) {
		return createPlayerAccount(plugin.getServer().getOfflinePlayer(playerName));
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player) {
		return plugin.isPlayerOnline(player);
	}

	@Override
	public boolean createPlayerAccount(String playerName, String worldName) {
		return createPlayerAccount(playerName);
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
		return createPlayerAccount(player);
	}

}

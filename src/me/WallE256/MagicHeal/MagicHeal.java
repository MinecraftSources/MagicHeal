package me.WallE256.MagicHeal;

// import java.util.Random;
import java.util.logging.Logger;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MagicHeal extends JavaPlugin {
	public final Logger logger = Logger.getLogger("Minecraft");
	public static MagicHeal plugin;
	public int i;
	// public Random rand;

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName()
				+ " Has Been Disabled Made By WallE256");
	}

	@Override
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion()
				+ " Has Been Enabled");
		if (!setupEconomy()) {
			logger.severe(String.format(
					"[%s] - Disabled due to no Vault dependency found!",
					getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		setupEconomy();
		setupPermissions();
	}

	public static Permission permission = null;
	public static Economy economy = null;
	public static Chat chat = null;

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	private void heal(Player player) {
		player.setHealth(20);
		player.setFireTicks(0);
		player.setFoodLevel(20);
		for (PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String CommandLabel, String[] args) {
		Player player = (Player) sender;
		economy.createPlayerAccount(player.getName());
		if (CommandLabel.equalsIgnoreCase("heal")
				|| CommandLabel.equalsIgnoreCase("h")) {
			if (args.length == 0) {
				if (economy.getBalance(player.getName()) >= 100) {
					economy.bankWithdraw(player.getName(), 100);
					heal(player);
					player.sendMessage(ChatColor.GREEN + "Healed!");
				} else
					player.sendMessage(ChatColor.RED
							+ "You don't have enough money to heal you");
			} else if (args.length == 1) {
				if (player.getServer().getPlayer(args[0]) != null) {
					if (economy.getBalance(player.getName()) >= 100) {
						economy.bankWithdraw(player.getName(), 100);
						Player targetPlayer = player.getServer().getPlayer(
								args[0]);
						heal(targetPlayer);
						player.sendMessage(ChatColor.GREEN + "Healed!");
						targetPlayer.sendMessage(ChatColor.GOLD
								+ player.getName() + " healed you."
								+ ChatColor.GREEN + " Say thank you!");
					} else
						player.sendMessage(ChatColor.RED
								+ "You don't have enough money to heal someone");
				} else {
					player.sendMessage(ChatColor.DARK_RED
							+ "Player is not online!");
				}
			}
		} else if (CommandLabel.equalsIgnoreCase("magicheal")
				|| CommandLabel.equalsIgnoreCase("mh")) {
			if (args.length == 0) {
				if (economy.getBalance(player.getName()) >= 200) {
					economy.bankWithdraw(player.getName(), 200);
					// rand = new Random();
					heal(player);
					player.addPotionEffect(new PotionEffect(
							PotionEffectType.ABSORPTION, 600, 1));
					player.sendMessage(ChatColor.GREEN + "Healed!");
				} else
					player.sendMessage(ChatColor.RED
							+ "You don't have enough money to heal you");
			} else if (args.length == 1) {
				if (economy.getBalance(player.getName()) >= 200) {
					economy.bankWithdraw(player.getName(), 200);
					Player targetPlayer = player.getServer().getPlayer(args[0]);
					heal(targetPlayer);
					targetPlayer.addPotionEffect(new PotionEffect(
							PotionEffectType.ABSORPTION, 600, 1));
					player.sendMessage(ChatColor.GREEN + "Healed!");
					targetPlayer.sendMessage(ChatColor.GOLD + player.getName()
							+ " healed you." + ChatColor.GREEN
							+ " Say thank you!");
				} else
					player.sendMessage(ChatColor.RED
							+ "You don't have enough money to heal someone");
			} else {
				player.sendMessage(ChatColor.DARK_RED + "Player is not online!");
			}
		}
		return false;
	}

}

package me.WallE256.MagicHeal;

import java.util.Random;
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
	public Random rand;
	public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has Been Disabled");
	}
	@Override
	public void onEnable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has Been Enabled");
		if (!setupEconomy() ) {
            logger.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
	}
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
	}
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args){
		Player player= (Player) sender;
		if(CommandLabel.equalsIgnoreCase("heal")  || CommandLabel.equalsIgnoreCase("h")){
			if(args.length == 0){ 
				//rand = new Random();
				if(((Economy) player).bankHas(player.getName(),100) != null)
				{
					((Economy) player).bankWithdraw(player.getName(),100);
				player.setHealth(20);
				player.setFireTicks(0);
				player.setFoodLevel(20);
				player.getLocation();
				for (PotionEffect effect : player.getActivePotionEffects())
			        player.removePotionEffect(effect.getType());
				player.sendMessage(ChatColor.GREEN + "Healed!");
				}
				else
					player.sendMessage(ChatColor.RED + "You don't have enough money to heal you");
			}
			else if(args.length == 1){
				if(player.getServer().getPlayer(args[0]) != null)
					{
					if(((Economy) player).bankHas(player.getName(),100) != null)
					{
						((Economy) player).bankWithdraw(player.getName(),100);
					Player targetPlayer = player.getServer().getPlayer(args[0]);
						targetPlayer.setHealth(20);
						targetPlayer.setFireTicks(0);
						targetPlayer.setFoodLevel(20);
						for (PotionEffect effect : player.getActivePotionEffects())
					        targetPlayer.removePotionEffect(effect.getType());
						player.sendMessage(ChatColor.GREEN + "Healed!");
						targetPlayer.sendMessage(ChatColor.GOLD + player.getName() + " healed you." + ChatColor.GREEN + " Say thank you!");
					}
					else
						player.sendMessage(ChatColor.RED + "You don't have enough money to heal someone");
					}
				else
					{
						player.sendMessage(ChatColor.DARK_RED + "Player is not online!");
					}
				}
		}
		else if(CommandLabel.equalsIgnoreCase("magicheal") || CommandLabel.equalsIgnoreCase("mh"))
		{
			if(args.length == 0)
			{
				if(((Economy) player).bankHas(player.getName(),100) != null)
				{
					((Economy) player).bankWithdraw(player.getName(),100);
				//rand = new Random();
				player.setHealth(20);
				player.setFireTicks(0);
				player.setFoodLevel(20);
				for (PotionEffect effect : player.getActivePotionEffects())
			        player.removePotionEffect(effect.getType());
				player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 1));
				player.sendMessage(ChatColor.GREEN + "Healed!");
				}
				else
					player.sendMessage(ChatColor.RED + "You don't have enough money to heal you");
			}
			else
				if(args.length == 1)
				{
					if(((Economy) player).bankHas(player.getName(),100) != null)
					{
						((Economy) player).bankWithdraw(player.getName(),100);
					Player targetPlayer = player.getServer().getPlayer(args[0]);
					targetPlayer.setHealth(20);
					targetPlayer.setFireTicks(0);
					targetPlayer.setFoodLevel(20);
					for (PotionEffect effect : player.getActivePotionEffects())
				        targetPlayer.removePotionEffect(effect.getType());
					targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 1));
					player.sendMessage(ChatColor.GREEN + "Healed!");
					targetPlayer.sendMessage(ChatColor.GOLD + player.getName() + " healed you." + ChatColor.GREEN + " Say thank you!");
					}
					else
						player.sendMessage(ChatColor.RED + "You don't have enough money to heal someone");
				}
				else
				{
					player.sendMessage(ChatColor.DARK_RED + "Player is not online!");
				}
		}
	return false;
	}
	
}

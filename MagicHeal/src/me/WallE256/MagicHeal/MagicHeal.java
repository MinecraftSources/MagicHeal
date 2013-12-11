package me.WallE256.MagicHeal;

import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MagicHeal extends JavaPlugin {
    public final Logger logger = Logger.getLogger("Minecraft");
	public static MagicHeal plugin;
	public int i;
	public Random rand;
	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has Been Disabled");
	}
	@Override
	public void onEnable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has Been Enabled");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args){
		Player player= (Player) sender;
		if(CommandLabel.equalsIgnoreCase("heal")  || CommandLabel.equalsIgnoreCase("h")){
			if(args.length == 0){ 
				//rand = new Random();
				player.setHealth(20);
				player.setFireTicks(0);
				player.setFoodLevel(20);
				player.getLocation();
				for (PotionEffect effect : player.getActivePotionEffects())
			        player.removePotionEffect(effect.getType());
				player.sendMessage(ChatColor.GREEN + "Healed!");
			}
			else if(args.length == 1){
				if(player.getServer().getPlayer(args[0]) != null)
					{
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
					{
						player.sendMessage(ChatColor.DARK_RED + "Player is not online!");
					}
				}
		}
		else if(CommandLabel.equalsIgnoreCase("magicheal") || CommandLabel.equalsIgnoreCase("mh"))
		{
			if(args.length == 0)
			{
				rand = new Random();
				player.setHealth(20);
				player.setFireTicks(0);
				player.setFoodLevel(20);
				for (PotionEffect effect : player.getActivePotionEffects())
			        player.removePotionEffect(effect.getType());
				player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 1));
				player.sendMessage(ChatColor.GREEN + "Healed!");
			}
			else
				if(args.length == 1)
				{
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
				{
					player.sendMessage(ChatColor.DARK_RED + "Player is not online!");
				}
		}
	return false;
	}
	
}

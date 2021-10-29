package me.mexicanminion.bountyHunt.commands;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.managers.CurrencyManager;
import me.mexicanminion.bountyHunt.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BountyBoard implements CommandExecutor {

    public BountyHunt plugin;

    public BountyBoard(BountyHunt plugin){
        this.plugin = plugin;
        plugin.getCommand("bountyboard").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender.hasPermission("bountyboard.use")){
            if(args.length == 0){
                sender.sendMessage(Utils.chat("&ethis command is in testing"));
                return true;
            } else if(args.length == 1){
                sender.sendMessage(Utils.chat("&ethis command is in testing "));
            } else if(args.length == 2){
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                CurrencyManager manager = new CurrencyManager(plugin);
                if(offlinePlayer != null){
                    if(args[0].equalsIgnoreCase("get")){
                        sender.sendMessage(Utils.chat(offlinePlayer.getName() + " has " + manager.getPlayerCurrency(offlinePlayer)));
                        return true;
                    } else {
                        sender.sendMessage(Utils.chat("&ethis command is in testing"));
                    }
                }

            } else if(args.length == 3){
                //@SuppressWarnings("deprecation");
                OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(args[1]);
                int amount = Integer.parseInt(args[2]);
                CurrencyManager manager = new CurrencyManager(plugin);
                if(args[0].equalsIgnoreCase("add")){
                    if(offPlayer != null){
                        manager.addCurrencyToPlayer(offPlayer, amount);
                        sender.sendMessage("&aYou added " + args[2], " to player " + offPlayer.getName());
                    } else {
                        sender.sendMessage("&eplayer " + args[1], "&ecould not be found");
                    }
                } else if(args[0].equalsIgnoreCase("remove")){
                    if(offPlayer != null){
                        manager.removeCurrencyFromPlayer(offPlayer, amount);
                        sender.sendMessage("&aYou removed " + args[2], " to player " + offPlayer.getName());
                    } else {
                        sender.sendMessage("&eplayer " + args[1], "&ecould not be found");
                    }
                } else if(args[0].equalsIgnoreCase("set")){
                    if(offPlayer != null){
                        manager.setPlayerCurrency(offPlayer, amount);
                        sender.sendMessage("&aYou set " + offPlayer.getName(), " to " + args[1]);
                    } else {
                        sender.sendMessage("&eplayer " + args[1], "&ecould not be found");
                    }
                }
            }

        } else {
          sender.sendMessage(Utils.chat("&cYou do not have permission to use this command"));
        }
        return false;
    }
}

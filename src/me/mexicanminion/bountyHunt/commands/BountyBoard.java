package me.mexicanminion.bountyHunt.commands;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.utils.Utils;
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
                sender.sendMessage(Utils.chat("&e/Currency <add:remove:set> <player>"));
            }

        } else {
          sender.sendMessage(Utils.chat("&cYou do not have permission to use this command"));
        }
        return false;
    }
}

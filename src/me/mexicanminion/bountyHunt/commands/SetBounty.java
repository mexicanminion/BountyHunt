package me.mexicanminion.bountyHunt.commands;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.ui.BountyUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetBounty implements CommandExecutor {


    private BountyHunt plugin;

    public SetBounty(BountyHunt plugin){
        this.plugin = plugin;
        plugin.getCommand("setbounty").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("Only players can send this command!");
            return true;
        }

        Player p = (Player) sender;

        if(p.hasPermission("bounty.use")){
            p.openInventory(BountyUI.GUI(p));
            return true;
        }else{
            p.sendMessage("You can no longer set bounties");
        }

        return false;
    }
}

package me.mexicanminion.bountyHunt.commands;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.managers.CurrencyManager;
import me.mexicanminion.bountyHunt.ui.BountyUI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetBounty implements CommandExecutor {


    private BountyHunt plugin;
    private CurrencyManager manager = new CurrencyManager(plugin);

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
            if(args.length == 0) {
                sender.sendMessage("/setbounty <player>");
            }else if(args.length == 1){
                Player bountyPlayer = Bukkit.getPlayer(args[0]);
                if(bountyPlayer != null){ //if online
                    if(!bountyPlayer.getUniqueId().equals(p.getUniqueId())) {
                        if (manager.getPlayerCurrency(bountyPlayer) > 0) {
                            sender.sendMessage("Sorry, " + bountyPlayer.getDisplayName() + " already has a bounty on their head!");
                        } else {
                            p.openInventory(BountyUI.GUI(bountyPlayer));
                            return true;
                        }
                    }else{
                        sender.sendMessage("You cant sent a bounty on your own head silly");
                    }
                }else{
                    sender.sendMessage("Player must be online to set a bounty");
                }
                return true;
            }else{
                sender.sendMessage("/setbounty <player>");
            }
            return true;
        }else{
            p.sendMessage("You can no longer set bounties");
        }

        return false;
    }
}

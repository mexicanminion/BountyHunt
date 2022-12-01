package me.mexicanminion.bountyHunt.commands;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.managers.BountyManager;
import me.mexicanminion.bountyHunt.managers.CurrencyManager;
import me.mexicanminion.bountyHunt.managers.TimerManager;
import me.mexicanminion.bountyHunt.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BountyBoard implements CommandExecutor {

    BountyHunt plugin;
    private BountyManager bountyManager = new BountyManager(plugin);
    private CurrencyManager currencyManager = new CurrencyManager(plugin);
    private TimerManager timerManager = new TimerManager(plugin);

    public BountyBoard(BountyHunt plugin){
        this.plugin = plugin;
        plugin.getCommand("bountyboard").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player p = (Player) sender;
        boolean noBounty = true;

        if(p.hasPermission("bountyboard.use")){
            p.sendMessage(Utils.chat("&2Players with bounties online: " + Bukkit.getOnlinePlayers().size()));
            for(Player player : Bukkit.getOnlinePlayers()){
                if((bountyManager.seeBounty(player.getUniqueId()) != null) && currencyManager.getPlayerCurrency(player) > 0) {
                    noBounty = false;
                    p.sendMessage(Utils.chat("&c" + player.getDisplayName() + " for " +
                            currencyManager.getPlayerCurrency(player) +
                            " diamonds with (time) " + timerManager.getTimer(player.getUniqueId()) + " left"));
                }
            }
            if(noBounty){
                p.sendMessage(Utils.chat("&cThere are no players online with a bounty"));
            }

            return true;
        }else{
            p.sendMessage("You can no longer see the list");
        }

        return false;

    }
}

package me.mexicanminion.bountyHunt.commands;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.managers.BountyManager;
import me.mexicanminion.bountyHunt.managers.CurrencyManager;
import me.mexicanminion.bountyHunt.ui.BountyUI;
import me.mexicanminion.bountyHunt.ui.ClaimBountyUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClaimBounty implements CommandExecutor {
    private BountyHunt plugin;
    private CurrencyManager currencyManager = new CurrencyManager(plugin);
    private BountyManager bountyManager = new BountyManager(plugin);

    private static Player bountyPlayer;


    public ClaimBounty(BountyHunt plugin){
        this.plugin = plugin;
        plugin.getCommand("claimbounty").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("Only players can send this command!");
            return true;
        }

        Player p = (Player) sender;

        if(p.hasPermission("bounty.use")){

            if(Bukkit.getPlayer(args[0]) != null) {
                bountyPlayer = Bukkit.getPlayer(args[0]);
                assert bountyPlayer != null;
                if (bountyManager.getBounty(bountyPlayer.getUniqueId()) == null && currencyManager.getPlayerCurrency(bountyPlayer) > 0) {
                    sender.sendMessage("This players bounty is still active");
                }else if (bountyManager.getBounty(bountyPlayer.getUniqueId()) == null) {
                    sender.sendMessage("This player does not have a bounty, Set one using /setbounty (playername)!");
                }else if(bountyManager.getBounty(bountyPlayer.getUniqueId()).equals(p.getUniqueId())){
                    p.openInventory(ClaimBountyUI.GUI(bountyPlayer));
                }else{
                    sender.sendMessage("You can not claim this bounty");
                }
            }else{
                sender.sendMessage("/claimbounty <player>");
            }

            return true;
        }else{
            p.sendMessage("You can no longer set bounties");
        }

        return false;
    }
}

package me.mexicanminion.bountyHunt.tasks;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.managers.BountyManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BountyTimer extends BukkitRunnable {

    private static BountyHunt plugin;
    private static Player player;
    private static BountyManager bountyManager= new BountyManager(plugin);


    public BountyTimer(BountyHunt plugin, Player player){
        this.player = player;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        bountyManager.setPlayerBounty(player,player);
        Bukkit.broadcastMessage("Bounty has passed for player " + player.getDisplayName());
        player.sendMessage("Claim your bouinty");
    }

}

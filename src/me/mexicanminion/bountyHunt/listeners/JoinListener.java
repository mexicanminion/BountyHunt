package me.mexicanminion.bountyHunt.listeners;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.managers.TimerManager;
import me.mexicanminion.bountyHunt.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private BountyHunt plugin;
    private TimerManager timerManager;

    public JoinListener(BountyHunt plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent join){

        Player p = join.getPlayer();

        /*if(timerManager.getTimer(p) != null) {
            timerManager.updateTimer(p);
            Bukkit.broadcastMessage(Utils.chat(p.getDisplayName() + " has joined and has" + timerManager.secondsLeft(p)));

        }

         */
    }


}

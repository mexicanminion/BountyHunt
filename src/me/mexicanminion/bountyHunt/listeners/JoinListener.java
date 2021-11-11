package me.mexicanminion.bountyHunt.listeners;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.managers.OnlineManager;
import me.mexicanminion.bountyHunt.managers.TimerManager;
import me.mexicanminion.bountyHunt.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    private BountyHunt plugin;
    private OnlineManager onlineManager;

    public JoinListener(BountyHunt plugin){
        this.plugin = plugin;

        onlineManager = new OnlineManager(plugin);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent join){

        UUID p = join.getPlayer().getUniqueId();
        //Bukkit.broadcastMessage("player joined");

        if(onlineManager.getOnline(p)){
            onlineManager.addOnline(p);
        }else{
            onlineManager.setOnline(p);
        }


    }

    @EventHandler
    public void onLeave(PlayerQuitEvent quit){

        UUID p = quit.getPlayer().getUniqueId();
        //Bukkit.broadcastMessage("player left");

        onlineManager.removeOnline(p);

        /*if(timerManager.getTimer(p) != null) {
            timerManager.updateTimer(p);
            Bukkit.broadcastMessage(Utils.chat(p.getDisplayName() + " has joined and has" + timerManager.secondsLeft(p)));

        }

         */
    }


}

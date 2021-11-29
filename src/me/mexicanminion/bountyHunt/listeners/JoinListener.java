package me.mexicanminion.bountyHunt.listeners;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.managers.BountyManager;
import me.mexicanminion.bountyHunt.managers.CurrencyManager;
import me.mexicanminion.bountyHunt.managers.OnlineManager;
import me.mexicanminion.bountyHunt.managers.TimerManager;
import me.mexicanminion.bountyHunt.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

public class JoinListener implements Listener {

    private BountyHunt plugin;
    private OnlineManager onlineManager;
    private static BountyManager bountyManager;
    private static CurrencyManager currencyManager;

    //private TimerManager timerManager;

    public JoinListener(BountyHunt plugin){
        this.plugin = plugin;

        onlineManager = new OnlineManager(plugin);
        bountyManager= new BountyManager(plugin);
        currencyManager = new CurrencyManager(plugin);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent join){

        UUID p = join.getPlayer().getUniqueId();
        Player player = join.getPlayer();

        //timerManager = new TimerManager(plugin,player,p);
        //Bukkit.broadcastMessage("player joined");

        if(onlineManager.getOnline(p)){
            onlineManager.addOnline(p);
        }else{
            onlineManager.setOnline(p);
        }

        if(!bountyManager.bountyDead(p) && currencyManager.getPlayerCurrency(player) > 0){
            Bukkit.broadcastMessage("This player has joined and has a bounty, timer should resume here");
            BukkitTask task = new TimerManager(plugin,player, p).runTaskTimer(plugin,10L,20L);
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

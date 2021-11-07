package me.mexicanminion.bountyHunt.listeners;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.managers.BountyManager;
import me.mexicanminion.bountyHunt.managers.CurrencyManager;
import me.mexicanminion.bountyHunt.ui.BountyUI;
import me.mexicanminion.bountyHunt.ui.ClaimBountyUI;
import me.mexicanminion.bountyHunt.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BountyDeath implements Listener {

    private static BountyHunt plugin;
    private static BountyManager bountyManager= new BountyManager(plugin);
    private static CurrencyManager currencyManager = new CurrencyManager(plugin);

    public BountyDeath(BountyHunt plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        Bukkit.broadcastMessage("event Handler for player death");
        BountyUI.timer.cancel();
        if (p.getUniqueId() == bountyManager.getBounty(p)) {
            if(p.isDead()){
                bountyManager.setPlayerBounty(p,p.getKiller());
                Bukkit.broadcastMessage(p.getKiller().getDisplayName() + " killed " + p.getDisplayName() + " and claimed their bounty!!");
                p.getKiller().sendMessage(Utils.chat("To Claim, type /claimbounty"));
            }

        }
    }

}

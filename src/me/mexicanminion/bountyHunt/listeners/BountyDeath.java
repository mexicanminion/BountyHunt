package me.mexicanminion.bountyHunt.listeners;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.managers.BountyManager;
import me.mexicanminion.bountyHunt.managers.CurrencyManager;
import me.mexicanminion.bountyHunt.ui.BountyUI;
import me.mexicanminion.bountyHunt.ui.ClaimBountyUI;
import me.mexicanminion.bountyHunt.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
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
        //Bukkit.broadcastMessage("event Handler for player death");
        if(event.getEntity().getKiller().getType() == null){
            return;
        }
        if(event.getEntity().getKiller().getType() == EntityType.PLAYER){
            Player p = event.getEntity();
            //Bukkit.broadcastMessage("player was killed by player");
            //Bukkit.broadcastMessage("event Handler for player death");
            if (p.getUniqueId() == bountyManager.seeBounty(p.getUniqueId())) {
                if(p.isDead()){
                    bountyManager.setPlayerBounty(p.getUniqueId(),p.getKiller().getUniqueId());
                    Bukkit.broadcastMessage(p.getKiller().getDisplayName() + " killed " + p.getDisplayName() + " and claimed their bounty!!");
                    for(Player player : Bukkit.getOnlinePlayers()){
                        player.resetTitle();
                        player.sendTitle(Utils.chat("&2" + p.getKiller().getDisplayName() + " killed " + p.getDisplayName()), Utils.chat("&cand claimed their bounty!!"), 10, 40, 10);
                    }
                    p.getKiller().sendMessage(Utils.chat("To Claim, type /claimbounty"));
                }

            }
        }

    }

}
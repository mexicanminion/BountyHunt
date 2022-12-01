package me.mexicanminion.bountyHunt.listeners;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.ui.BountyUI;
import me.mexicanminion.bountyHunt.ui.ClaimBountyUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private BountyHunt plugin;

    public InventoryClickListener(BountyHunt plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        String title = e.getView().getTitle();
        if(title.equals(BountyUI.inventory_name)){
            e.setCancelled(false);
            if(e.getCurrentItem() != null){
                e.setCancelled(BountyUI.clicked((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory()));
            }
        }else if(title.equals(ClaimBountyUI.inventory_name)){
            e.setCancelled(false);
            if(e.getCurrentItem() != null){
                e.setCancelled(ClaimBountyUI.clicked((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory()));
            }
        }
    }

}

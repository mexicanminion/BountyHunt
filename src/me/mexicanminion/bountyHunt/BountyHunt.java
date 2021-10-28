package me.mexicanminion.bountyHunt;

import me.mexicanminion.bountyHunt.commands.SetBounty;
import me.mexicanminion.bountyHunt.listeners.InventoryClickListener;
import me.mexicanminion.bountyHunt.ui.BountyUI;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class BountyHunt extends JavaPlugin {

    @Override
    public void onEnable(){
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin is on, Enjoy hunting!");
        new SetBounty(this);
        new InventoryClickListener(this);
        BountyUI.initialize();
    }

    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin is off, see you next time!");
    }

}

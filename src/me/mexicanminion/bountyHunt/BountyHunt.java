package me.mexicanminion.bountyHunt;

import me.mexicanminion.bountyHunt.commands.BountyBoard;
import me.mexicanminion.bountyHunt.commands.SetBounty;
import me.mexicanminion.bountyHunt.listeners.InventoryClickListener;
import me.mexicanminion.bountyHunt.managers.CurrencyManager;
import me.mexicanminion.bountyHunt.ui.BountyUI;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class BountyHunt extends JavaPlugin {

    @Override
    public void onEnable(){

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin init; currency manager files");
        CurrencyManager currencyManager = new CurrencyManager(this);
        try {
            currencyManager.loadCurrencyFile();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin init; Commands init");
        BountyUI.initialize();
        new CurrencyManager(this);
        new SetBounty(this);
        new BountyBoard(this);
        new InventoryClickListener(this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin is on, Enjoy hunting!");
    }

    @Override
    public void onDisable(){

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin is saving");
        CurrencyManager currencyManager = new CurrencyManager(this);
        try {
            currencyManager.saveCurrencyFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin is off, see you next time!");
    }

}

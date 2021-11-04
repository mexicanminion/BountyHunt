package me.mexicanminion.bountyHunt;

import me.mexicanminion.bountyHunt.commands.BountyBoard;
import me.mexicanminion.bountyHunt.commands.ClaimBounty;
import me.mexicanminion.bountyHunt.commands.SetBounty;
import me.mexicanminion.bountyHunt.listeners.BountyDeath;
import me.mexicanminion.bountyHunt.listeners.InventoryClickListener;
import me.mexicanminion.bountyHunt.managers.BountyManager;
import me.mexicanminion.bountyHunt.managers.CurrencyManager;
import me.mexicanminion.bountyHunt.ui.BountyUI;
import me.mexicanminion.bountyHunt.ui.ClaimBountyUI;
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

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin init; Bounty manager files");
        BountyManager bountyManager = new BountyManager(this);
        try {
            bountyManager.loadBountyFile();
        } catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin init; Commands init");
        new CurrencyManager(this);
        new BountyManager(this);
        new InventoryClickListener(this);
        new BountyDeath(this);
        BountyUI.initialize();
        ClaimBountyUI.initialize();
        new SetBounty(this);
        new ClaimBounty(this);
        new BountyBoard(this);
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

        BountyManager bountyManager = new BountyManager(this);
        try {
            bountyManager.saveBountyFile();
        } catch (IOException e){
            e.printStackTrace();
        }

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin is off, see you next time!");
    }

}

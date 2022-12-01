package me.mexicanminion.bountyHunt;

import java.io.File;

import me.mexicanminion.bountyHunt.commands.BountyBoard;
import me.mexicanminion.bountyHunt.commands.FixBounty;
import me.mexicanminion.bountyHunt.commands.ClaimBounty;
import me.mexicanminion.bountyHunt.commands.SetBounty;
import me.mexicanminion.bountyHunt.listeners.BountyDeath;
import me.mexicanminion.bountyHunt.listeners.InventoryClickListener;
import me.mexicanminion.bountyHunt.listeners.JoinListener;
import me.mexicanminion.bountyHunt.managers.BountyManager;
import me.mexicanminion.bountyHunt.managers.CurrencyManager;
import me.mexicanminion.bountyHunt.managers.OnlineManager;
import me.mexicanminion.bountyHunt.managers.TimerManager;
import me.mexicanminion.bountyHunt.ui.BountyUI;
import me.mexicanminion.bountyHunt.ui.ClaimBountyUI;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class BountyHunt extends JavaPlugin {

    @Override
    public void onEnable(){

        int pluginId = 16986; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
        // Optional: Add custom charts
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));

        String path = this.getDataFolder().getPath() + File.separator;

        try {
            boolean exists = (new File(path)).exists();
            if (!exists) {
                new File(path).mkdir();
            }
            CurrencyManager currencyManager = new CurrencyManager(this);
            BountyManager bountyManager = new BountyManager(this);
            TimerManager timerManager = new TimerManager(this,null,null);
            OnlineManager onlineManager = new OnlineManager(this);
            currencyManager.saveCurrencyFile();
            bountyManager.saveBountyFile();
            timerManager.saveTimerFile();
            onlineManager.saveOnlineFile();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

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

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin init; Timer manager files");
        TimerManager timerManager = new TimerManager(this,null,null);
        try {
            timerManager.loadTimerFile();
        } catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin init; Online manager files");
        OnlineManager onlineManager = new OnlineManager(this);
        try {
            onlineManager.loadOnlineFile();
        } catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin init; Managers init");
        new CurrencyManager(this);
        new BountyManager(this);
        new TimerManager(this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin init; Listeners init");
        new InventoryClickListener(this);
        new JoinListener(this);
        new BountyDeath(this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin init; GUI init");
        BountyUI.initialize(this);
        ClaimBountyUI.initialize();
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin init; Commands init");
        new SetBounty(this);
        new ClaimBounty(this);
        new FixBounty(this);
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

        TimerManager timerManager = new TimerManager(this, null, null);
        try {
            timerManager.saveTimerFile();
        } catch (IOException e){
            e.printStackTrace();
        }

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Bounty Hunt]: Plugin is off, see you next time!");
    }

}

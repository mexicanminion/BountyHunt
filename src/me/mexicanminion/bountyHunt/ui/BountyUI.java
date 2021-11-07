package me.mexicanminion.bountyHunt.ui;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.managers.BountyManager;
import me.mexicanminion.bountyHunt.managers.CurrencyManager;
import me.mexicanminion.bountyHunt.tasks.BountyTimer;
import me.mexicanminion.bountyHunt.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class BountyUI {

    public static Inventory inv;
    public static String inventory_name;
    public static int inv_rows = 4 * 9;
    public static BukkitTask timer;

    private static BountyHunt plugin;
    private static CurrencyManager currencyManager;// = new CurrencyManager(plugin);
    private static BountyManager bountyManager; //= new BountyManager(plugin);

    private static Player bountyPlayer;

    public static void initialize(BountyHunt pluginInit){

        plugin = pluginInit;
        inventory_name = Utils.chat("Place diamond bounty here");

        currencyManager = new CurrencyManager(plugin);
        bountyManager= new BountyManager(plugin);

        inv = Bukkit.createInventory(null, inv_rows);
    }

    public static Inventory GUI(Player player){

        bountyPlayer = player;

        Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);

        Utils.createItem(inv,"dirt",1,22,"Click here to confirm","This click is final");
        Utils.createItem(inv,"dirt",1,24,"Click here to cancel","This click is final");

        toReturn.setContents(inv.getContents());

        return toReturn;
    }

    public static void clicked(Player p, int slot, ItemStack clicked, Inventory inv){

        ItemStack[] stack  = inv.getContents();

        if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("Click here to confirm"))){
            boolean diamondTrue = false;
            for(ItemStack item : stack){
                //p.sendMessage(item.getType().toString());
                if(item != null) {
                    if (item.getType() == Material.matchMaterial("diamond")) {
                        currencyManager.addCurrencyToPlayer(bountyPlayer, item.getAmount());
                        bountyManager.removeBounty(bountyPlayer);
                        diamondTrue = true;
                    }
                }
            }
            if(diamondTrue == true){
                p.sendMessage(Utils.chat("Bounty set on " + bountyPlayer.getDisplayName() + " for a reward of " + currencyManager.getPlayerCurrency(bountyPlayer)));
                Bukkit.broadcastMessage(p.getDisplayName() + " set a bounty on " + bountyPlayer.getDisplayName() + " for " + currencyManager.getPlayerCurrency(bountyPlayer));
                timer = new BountyTimer(plugin,bountyPlayer).runTaskLater(plugin,60*20L);// 60 sec * 20 ticks (20 ticks in 1 sec)
                p.closeInventory();
            }else{
                p.sendMessage(Utils.chat("Please Use Diamonds!!"));
            }
        }else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("Click here to cancel"))){
            boolean itemInInv = false;
            for(ItemStack item : stack) {
                if (item != null) {
                    itemInInv = true;
                }
            }
            if(itemInInv == true){
                p.sendMessage(Utils.chat("Please take out all items from inventory!!"));
            }else{
                p.sendMessage(Utils.chat("GUI Closed"));
                p.closeInventory();
            }

        }
    }

}

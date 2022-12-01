package me.mexicanminion.bountyHunt.ui;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.managers.BountyManager;
import me.mexicanminion.bountyHunt.managers.CurrencyManager;
import me.mexicanminion.bountyHunt.managers.TimerManager;
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

    private static BountyHunt plugin;
    private static CurrencyManager currencyManager;// = new CurrencyManager(plugin);
    private static BountyManager bountyManager; //= new BountyManager(plugin);
    private static TimerManager timerManager;

    private static Player bountyPlayer;

    public static void initialize(BountyHunt pluginInit){

        plugin = pluginInit;
        inventory_name = Utils.chat("Place diamond bounty here");

        currencyManager = new CurrencyManager(plugin);
        bountyManager= new BountyManager(plugin);
        timerManager = new TimerManager(plugin);//,bountyPlayer, bountyPlayer.getUniqueId());

        inv = Bukkit.createInventory(null, inv_rows);
    }

    public static Inventory GUI(Player player){

        bountyPlayer = player;

        Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);

        Utils.createItem(inv,Material.LIME_CONCRETE,1,31,"Click here to confirm","This click is final");
        Utils.createItem(inv,Material.RED_CONCRETE,1,33,"Click here to cancel","This click is final");

        toReturn.setContents(inv.getContents());

        return toReturn;
    }

    /**
     *
     * @param p
     * @param slot
     * @param clicked
     * @param inv
     * @return true when incorrect click, false when correct click
     */
    public static boolean clicked(Player p, int slot, ItemStack clicked, Inventory inv){

        ItemStack[] stack  = inv.getContents();

        if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("Click here to confirm"))){
            boolean diamondTrue = false;
            for(ItemStack item : stack){
                //p.sendMessage(item.getType().toString());
                if(item != null) {
                    if (item.getType() == Material.matchMaterial("diamond")) {
                        currencyManager.addCurrencyToPlayer(bountyPlayer, item.getAmount());
                        bountyManager.removeBounty(bountyPlayer.getUniqueId());
                        diamondTrue = true;
                    }
                }
            }
            if(diamondTrue == true){
                p.sendMessage(Utils.chat("Bounty set on " + bountyPlayer.getDisplayName() + " for a reward of " + currencyManager.getPlayerCurrency(bountyPlayer)));
                String bountyStringOne = "Bounty set on " + bountyPlayer.getDisplayName();
                String bountyStringTwo = "For a reward of " + currencyManager.getPlayerCurrency(bountyPlayer);
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.resetTitle();
                    player.sendTitle(Utils.chat("&4" + bountyStringOne), Utils.chat("&c" + bountyStringTwo), 10, 40, 10);
                }
                //Bukkit.broadcastMessage(p.getDisplayName() + " set a bounty on " + bountyPlayer.getDisplayName() + " for " + currencyManager.getPlayerCurrency(bountyPlayer));
                BukkitTask task = new TimerManager(plugin,bountyPlayer, bountyPlayer.getUniqueId()).runTaskTimer(plugin,10L,20L);
                p.closeInventory();
            }else{
                p.sendMessage(Utils.chat("Please Use Diamonds!!"));
                return true;
            }
        }else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("Click here to cancel"))){
            boolean itemInInv = false;
            for(ItemStack item : stack) {
                if (item != null && (!item.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("Click here to confirm")) &&
                                     !item.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("Click here to cancel")))) {
                    itemInInv = true;
                }
            }
            if(itemInInv == true){
                p.sendMessage(Utils.chat("Please take out all items from inventory!!"));
                return true;
            }else{
                //p.sendMessage(Utils.chat("GUI Closed"));
                p.closeInventory();
            }

        }
        return false;
    }

}

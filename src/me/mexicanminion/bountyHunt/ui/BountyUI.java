package me.mexicanminion.bountyHunt.ui;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.managers.CurrencyManager;
import me.mexicanminion.bountyHunt.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BountyUI {

    public static Inventory inv;
    public static String inventory_name;
    public static int inv_rows = 3 * 9;

    private static BountyHunt plugin;
    private static CurrencyManager manager = new CurrencyManager(plugin);

    public static void initialize(){
        inventory_name = Utils.chat("Place diamond bounty here");

        inv = Bukkit.createInventory(null, inv_rows);
    }

    public static Inventory GUI(Player player){

        Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);

        Utils.createItem(inv,"dirt",1,22,"Click here to confirm","This click is final");
        Utils.createItem(inv,"dirt",1,24,"Click here to cancel","This click is final");

        toReturn.setContents(inv.getContents());

        return toReturn;
    }

    public static void clicked(Player p, int slot, ItemStack clicked, Inventory inv){
        if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("Click here to confirm"))){

            ItemStack[] stack  = inv.getContents();

            for(ItemStack item : stack){
                Utils.chat(item.getType().toString());
            }
            //p.sendMessage(Utils.chat("I made a GUI"));
            //manager.addCurrencyToPlayer(p,20);

            p.closeInventory();

        }else if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("Click here to cancel"))){
            p.sendMessage(Utils.chat("GUI CLosed"));
            manager.setPlayerCurrency(p,0);
            //inv.getContents();
            p.closeInventory();

        }
    }

}

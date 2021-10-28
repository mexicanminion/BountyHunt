package me.mexicanminion.bountyHunt.ui;

import me.mexicanminion.bountyHunt.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BountyUI {

    public static Inventory inv;
    public static String inventory_name;
    public static int inv_rows = 4 * 9;

    public static void initialize(){
        inventory_name = Utils.chat("Place diamond bounty here");

        inv = Bukkit.createInventory(null, inv_rows);
    }

    public static Inventory GUI(Player player){

        Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);

        Utils.createItem(inv,"dirt",1,0,"Place diamond bounty here","this is line 1");

        toReturn.setContents(inv.getContents());

        return toReturn;
    }

    public static void clicked(Player p, int slot, ItemStack clicked, Inventory inv){
        if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("&test item"))){
            p.setDisplayName(Utils.chat("I made a GUI"));
        }
    }

}

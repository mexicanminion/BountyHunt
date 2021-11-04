package me.mexicanminion.bountyHunt.ui;

import me.mexicanminion.bountyHunt.BountyHunt;
import me.mexicanminion.bountyHunt.managers.BountyManager;
import me.mexicanminion.bountyHunt.managers.CurrencyManager;
import me.mexicanminion.bountyHunt.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClaimBountyUI {

    public static Inventory inv;
    public static String inventory_name;
    public static int inv_rows = 4 * 9;

    private static BountyHunt plugin;
    private static CurrencyManager currencyManager = new CurrencyManager(plugin);
    private static BountyManager bountyManager= new BountyManager(plugin);


    private static Player bountyPlayer;

    public static void initialize(){
        inventory_name = Utils.chat("Claim your bounty reward!!");

        inv = Bukkit.createInventory(null, inv_rows);
    }

    public static Inventory GUI(Player player){

        bountyPlayer = player;

        Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);

        Utils.createItem(inv,"dirt",1,23,"Click here to exit","This click is final");

        int diamomnds = currencyManager.getPlayerCurrency(bountyPlayer);

        Utils.createItem(inv,"diamond", diamomnds, 5, "Your prize!", "Drag to your inventory!");

        bountyManager.removeBounty(bountyPlayer);
        currencyManager.removeCurrencyFromPlayer(bountyPlayer,diamomnds);

        toReturn.setContents(inv.getContents());

        return toReturn;
    }

    public static void clicked(Player p, int slot, ItemStack clicked, Inventory inv){

        ItemStack[] stack  = inv.getContents();

        if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("Click here to exit"))){

            int itemInInv = 0;
            for(ItemStack item : stack) {
                if (item != null) {
                    if(item.getType() == Material.matchMaterial("dirt")){
                        itemInInv++;
                    }
                }
            }
            if(itemInInv > 2){
                p.sendMessage(Utils.chat("Please take out all items from inventory!!"));
            }else{
                p.sendMessage(Utils.chat("GUI Closed"));
                p.closeInventory();
            }


        }
    }

}

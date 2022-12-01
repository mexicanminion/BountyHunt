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

import java.util.ArrayList;
import java.util.List;

public class ClaimBountyUI {

    public static Inventory inv;
    public static String inventory_name;
    public static int inv_rows = 4 * 9;

    private static BountyHunt plugin;
    private static CurrencyManager currencyManager = new CurrencyManager(plugin);
    private static BountyManager bountyManager= new BountyManager(plugin);

    static int diamomnds;


    private static Player bountyPlayer;

    public static void initialize(){
        inventory_name = Utils.chat("Claim your bounty reward!!");

        inv = Bukkit.createInventory(null, inv_rows);
    }

    public static Inventory GUI(Player player){

        bountyPlayer = player;
        diamomnds = currencyManager.getPlayerCurrency(bountyPlayer);
        //List<Integer> diamondArray = new ArrayList<Integer>();

        int diamondStacks = diamomnds/64;
        int diamondRemander = diamomnds % 64;

        //Bukkit.broadcastMessage("diamond stacks" + diamondStacks);

        int space = 1;

        //fix how to spawn diamonds, this wont work. will give out wrong amounts(UPDATE, idk how old this is, it seems to work as of 11/21/22, keeping this comment for future refrence)
        if(diamondStacks > 0){
            for(int i = diamondStacks; i >= 1; i--){
                Utils.createItemNoString(inv,"diamond", 64, space);
                space++;
            }
        }
        Utils.createItemNoString(inv,"diamond", diamondRemander, space);

        Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);

        Utils.createItem(inv,Material.RED_CONCRETE,1,32,"Click here to exit","This click is final");

        toReturn.setContents(inv.getContents());

        return toReturn;
    }

    public static boolean clicked(Player p, int slot, ItemStack clicked, Inventory inv){

        ItemStack[] stack  = inv.getContents();

        if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("Click here to exit"))){

            int itemInInv = 0;
            for(ItemStack item : stack) {
                if (item != null) {
                    itemInInv++;
                }
            }
            if(itemInInv > 1){
                p.sendMessage(Utils.chat("Please take out all items from inventory!!"));
                return true;
            }else{
                bountyManager.removeBounty(bountyPlayer.getUniqueId());
                currencyManager.removeCurrencyFromPlayer(bountyPlayer,diamomnds);
                p.closeInventory();
            }

        }else if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.chat("Diamond"))){

        }
        return false;

    }

}

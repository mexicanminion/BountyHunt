package me.mexicanminion.bountyHunt.managers;

import me.mexicanminion.bountyHunt.BountyHunt;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CurrencyManager {

    public BountyHunt plugin;
    private HashMap<UUID, Integer> currency = new HashMap<UUID, Integer>();

    public CurrencyManager(BountyHunt plugin){
        this.plugin = plugin;
    }

    public void saveCurrencyFile() throws FileNotFoundException, IOException {

        for(OfflinePlayer offPlayer : Bukkit.getOfflinePlayers()) {
            File file = new File("Currency/curency.dat");

            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            UUID uuid = offPlayer.getUniqueId();

            if (currency.get(uuid) != null) {
                currency.put(uuid, currency.get(uuid));
            }

            try{
                output.writeObject(currency);
                output.flush();
                output.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

    }

    public void loadCurrencyFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File("Currency/curency.dat");
        if(file != null){
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObject = input.readObject();
            input.close();

            if(!(readObject instanceof HashMap)){
                throw new IOException("Data is not a HashMap");
            }

            currency = (HashMap<UUID, Integer>) readObject;
            for(UUID key : currency.keySet()){
                currency.put(key, currency.get(key));
            }

        }

    }

    public void addCurrencyToPlayer(OfflinePlayer offPlayer, int amount){
        if(currency.get(offPlayer.getUniqueId()) != null){
            currency.put(offPlayer.getUniqueId(), currency.get(offPlayer.getUniqueId()) + amount);
        } else {
            currency.put(offPlayer.getUniqueId(), amount);
        }
    }

    public void removeCurrencyFromPlayer(OfflinePlayer offPlayer, int amount){
        if(currency.get(offPlayer.getUniqueId()) != null){
            currency.put(offPlayer.getUniqueId(), currency.get(offPlayer.getUniqueId()) - amount);
        }
    }

    public void setPlayerCurrency(OfflinePlayer offPlayer, int amount){
        currency.put(offPlayer.getUniqueId(), amount);
    }

    public int getPlayerCurrency(OfflinePlayer offPlayer){
        if(currency.get(offPlayer.getUniqueId()) != null){
            return currency.get(offPlayer.getUniqueId());
        }else {
            return 0;
        }
    }
}

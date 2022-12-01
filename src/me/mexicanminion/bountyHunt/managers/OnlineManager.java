package me.mexicanminion.bountyHunt.managers;

import me.mexicanminion.bountyHunt.BountyHunt;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class OnlineManager {

    public BountyHunt plugin;
    public static HashMap<UUID, UUID> online = new HashMap<UUID, UUID>();
    //private boolean isDead;

    public OnlineManager(BountyHunt plugin){
        this.plugin = plugin;
    }

    public void saveOnlineFile() throws FileNotFoundException, IOException {

        for(OfflinePlayer offPlayer : Bukkit.getOfflinePlayers()) {
            //File file = new File("BountyCurrency/bounty.dat");
            File file = new File(plugin.getDataFolder() + "/bounty.dat");

            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            UUID uuid = offPlayer.getUniqueId();

            try{
                output.writeObject(online);
                output.flush();
                output.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

    }

    public void loadOnlineFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        //File file = new File("BountyCurrency/bounty.dat");
        File file = new File(plugin.getDataFolder() + "/curency.dat");

        if(file != null){
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObject = input.readObject();
            input.close();

            if(!(readObject instanceof HashMap)){
                throw new IOException("Data is not a HashMap");
            }

            online = (HashMap<UUID, UUID>) readObject;
            for(UUID key : online.keySet()){
                online.put(key, online.get(key));
            }

        }

    }

    public void addOnline(UUID player){
        if(online.get(player) == null){
            online.put(player, player);
        }
    }

    public void removeOnline(UUID player){
        if(online.get(player) != null){
            online.put(player, null);
        }
    }

    public void setOnline(UUID player){
        online.put(player, player);
    }


    public boolean getOnline(UUID offPlayer){
        if(online.get(offPlayer) != null){
            return true;
        }else{
            return false;
        }
    }


}

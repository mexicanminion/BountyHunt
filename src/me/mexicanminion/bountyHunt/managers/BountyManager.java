package me.mexicanminion.bountyHunt.managers;

import me.mexicanminion.bountyHunt.BountyHunt;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class BountyManager {

    public BountyHunt plugin;
    public static HashMap<UUID, UUID> bounty = new HashMap<UUID, UUID>();
    //private boolean isDead;

    public BountyManager(BountyHunt plugin){
        this.plugin = plugin;
    }

    public void saveBountyFile() throws FileNotFoundException, IOException {

        for(OfflinePlayer offPlayer : Bukkit.getOfflinePlayers()) {
            File file = new File("BountyCurrency/bounty.dat");

            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            UUID uuid = offPlayer.getUniqueId();

            try{
                output.writeObject(bounty);
                output.flush();
                output.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

    }

    public void loadBountyFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File("BountyCurrency/bounty.dat");
        if(file != null){
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObject = input.readObject();
            input.close();

            if(!(readObject instanceof HashMap)){
                throw new IOException("Data is not a HashMap");
            }

            bounty = (HashMap<UUID, UUID>) readObject;
            for(UUID key : bounty.keySet()){
                bounty.put(key, bounty.get(key));
            }

        }

    }

    public void removeBounty(OfflinePlayer bountyPlayer){
        if(bounty.get(bountyPlayer.getUniqueId()) != null){
            bounty.put(bountyPlayer.getUniqueId(), null);
        }
    }

    public void setPlayerBounty(OfflinePlayer bounterPlayer, OfflinePlayer bountier){
        bounty.put(bounterPlayer.getUniqueId(), bountier.getUniqueId());
    }

    public UUID seeBounty(OfflinePlayer offPlayer){
        if(bounty.get(offPlayer.getUniqueId()) == null){
            return offPlayer.getUniqueId();
        }else {
            return null;
        }
    }

    public UUID getBounty(OfflinePlayer offPlayer){
        if(bounty.get(offPlayer.getUniqueId()) != null){
            return bounty.get(offPlayer.getUniqueId());
        }else {
            return null;
        }
    }


    public boolean bountyDead(OfflinePlayer player){
        if(getBounty(player) == null){
            return false;
        }else{
            return true;
        }
    }
}

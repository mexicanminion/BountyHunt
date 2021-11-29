package me.mexicanminion.bountyHunt.managers;

import me.mexicanminion.bountyHunt.BountyHunt;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TimerManager extends BukkitRunnable {

    private BountyManager bountyManager;
    private HashMap<UUID, Integer> cooldown = new HashMap<UUID, Integer>();
    private BountyHunt plugin;
    private OnlineManager onlineManager;
    public Player player;
    private UUID playerUUID;
    private int coolDownTime = 2*60*60;//hours * mins * seconds
    private int secondsLeft = 0;

    public TimerManager(BountyHunt plugin, Player p, UUID playerUUID){
        this.plugin = plugin;
        onlineManager = new OnlineManager(plugin);
        player = p;
        this.playerUUID = playerUUID;
        bountyManager = new BountyManager(plugin);
        if(player != null) {
            cooldown.put(player.getUniqueId(),coolDownTime);
            secondsLeft = coolDownTime;
        }
    }

    public TimerManager(BountyHunt plugin){
        this.plugin = plugin;
        bountyManager = new BountyManager(plugin);

    }

    public void saveTimerFile() throws FileNotFoundException, IOException {

        for(OfflinePlayer offPlayer : Bukkit.getOfflinePlayers()) {
            File file = new File("BountyCurrency/timer.dat");

            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

            UUID uuid = offPlayer.getUniqueId();

            try{
                output.writeObject(cooldown);
                output.flush();
                output.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

    }

    public void loadTimerFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File("BountyCurrency/timer.dat");
        if(file != null){
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObject = input.readObject();
            input.close();

            if(!(readObject instanceof HashMap)){
                throw new IOException("Data is not a HashMap");
            }

            cooldown = (HashMap<UUID, Integer>) readObject;
            for(UUID key : cooldown.keySet()){
                cooldown.put(key, cooldown.get(key));
            }

        }

    }

    @Override
    public void run() {

        //this is untested
        //for (Player pl : Bukkit.getOnlinePlayers()) {
            //Bukkit.broadcastMessage(Bukkit.getOnlinePlayers() + " test");
            //this works
            if(onlineManager.getOnline(playerUUID)){
                //Bukkit.broadcastMessage(player + " is online");
                if(secondsLeft > 0 && !bountyManager.bountyDead(playerUUID)){
                    cooldown.put(playerUUID, coolDownTime);
                    secondsLeft--;
                    coolDownTime = secondsLeft;
                }else{
                    if(secondsLeft <= 0){
                        bountyManager.setPlayerBounty(playerUUID,playerUUID);
                        Bukkit.broadcastMessage("Bounty has passed for player " + player.getDisplayName());
                        player.sendMessage("Claim your bouinty");
                        this.cancel();
                    }else if(bountyManager.bountyDead(playerUUID)){
                        //Bukkit.broadcastMessage("timer stop/ player died");
                        this.cancel();
                    }

                }
            }else{
                this.cancel();
            }

        //}


        //bountyManager.setPlayerBounty(player,player);
        //Bukkit.broadcastMessage("Bounty has passed for player " + player.getDisplayName());
        //player.sendMessage("Claim your bouinty");
    }

    public Integer getTimer(Player player){

        /*if(cooldown.get(player.getUniqueId()) != null){
            return cooldown.get(player.getUniqueId());
        } else {
            return null;
        }
         */

        return cooldown.get(player.getUniqueId());
    }

}

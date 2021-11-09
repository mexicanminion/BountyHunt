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
    private Player player;
    private int coolDownTime = 20;//seconds
    private int secondsLeft = 0;

    public TimerManager(BountyHunt plugin, Player p){
        this.plugin = plugin;
        player = p;
        bountyManager = new BountyManager(plugin);
        if(player != null) {
            cooldown.put(player.getUniqueId(),coolDownTime);
            secondsLeft = coolDownTime;
        }
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
        if(Bukkit.getOnlinePlayers().contains(player)) {

            //this works
            //if(pl == player){
                Bukkit.broadcastMessage(player.getDisplayName() + " is online");
            Bukkit.broadcastMessage(Bukkit.getOnlinePlayers() + " test");
                if(secondsLeft > 0 && !bountyManager.bountyDead(player)){
                    cooldown.put(player.getUniqueId(), coolDownTime);
                    secondsLeft--;
                    coolDownTime = secondsLeft;
                }else{
                    if(secondsLeft <= 0){
                        bountyManager.setPlayerBounty(player,player);
                        Bukkit.broadcastMessage("Bounty has passed for player " + player.getDisplayName());
                        player.sendMessage("Claim your bouinty");
                        this.cancel();
                    }else if(bountyManager.bountyDead(player)){
                        Bukkit.broadcastMessage("timer stop/ player died");
                        this.cancel();
                    }

                }
            //}
        }else{
            Bukkit.broadcastMessage(player.getDisplayName() + " is not online");
            Bukkit.broadcastMessage(Bukkit.getOnlinePlayers() + " test");
            //Bukkit.getOnlinePlayers().
        }


        //bountyManager.setPlayerBounty(player,player);
        //Bukkit.broadcastMessage("Bounty has passed for player " + player.getDisplayName());
        //player.sendMessage("Claim your bouinty");
    }

    /*public void startTimer(Player player){
        cooldown.put(player.getUniqueId(),System.currentTimeMillis());
        secondsLeft = ((cooldown.get(player.getUniqueId()) / 1000) + coolDownTime) - (System.currentTimeMillis() / 1000);
    }

    public Long getTimer(Player player){

        if(cooldown.get(player.getUniqueId()) != null){
            return cooldown.get(player.getUniqueId());
        } else {
            return null;
        }

    }

    public void updateTimer(Player player){
        if(cooldown.containsKey(player.getUniqueId())){
            secondsLeft = ((cooldown.get(player.getUniqueId()) / 1000) + coolDownTime) - (System.currentTimeMillis() / 1000);
            // do time stuff
        } else {
            cooldown.put(player.getUniqueId(),System.currentTimeMillis());
        }
    }

    public Long secondsLeft(Player player){
        if(cooldown.containsKey(player.getUniqueId())){
            secondsLeft = ((cooldown.get(player.getUniqueId()) / 1000) + coolDownTime) - (System.currentTimeMillis() / 1000);
            return secondsLeft;
        }else {
            return 0L;
        }
    }
    */


}

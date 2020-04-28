package com.serverworld.phoenix.paper.Listeners;

import com.serverworld.phoenix.paper.worldMISFpaperspigot;
import com.serverworld.worldSocket.paperspigot.events.MessagecomingEvent;
import com.serverworld.worldSocket.paperspigot.util.messagecoder;
import com.serverworld.worldSocket.paperspigot.util.messager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Messagecoming implements Listener {

    worldMISFpaperspigot worldmisfpaperspigot;

    public Messagecoming(worldMISFpaperspigot worldmisfpaperspigot){
        this.worldmisfpaperspigot = worldmisfpaperspigot;
    }
    @EventHandler
    public void onMessagecomingEvent(MessagecomingEvent event){
        if(!event.getChannel().toUpperCase().equals("MISF")) return;
        //worldmisfpaperspigot.getLogger().info(event.getMessage());
        World world = worldmisfpaperspigot.getServer().getWorld("world");
        try{
            switch (event.getType().toUpperCase()){
                case "COMMAND": worldmisfpaperspigot.getServer().dispatchCommand(worldmisfpaperspigot.getServer().getConsoleSender(),event.getMessage().toString());

                case "REQUEST":;

                case "SYNC":{
                    String[] msg = event.getMessage().toUpperCase().split(",");
                    if(event.getSender().equals(worldmisfpaperspigot.config.servername())) return;
                    switch (msg[0].toUpperCase()){
                        case "TIME": {
                            long time =Long.valueOf(msg[1]) - world.getTime();
                            if(time<200&&time>-200) {
                                worldmisfpaperspigot.getLogger().info("time checked, no need to set time.");
                            }else{
                                worldmisfpaperspigot.getLogger().info("time checked, setting time to " + msg[1]);
                                world.setTime(Long.valueOf(msg[1]));
                            }
                            return;
                        }
                        case "WEATHER":{
                            if(msg[1].toUpperCase().equals("CLEAR")){
                                world.setStorm(false);
                                world.setThundering(false);
                                world.setWeatherDuration(Integer.valueOf(msg[2]));
                                world.setThunderDuration(Integer.valueOf(msg[2]));
                            }else if (msg[1].toUpperCase().equals("RAIN")){
                                world.setStorm(true);
                                world.setThundering(false);
                                world.setWeatherDuration(Integer.valueOf(msg[2]));
                                world.setThunderDuration(Integer.valueOf(msg[2]));
                            }else if(msg[1].toUpperCase().equals("THUNDER")){
                                world.setStorm(true);
                                world.setThundering(true);
                                world.setWeatherDuration(Integer.valueOf(msg[2]));
                                world.setThunderDuration(Integer.valueOf(msg[2]));
                            }else {
                                String errormsg = "";
                                for (String stuff : msg) {
                                    errormsg += stuff;
                                    worldmisfpaperspigot.getLogger().warning(ChatColor.RED + "weather check failed! " + errormsg);
                                }
                            }
                            return;
                        }
                    }
                }

                case  "SET": {
                    String[] msg = event.getMessage().toUpperCase().split(",");
                    switch (msg[0].toUpperCase()){
                        case "TIME": {
                            worldmisfpaperspigot.getLogger().info("Setting time to " + Long.valueOf(msg[1]));
                            world.setTime(Long.valueOf(msg[1]));
                            return;
                        }

                        case "WEATHER":{
                            if(msg[1].toUpperCase().equals("CLEAR")){
                                world.setStorm(false);
                                world.setThundering(false);
                                world.setWeatherDuration(Integer.valueOf(msg[2]));
                                world.setThunderDuration(Integer.valueOf(msg[2]));
                                worldmisfpaperspigot.getLogger().info(ChatColor.GREEN + "Setting weather to clear");
                            }else if (msg[1].toUpperCase().equals("RAIN")){
                                world.setStorm(true);
                                world.setThundering(false);
                                world.setWeatherDuration(Integer.valueOf(msg[2]));
                                world.setThunderDuration(Integer.valueOf(msg[2]));
                                worldmisfpaperspigot.getLogger().info(ChatColor.GREEN + "Setting weather to storm");
                            }else {
                                world.setStorm(true);
                                world.setThundering(true);
                                world.setWeatherDuration(Integer.valueOf(msg[2]));
                                world.setThunderDuration(Integer.valueOf(msg[2]));
                                worldmisfpaperspigot.getLogger().info(ChatColor.GREEN + "Setting weather to thunderstorms");
                            }
                            return;
                        }


                    }
                }
            }
        }catch (Exception e){
            worldmisfpaperspigot.getLogger().warning("Error on socket msg "+e.getMessage());
        }
    }
}

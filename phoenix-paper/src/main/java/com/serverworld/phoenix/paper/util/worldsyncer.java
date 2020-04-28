package com.serverworld.phoenix.paper.util;

import com.serverworld.phoenix.paper.worldMISFpaperspigot;
import com.serverworld.phoenix.paper.worldMISFpaperspigotconfig;
import com.serverworld.worldSocket.paperspigot.util.messagecoder;
import com.serverworld.worldSocket.paperspigot.util.messager;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.World;

public class worldsyncer {
    public worldMISFpaperspigot worldmisfpaperspigot;
    public worldMISFpaperspigotconfig config;

    public worldsyncer(worldMISFpaperspigot worldMISFpaperspigot){
        this.worldmisfpaperspigot = worldMISFpaperspigot;
        config=worldMISFpaperspigot.config;
        weathersyncer();
        timesyncer();
    }

    public void timesyncer() {
        worldmisfpaperspigot.getServer().getScheduler().scheduleSyncRepeatingTask(worldmisfpaperspigot, new Runnable() {
            @Override
            public void run() {
                World world = worldmisfpaperspigot.getServer().getWorld("world");
                messagecoder messagecode = new messagecoder();
                messagecode.setSender(worldmisfpaperspigot.config.servername());
                messagecode.setReceiver("ALL");
                messagecode.setChannel("MISF");
                messagecode.setType("SYNC");
                if(!NumberUtils.isNumber(String.valueOf(world.getTime()))){
                    worldmisfpaperspigot.getLogger().warning(ChatColor.RED + "Cant get world time!");
                    return;
                }
                messagecode.setMessage("TIME," + world.getTime());
                messager.sendmessage(messagecode.createmessage());
            }
        }, 0L, config.sync_time()*20);
    }

    public void weathersyncer() {
        worldmisfpaperspigot.getServer().getScheduler().scheduleSyncRepeatingTask(worldmisfpaperspigot, new Runnable() {
            @Override
            public void run() {
                World world = worldmisfpaperspigot.getServer().getWorld("world");
                messagecoder messagecode = new messagecoder();
                messagecode.setSender(worldmisfpaperspigot.config.servername());
                messagecode.setReceiver("ALL");
                messagecode.setChannel("MISF");
                messagecode.setType("SYNC");
                if(world.hasStorm()&&world.isThundering()){
                    messagecode.setMessage("WEATHER,THUNDER," + world.getWeatherDuration());
                }else if(world.hasStorm()&&!world.isThundering()) {
                    messagecode.setMessage("WEATHER,RAIN," + world.getWeatherDuration());
                }else if(!world.hasStorm()&&!world.isThundering()){
                    messagecode.setMessage("WEATHER,CLEAR," + world.getWeatherDuration());
                }else {
                    messagecode.setMessage("WEATHER,CLEAR," + world.getWeatherDuration());
                }
                messager.sendmessage(messagecode.createmessage());
            }
        }, 0L, config.sync_weather_time()*20);
    }

}

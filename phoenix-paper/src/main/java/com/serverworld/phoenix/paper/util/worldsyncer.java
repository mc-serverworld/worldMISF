package com.serverworld.phoenix.paper.util;

import com.serverworld.phoenix.paper.BukkitPhoenix;
import com.serverworld.phoenix.paper.BukkitPhoenixConfig;
import com.serverworld.worldSocket.paperspigot.util.messagecoder;
import com.serverworld.worldSocket.paperspigot.util.messager;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.World;

public class worldsyncer {
    public BukkitPhoenix bukkitphoenix;
    public BukkitPhoenixConfig config;

    public worldsyncer(BukkitPhoenix BukkitPhoenix){
        this.bukkitphoenix = BukkitPhoenix;
        config= BukkitPhoenix.config;
        weathersyncer();
        timesyncer();
    }

    public void timesyncer() {
        bukkitphoenix.getServer().getScheduler().scheduleSyncRepeatingTask(bukkitphoenix, new Runnable() {
            @Override
            public void run() {
                World world = bukkitphoenix.getServer().getWorld("world");
                messagecoder messagecode = new messagecoder();
                messagecode.setSender(bukkitphoenix.config.servername());
                messagecode.setReceiver("ALL");
                messagecode.setChannel("MISF");
                messagecode.setType("SYNC");
                if(!NumberUtils.isNumber(String.valueOf(world.getTime()))){
                    bukkitphoenix.getLogger().warning(ChatColor.RED + "Cant get world time!");
                    return;
                }
                messagecode.setMessage("TIME," + world.getTime());
                messager.sendmessage(messagecode.createmessage());
            }
        }, 0L, config.sync_time()*20);
    }

    public void weathersyncer() {
        bukkitphoenix.getServer().getScheduler().scheduleSyncRepeatingTask(bukkitphoenix, new Runnable() {
            @Override
            public void run() {
                World world = bukkitphoenix.getServer().getWorld("world");
                messagecoder messagecode = new messagecoder();
                messagecode.setSender(bukkitphoenix.config.servername());
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

/*
 * WorldMISF - cms of mc-serverworld
 * Copyright (C) 2019-2020 mc-serverworld
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.serverworld.phoenix.paper.Listeners;

import com.serverworld.phoenix.paper.BukkitPhoenix;
import com.serverworld.worldSocket.paperspigot.events.MessagecomingEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Messagecoming implements Listener {

    BukkitPhoenix bukkitphoenix;

    public Messagecoming(BukkitPhoenix bukkitphoenix){
        this.bukkitphoenix = bukkitphoenix;
    }
    @EventHandler
    public void onMessagecomingEvent(MessagecomingEvent event){
        if(!event.getChannel().toUpperCase().equals("MISF")) return;
        //worldmisfpaperspigot.getLogger().info(event.getMessage());
        World world = bukkitphoenix.getServer().getWorld("world");
        try{
            switch (event.getType().toUpperCase()){
                case "COMMAND": bukkitphoenix.getServer().dispatchCommand(bukkitphoenix.getServer().getConsoleSender(),event.getMessage().toString());

                case "REQUEST":;

                case "SYNC":{
                    String[] msg = event.getMessage().toUpperCase().split(",");
                    if(event.getSender().equals(bukkitphoenix.config.servername())) return;
                    switch (msg[0].toUpperCase()){
                        case "TIME": {
                            long time =Long.valueOf(msg[1]) - world.getTime();
                            if(time<200&&time>-200) {
                                bukkitphoenix.getLogger().info("time checked, no need to set time.");
                            }else{
                                bukkitphoenix.getLogger().info("time checked, setting time to " + msg[1]);
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
                                    bukkitphoenix.getLogger().warning(ChatColor.RED + "weather check failed! " + errormsg);
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
                            bukkitphoenix.getLogger().info("Setting time to " + Long.valueOf(msg[1]));
                            world.setTime(Long.valueOf(msg[1]));
                            return;
                        }

                        case "WEATHER":{
                            if(msg[1].toUpperCase().equals("CLEAR")){
                                world.setStorm(false);
                                world.setThundering(false);
                                world.setWeatherDuration(Integer.valueOf(msg[2]));
                                world.setThunderDuration(Integer.valueOf(msg[2]));
                                bukkitphoenix.getLogger().info(ChatColor.GREEN + "Setting weather to clear");
                            }else if (msg[1].toUpperCase().equals("RAIN")){
                                world.setStorm(true);
                                world.setThundering(false);
                                world.setWeatherDuration(Integer.valueOf(msg[2]));
                                world.setThunderDuration(Integer.valueOf(msg[2]));
                                bukkitphoenix.getLogger().info(ChatColor.GREEN + "Setting weather to storm");
                            }else {
                                world.setStorm(true);
                                world.setThundering(true);
                                world.setWeatherDuration(Integer.valueOf(msg[2]));
                                world.setThunderDuration(Integer.valueOf(msg[2]));
                                bukkitphoenix.getLogger().info(ChatColor.GREEN + "Setting weather to thunderstorms");
                            }
                            return;
                        }


                    }
                }
            }
        }catch (Exception e){
            bukkitphoenix.getLogger().warning("Error on socket msg "+e.getMessage());
        }
    }
}

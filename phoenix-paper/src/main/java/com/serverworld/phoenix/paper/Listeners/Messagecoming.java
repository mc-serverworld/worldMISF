/*
 *
 *  * WorldMISF - cms of mc-serverworld
 *  * Copyright (C) 2019-2020 mc-serverworld
 *  *
 *  * This program is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as published by
 *  * the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.serverworld.phoenix.paper.Listeners;

import com.serverworld.phoenix.paper.PaperPhoenix;
import com.serverworld.phoenix.paper.util.DebugMessage;
import com.serverworld.worldSocket.paperspigot.events.MessagecomingEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.json.JSONObject;

public class Messagecoming implements Listener {

    private PaperPhoenix paperPhoenix;

    public Messagecoming(PaperPhoenix paperPhoenix){
        this.paperPhoenix = paperPhoenix;
    }
    @EventHandler
    public void onMessagecomingEvent(MessagecomingEvent event){
        if(!event.getChannel().toUpperCase().equals("MISF_PHOENIX")) return;
        //DebugMessage.sendInfoIfDebug("Incoming message: " + "sender: " + event.getSender() + " receiver: " + event.getReceiver() + " channel: " + event.getChannel() + " type: " + event.getType());
        //worldmisfpaperspigot.getLogger().info(event.getMessage());
        World world = paperPhoenix.getServer().getWorld("world");
        try{
            switch (event.getType().toUpperCase()){
                case "COMMAND": paperPhoenix.getServer().dispatchCommand(paperPhoenix.getServer().getConsoleSender(),event.getMessage().toString());

                case "ACTION":Actions(event);

                case "SYNC":{
                    String[] msg = event.getMessage().toUpperCase().split(",");
                    if(event.getSender().equals(PaperPhoenix.config.servername())) return;
                    switch (msg[0].toUpperCase()){
                        case "TIME": {
                            long time =Long.valueOf(msg[1]) - world.getTime();
                            if(time<200&&time>-200) {
                                DebugMessage.sendInfoIfDebug("time checked, no need to set time.");
                            }else{
                                DebugMessage.sendInfoIfDebug("time checked, setting time to " + msg[1]);
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
                                    DebugMessage.sendWarring(ChatColor.RED + "weather check failed! " + errormsg);
                                }
                            }
                            return;
                        }
                        default: return;
                    }
                }

                case  "SET": {
                    String[] msg = event.getMessage().toUpperCase().split(",");
                    switch (msg[0].toUpperCase()){
                        case "TIME": {
                            DebugMessage.sendInfo("Setting time to " + Long.valueOf(msg[1]));
                            world.setTime(Long.valueOf(msg[1]));
                            return;
                        }

                        case "WEATHER":{
                            if(msg[1].toUpperCase().equals("CLEAR")){
                                world.setStorm(false);
                                world.setThundering(false);
                                world.setWeatherDuration(Integer.valueOf(msg[2]));
                                world.setThunderDuration(Integer.valueOf(msg[2]));
                                DebugMessage.sendInfo(ChatColor.GREEN + "Setting weather to clear");
                            }else if (msg[1].toUpperCase().equals("RAIN")){
                                world.setStorm(true);
                                world.setThundering(false);
                                world.setWeatherDuration(Integer.valueOf(msg[2]));
                                world.setThunderDuration(Integer.valueOf(msg[2]));
                                DebugMessage.sendInfo(ChatColor.GREEN + "Setting weather to storm");
                            }else {
                                world.setStorm(true);
                                world.setThundering(true);
                                world.setWeatherDuration(Integer.valueOf(msg[2]));
                                world.setThunderDuration(Integer.valueOf(msg[2]));
                                DebugMessage.sendInfo(ChatColor.GREEN + "Setting weather to thunderstorms");
                            }
                            return;
                        }
                        default: return;
                    }
                }
                default: return;
            }
        }catch (Exception e){
            paperPhoenix.getLogger().warning("Error on socket msg "+e.getMessage());
        }
    }

    private void  Actions(MessagecomingEvent event){
        JSONObject message = new JSONObject(event.getMessage());
        DebugMessage.sendInfoIfDebug("Action triggered: " + event.getMessage());
        switch (message.getString("TYPE").toUpperCase()){
            case "RESPAWNPLAYER": {
                try{
                    World world = PaperPhoenix.getInstance().getServer().getWorld("world");
                    Location spawn = new Location(world,PaperPhoenix.config.spawnx(), PaperPhoenix.config.spawny(),PaperPhoenix.config.spawnz());
                    Player player = PaperPhoenix.getInstance().getServer().getPlayer(message.getString("PLAYER"));
                    DebugMessage.sendInfoIfDebug("Send Player " + player.getName() + " to spawn");

                    player.teleport(spawn);
                }catch (Exception e){
                    e.printStackTrace();
                    DebugMessage.sendWarring("The player is gone!");
                }
            }

            case "TELEPORTPLAYER": {
                World world = PaperPhoenix.getInstance().getServer().getWorld(message.getString("WORLD"));
                Location spawn = new Location(world,message.getDouble("LOCATION_X"), message.getDouble("LOCATION_Y"),message.getDouble("LOCATION_Z"));
                Player player = PaperPhoenix.getInstance().getServer().getPlayer(message.getString("PLAYER"));
                DebugMessage.sendInfoIfDebug("Teleport Player " + player.getName() + "to " + message.get("LOCATION_X") + " " + message.get("LOCATION_Y") + " " + message.get("LOCATION_Z"));

                player.teleport(spawn);
            }

            default: return;

        }
    }

}

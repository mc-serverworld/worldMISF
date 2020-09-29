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

import com.serverworld.phoenix.paper.Listeners.subListeners.Sync_v2;
import com.serverworld.phoenix.paper.PaperPhoenix;
import com.serverworld.phoenix.paper.util.DebugMessage;
import com.serverworld.phoenix.paper.util.Formats;
import com.serverworld.worldSocket.paperspigot.events.MessagecomingEvent;
import com.serverworld.worlduserdata.jsondata.UserPhoenixPlayerData;
import com.serverworld.worlduserdata.paper.utils.UserPhoenixPlayerDataMySQL;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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

                case "SYNC_V2": new Sync_v2(event);

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

    //private static Set<Player> players = new HashSet<>();

    private void  Actions(MessagecomingEvent event){
        JSONObject message = new JSONObject(event.getMessage());
        DebugMessage.sendInfoIfDebug("Action triggered: " + event.getMessage());
        switch (message.getString("TYPE").toUpperCase()){
            case "SAVE_PLAYER_LOCATION_AS_LAST": {
                try{
                    Player player = PaperPhoenix.getInstance().getServer().getPlayer(message.getString("PLAYER"));

                    UserPhoenixPlayerData playerData = UserPhoenixPlayerDataMySQL.getDataClass(player.getUniqueId().toString());
                    playerData.setLastlocation_server(PaperPhoenix.config.servername());
                    playerData.setLastlocation_world(player.getWorld().getName());
                    playerData.setLastlocation_x(player.getLocation().getX());
                    playerData.setLastlocation_y(player.getLocation().getY());
                    playerData.setLastlocation_z(player.getLocation().getZ());
                    UserPhoenixPlayerDataMySQL.setDataClass(player.getUniqueId().toString() , playerData);//save dead pos to database

                    DebugMessage.sendInfoIfDebug("Save Player: " + player.getName() + " location");
                    return;
                }catch (Exception e){
                    e.printStackTrace();
                    DebugMessage.sendWarring("Some thing go wrong!");
                }
            }

            case "RESPAWN_PLAYER": {
                try{
                    World world = PaperPhoenix.getInstance().getServer().getWorld("world");
                    Location spawn = new Location(world,PaperPhoenix.config.spawnx(), PaperPhoenix.config.spawny(),PaperPhoenix.config.spawnz());
                    Player player = PaperPhoenix.getInstance().getServer().getPlayer(message.getString("PLAYER"));
                    DebugMessage.sendInfoIfDebug("Send Player " + player.getName() + " to spawn");

                    player.teleport(spawn);
                    return;
                }catch (Exception e){
                    e.printStackTrace();
                    DebugMessage.sendWarring("The player is gone!");
                }
            }

            case "TELEPORT_PLAYER": {
                try {
                    World world = PaperPhoenix.getInstance().getServer().getWorld(message.getString("WORLD"));
                    Location spawn = new Location(world,message.getDouble("LOCATION_X"), message.getDouble("LOCATION_Y"),message.getDouble("LOCATION_Z"));
                    Player player = PaperPhoenix.getInstance().getServer().getPlayer(message.getString("PLAYER"));
                    DebugMessage.sendInfoIfDebug("Teleport Player " + player.getName() + " to " + message.get("LOCATION_X") + " " + message.get("LOCATION_Y") + " " + message.get("LOCATION_Z"));

                    player.teleport(spawn);
                    return;
                }catch (Exception e){
                    e.printStackTrace();
                    DebugMessage.sendWarring("The Player is gone!");
                }
            }

            case "TELEPORT_PLAYER_TO_PLAYER": {
                try {
                    Player player = PaperPhoenix.getInstance().getServer().getPlayer(message.getString("PLAYER"));
                    Player target_player = PaperPhoenix.getInstance().getServer().getPlayer(message.getString("TARGET_PLAYER"));
                    DebugMessage.sendInfoIfDebug("Teleport Player " + player.getName() + " to " + target_player);

                    player.teleport(target_player);
                    return;
                }catch (Exception e){
                    e.printStackTrace();
                    DebugMessage.sendWarring("The Player is gone!");
                }
            }

            case "TELEPORT_REQUEST_TPA": {
                try {
                    //Player player = PaperPhoenix.getInstance().getServer().getPlayer(message.getString("PLAYER"));
                    Player target_player = PaperPhoenix.getInstance().getServer().getPlayer(message.getString("TARGET_PLAYER"));
                    if(!target_player.isOnline()){
                        //TODO return
                        return;
                    }

                    TextComponent ButtonYESComponent = new TextComponent( "/tpaccept" );
                    ButtonYESComponent.setColor( ChatColor.GREEN );
                    ButtonYESComponent.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "接受傳送請求" ).create() ) );
                    ButtonYESComponent.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tpaccept" ) );

                    TextComponent ButtonNOComponent = new TextComponent( "/tpdeny" );
                    ButtonNOComponent.setColor( ChatColor.RED );
                    ButtonNOComponent.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "拒絕傳送請求" ).create() ) );
                    ButtonNOComponent.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tpdeny" ) );

                    TextComponent Line1 = new TextComponent(Formats.perfix());
                    TextComponent Line2 = new TextComponent(Formats.perfix());
                    TextComponent Line3 = new TextComponent(Formats.perfix());
                    TextComponent Line4 = new TextComponent(Formats.perfix());
                    Line1.addExtra(ChatColor.GOLD + "玩家 " + ChatColor.YELLOW + message.getString("PLAYER") + ChatColor.GOLD + " 想要" + ChatColor.GREEN + "傳送到你的位置");

                    Line2.addExtra(ChatColor.GOLD + "點選或輸入");
                    Line2.addExtra(ButtonYESComponent);
                    Line2.addExtra(ChatColor.GOLD + " 接受傳送請求");

                    Line3.addExtra(ChatColor.GOLD + "點選或輸入");
                    Line3.addExtra(ButtonNOComponent);
                    Line3.addExtra(ChatColor.GOLD + " 拒絕傳送請求");

                    Line4.addExtra(ChatColor.GOLD + "此傳送請求將在" + ChatColor.RED + "30秒" + ChatColor.GOLD + "後過期");

                    target_player.sendMessage(Line1);
                    target_player.sendMessage(Line2);
                    target_player.sendMessage(Line3);
                    target_player.sendMessage(Line4);


                    /*target_player.sendMessage(new TextComponent(Formats.perfix() + ChatColor.GOLD + "玩家 " + ChatColor.YELLOW + message.getString("PLAYER") + ChatColor.GOLD + " 想要" + ChatColor.GREEN + "傳送到你的位置");
                    target_player.sendMessage(new TextComponent(Formats.perfix() + ChatColor.GOLD + "點選或輸入" + ButtonYESComponent.toString() + ChatColor.GOLD + " 接受傳送請求");
                    target_player.sendMessage(new TextComponent(Formats.perfix() + ChatColor.GOLD + "點選或輸入" + ButtonNOComponent.toString() + ChatColor.GOLD + " 拒絕傳送請求");
                    target_player.sendMessage(new TextComponent(Formats.perfix() + ChatColor.GOLD + "此傳送請求將在" + ChatColor.RED + "30秒" + ChatColor.GOLD + "後過期");
                    target_player.sendMessage(new TextComponent(Formats.perfix() + ChatColor.GOLD + "輸入" + ChatColor.GREEN + "/tpaccept" + ChatColor.GOLD + " 接受傳送請求");
                    */

                    //DebugMessage.sendInfoIfDebug("Teleport Player " + player.getName() + " to " + target_player);

                    return;
                }catch (Exception e){
                    e.printStackTrace();
                    DebugMessage.sendWarring("The Player is gone!");
                }
            }

            default: return;

        }
    }

}

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

package com.serverworld.phoenix.bungee.Listeners;

import com.serverworld.phoenix.bungee.Listeners.subListeners.Sync_v2;
import com.serverworld.phoenix.bungee.util.DebugMessage;
import com.serverworld.worldSocket.bungeecord.events.MessagecomingEvent;
import com.serverworld.worldSocket.bungeecord.util.messagecoder;
import com.serverworld.worldSocket.bungeecord.worldSocket;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import org.json.JSONObject;

public class Messagecoming implements Listener {

    public Messagecoming(Plugin plugin){
        ProxyServer.getInstance().getPluginManager().registerListener(plugin,this);
    }

    @EventHandler
    public void onMessagecoming(MessagecomingEvent event) {
        if(!event.getChannel().equals("MISF_PHOENIX"))
            return;
        if(!event.getReceiver().toLowerCase().equals("proxy"))
            return;
        try{
            switch (event.getType().toUpperCase()){
                case "COMMAND": ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(),event.getMessage());

                case "ACTION": Actions(event);

                case "SYNC_V2": new Sync_v2(event);

                default:return;
            }
        }catch (Exception e){
            DebugMessage.sendWarring("Error on socket msg "+e.getMessage());
        }
    }

    private void  Actions(MessagecomingEvent event){
        //JsonObject message = JsonParser.parseString(event.getMessage()).getAsJsonObject();
        JSONObject message = new JSONObject(event.getMessage());
        //String[] msg = event.getMessage().toUpperCase().split(",");
        switch (message.getString("TYPE").toUpperCase()){
            case "SEND_PLAYER_TO_SERVER": {
                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                    if(player.getUniqueId().toString().equals(message.getString("PLAYER"))){
                        player.connect(ProxyServer.getInstance().getServerInfo(message.getString("SERVER")));
                        return;
                    }
                    if(player.getName().toLowerCase().equals(message.getString("PLAYER").toLowerCase())) {
                        player.connect(ProxyServer.getInstance().getServerInfo(message.getString("SERVER")));
                        return;
                    }
                }
            }
            case "TELEPORT_REQUEST_TPA": {
                messagecoder messagecode = new messagecoder();
                messagecode.setSender(event.getSender());
                messagecode.setReceiver(ProxyServer.getInstance().getPlayer(message.getString("TARGET_PLAYER")).getServer().getInfo().getName());
                messagecode.setChannel("MISF_PHOENIX");
                messagecode.setType("ACTION");
                messagecode.setMessage(event.getMessage());
                worldSocket.getInstance().sendmessage(messagecode.createmessage());
            }

            case "TELEPORT_REQUEST_TPAHERE": {
                messagecoder messagecode = new messagecoder();
                messagecode.setSender(event.getSender());
                messagecode.setReceiver(ProxyServer.getInstance().getPlayer(message.getString("TARGET_PLAYER")).getServer().getInfo().getName());
                messagecode.setChannel("MISF_PHOENIX");
                messagecode.setType("ACTION");
                messagecode.setMessage(event.getMessage());
                worldSocket.getInstance().sendmessage(messagecode.createmessage());
            }

            case "TELEPORT_REQUEST_TPA_ACCEPT": {
                messagecoder messagecode = new messagecoder();
                messagecode.setSender(event.getSender());
                messagecode.setReceiver(ProxyServer.getInstance().getPlayer(message.getString("PLAYER")).getServer().getInfo().getName());
                messagecode.setChannel("MISF_PHOENIX");
                messagecode.setType("ACTION");
                messagecode.setMessage(event.getMessage());
                worldSocket.getInstance().sendmessage(messagecode.createmessage());
            }

            case "TELEPORT_REQUEST_TPAHERE_ACCEPT": {
                messagecoder messagecode = new messagecoder();
                messagecode.setSender(event.getSender());
                messagecode.setReceiver(ProxyServer.getInstance().getPlayer(message.getString("PLAYER")).getServer().getInfo().getName());
                messagecode.setChannel("MISF_PHOENIX");
                messagecode.setType("ACTION");
                messagecode.setMessage(event.getMessage());
                worldSocket.getInstance().sendmessage(messagecode.createmessage());
            }

            case "TELEPORT_REQUEST_DENY": {
                messagecoder messagecode = new messagecoder();
                messagecode.setSender(event.getSender());
                messagecode.setReceiver(ProxyServer.getInstance().getPlayer(message.getString("PLAYER")).getServer().getInfo().getName());
                messagecode.setChannel("MISF_PHOENIX");
                messagecode.setType("ACTION");
                messagecode.setMessage(event.getMessage());
                worldSocket.getInstance().sendmessage(messagecode.createmessage());
            }

            default: return;
        }
    }
}

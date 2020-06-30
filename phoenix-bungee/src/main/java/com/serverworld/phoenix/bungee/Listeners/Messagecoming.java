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

import com.serverworld.phoenix.bungee.util.DebugMessage;
import com.serverworld.worldSocket.bungeecord.events.MessagecomingEvent;
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
                default:return;

                case "COMMAND": ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(),event.getMessage());

                case "ACTION": Actions(event);
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
            default: return;

            case "SENDPLAYERTOSERVER": {
                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                    if(player.getUniqueId().toString().equals(message.getString("PLAYER"))){
                        player.connect(ProxyServer.getInstance().getServerInfo(message.getString("SERVER")));
                        return;
                    }
                }

            }

        }
    }
}

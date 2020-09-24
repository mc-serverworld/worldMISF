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

package com.serverworld.phoenix.bungee.Listeners.subListeners;

import com.google.gson.JsonObject;
import com.serverworld.phoenix.bungee.BungeePhoenix;
import com.serverworld.phoenix.bungee.util.DebugMessage;
import com.serverworld.worldSocket.bungeecord.events.MessagecomingEvent;
import com.serverworld.worldSocket.bungeecord.util.messagecoder;
import com.serverworld.worldSocket.bungeecord.worldSocket;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class Sync_v2 {

    private int totalservers;
    private String serverlist;
    private int totalplayers;
    private String playerlist;

    public Sync_v2(MessagecomingEvent event) {
        try {
            //JSONObject message = new JSONObject(event.getMessage());
            DebugMessage.sendInfoIfDebug("Sync v2 triggered: " + event.getMessage());
            switch (event.getMessage().toUpperCase()){
                case "REQUEST_BUNGEE_INFO_V1": BUNGEE_INFO_V1(event);

                default: return;

            }
        }catch (Exception e){
            e.printStackTrace();
            DebugMessage.sendWarring("Error on Sync_v2" + event.getMessage());
        }
    }

    private static String convertObjectArrayToString(Object[] arr, String delimiter) {
        if (arr.length==1)
            return arr[0].toString();
        StringBuilder sb = new StringBuilder();
        for (Object obj : arr)
            sb.append(obj.toString()).append(delimiter);
        return sb.substring(0, sb.length() - 1);

    }

    private void BUNGEE_INFO_V1(MessagecomingEvent event){
        if (BungeePhoenix.getInstance().getProxy().getPlayers().isEmpty())
            return;
        totalservers = BungeePhoenix.getInstance().getProxy().getServers().size();
        serverlist = convertObjectArrayToString(BungeePhoenix.getInstance().getProxy().getServers().keySet().toArray(new String[0]),",");
        totalplayers = BungeePhoenix.getInstance().getProxy().getPlayers().size();
        List<String> plist = new ArrayList<String>();
        for(ProxiedPlayer p: BungeePhoenix.getInstance().getProxy().getPlayers()){
            plist.add(p.getName());
        }
        playerlist = convertObjectArrayToString(plist.toArray(), ",");

        JsonObject json = new JsonObject();
        json.addProperty("TOTAL_SERVERS", totalservers);
        json.addProperty("SERVERLIST", serverlist);
        json.addProperty("TOTAL_PLAYERS",totalplayers);
        json.addProperty("PLAYERLIST",playerlist);
        json.addProperty("TYPE","RETURN_BUNGEE_INFO_V1");

        messagecoder messagecode = new messagecoder();
        messagecode.setSender("PROXY");
        messagecode.setReceiver(event.getSender());
        messagecode.setChannel("MISF_PHOENIX");
        messagecode.setType("SYNC_V2");
        messagecode.setMessage(json.toString());
        worldSocket.getInstance().sendmessage(messagecode.createmessage());
        return;
    }

}

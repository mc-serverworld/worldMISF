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

package com.serverworld.phoenix.paper.Listeners.subListeners;

import com.serverworld.phoenix.paper.util.DebugMessage;
import com.serverworld.worldSocket.paperspigot.events.MessagecomingEvent;
import org.json.JSONObject;

public class Sync_v2 {
    public Sync_v2(MessagecomingEvent event) {
        try {
            JSONObject message = new JSONObject(event.getMessage());
            DebugMessage.sendInfoIfDebug("Sync v2 triggered: " + event.getMessage());
            switch (message.getString("TYPE").toUpperCase()){
                case "Bungee": {
                    int totalservers;
                    String[] serverlist;
                    int totalplayers;
                    String[] playerlist;
                    serverlist = message.getString("PLAYERLIST").split(",");
                    return;
                }
                default: return;

            }
        }catch (Exception e){
            e.printStackTrace();
            DebugMessage.sendWarring("Error on Sync_v2" + event.getMessage());
        }
    }
}

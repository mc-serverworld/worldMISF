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

package com.serverworld.phoenix.paper.Listeners.queue;

import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.util.ArrayList;

public class TpQueue {

    //private static Player players;
    public static ArrayList<JSONObject> messages = new ArrayList<JSONObject>();

    public static void addQueue(JSONObject message){
        messages.removeIf(stuff -> stuff.getString("TARGET_PLAYER").toLowerCase().equals(message.getString("TARGET_PLAYER").toLowerCase()));
        //no remove with foreach (ConcurrentModificationException)
        messages.add(message);
    }

    public static JSONObject getAndDelQueue(Player player){
        for (JSONObject stuff:messages) {
            if(stuff.getString("TARGET_PLAYER").toLowerCase().equals(player.getName().toLowerCase())){
                messages.removeIf(stuf -> stuf.getString("TARGET_PLAYER").toLowerCase().equals(player.getName().toLowerCase()));
                //no remove with foreach (ConcurrentModificationException)
                return stuff;
            }
        }
        return null;
    }

    public static boolean hasQueue(Player player){
        if(messages.size()!=0){
            for (JSONObject stuff:messages){
                if(stuff.getString("TARGET_PLAYER").toLowerCase().equals(player.getName().toLowerCase()))
                    return true;
            }
        }
        return false;
    }
}

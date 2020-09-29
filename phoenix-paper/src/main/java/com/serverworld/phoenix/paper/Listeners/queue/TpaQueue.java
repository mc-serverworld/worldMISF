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

import com.serverworld.phoenix.paper.PaperPhoenix;
import org.bukkit.entity.Player;
import org.json.JSONObject;

public class TpaQueue {
    private Player player;

    public void addQueue(Player player , JSONObject message){
        //Player player = PaperPhoenix.getInstance().getServer().getPlayer(message.getString("PLAYER"));
        Player target_player = PaperPhoenix.getInstance().getServer().getPlayer(message.getString("TARGET_PLAYER"));
        if(!target_player.isOnline()){
            return;
        }
    }

    public void executeQueue(Player player){

    }

}

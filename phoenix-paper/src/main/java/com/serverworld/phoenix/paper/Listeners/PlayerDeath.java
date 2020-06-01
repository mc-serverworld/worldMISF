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
import com.serverworld.worldUserProfile.jsondata.UserPhoenixPlayerData;
import com.serverworld.worldUserProfile.paper.utils.UserPhoenixPlayerDataMySQL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    private PaperPhoenix paperPhoenix;

    public PlayerDeath(PaperPhoenix paperPhoenix){
        this.paperPhoenix = paperPhoenix;
    }

    @EventHandler
    public void onPlayerDearh(PlayerDeathEvent event){
        if(event.getEntity() instanceof Player) {
            UserPhoenixPlayerData playerData = UserPhoenixPlayerDataMySQL.getDataClass(event.getEntity().getUniqueId().toString());
            playerData.setLastlocation_server(event.getEntity().getServer().getServerName());
            playerData.setLastlocation_world(event.getEntity().getWorld().getName());
            playerData.setLastlocation_x(event.getEntity().getLocation().getX());
            playerData.setLastlocation_y(event.getEntity().getLocation().getY());
            playerData.setLastlocation_z(event.getEntity().getLocation().getZ());
            UserPhoenixPlayerDataMySQL.setDataClass(event.getEntity().getUniqueId().toString() , playerData);
            event.getEntity().spigot().respawn();
        }
    }
}

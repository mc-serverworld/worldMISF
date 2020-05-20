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
import com.serverworld.phoenix.paper.util.worldinfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {

    PaperPhoenix paperPhoenix;

    public PlayerRespawn(PaperPhoenix paperPhoenix){
        this.paperPhoenix = paperPhoenix;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerRespawn(PlayerRespawnEvent event){
        if(paperPhoenix.config.chunk_position_x()==0&& paperPhoenix.config.chunk_position_y()==0)
            return;
        event.setRespawnLocation(worldinfo.getcenterlocation(event.getPlayer().getWorld()));
    }
}

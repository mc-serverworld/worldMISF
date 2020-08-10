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

package com.serverworld.phoenix.paper.util;

import com.serverworld.phoenix.paper.PaperPhoenix;
import org.bukkit.Location;

public class worldSetup {
    public worldSetup(){
        setworldspawn();
    }

    void setworldspawn(){
        String worldtype = PaperPhoenix.config.worldtype();
        if(PaperPhoenix.config.worldtype().toLowerCase().equals("end"))
            worldtype = "world_the_end";
        else if(PaperPhoenix.config.worldtype().toLowerCase().equals("nether"))
            worldtype = "world_nether";
        else {
            worldtype = "world";
        }
        Location spawn = new Location(PaperPhoenix.getInstance().getServer().getWorld(worldtype),PaperPhoenix.config.spawnx(),PaperPhoenix.config.spawny(),PaperPhoenix.config.spawnz());
        if(PaperPhoenix.config.servername().equals(String.valueOf(PaperPhoenix.config.serversprefix() + "OVERWORLD_0_0")))
            PaperPhoenix.getInstance().getServer().getWorld(worldtype).setSpawnLocation(spawn);
        else
            PaperPhoenix.getInstance().getServer().getWorld(worldtype).setSpawnLocation(worldInfo.getCenterLocation(PaperPhoenix.getInstance().getServer().getWorld(worldtype)));
    }
}

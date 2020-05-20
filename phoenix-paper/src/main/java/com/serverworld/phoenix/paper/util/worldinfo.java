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
import org.bukkit.World;

public class worldinfo {
    static PaperPhoenix bukkitphoenix;

    public worldinfo(PaperPhoenix paperPhoenix){
        this.bukkitphoenix = paperPhoenix;
    }

    public static Location getcenterlocation(World world){
        double x,y;
        x = bukkitphoenix.config.chunk_position_x() * bukkitphoenix.config.chunksize();
        if(x<0){
            x = x - bukkitphoenix.config.chunksize();
        }else {
            x = x + bukkitphoenix.config.chunksize();
        }

        y = bukkitphoenix.config.chunk_position_y() * bukkitphoenix.config.chunksize();
        if(y<0){
            y = y - bukkitphoenix.config.chunksize();
        }else {
            y = y + bukkitphoenix.config.chunksize();
        }
        Location center = null;
        center.set(x,y,255d);
        center.setWorld(world);
        return center;
    }

    public static double getcenterx(){
        double x;
        x = bukkitphoenix.config.chunk_position_x() * bukkitphoenix.config.chunksize();
        if(x<0){
            x = x - bukkitphoenix.config.chunksize();
        }else {
            x = x + bukkitphoenix.config.chunksize();
        }
        return x;
    }

    public static double getcentery(){
        double y;
        y = bukkitphoenix.config.chunk_position_y() * bukkitphoenix.config.chunksize();
        if(y<0){
            y = y - bukkitphoenix.config.chunksize();
        }else {
            y = y + bukkitphoenix.config.chunksize();
        }
        return y;
    }

}

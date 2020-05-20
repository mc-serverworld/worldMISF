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
    private static PaperPhoenix paperPhoenix;

    public worldinfo(PaperPhoenix paperPhoenix){
        this.paperPhoenix = paperPhoenix;
    }

    public static Location getcenterlocation(World world){
        double x,y;
        x = paperPhoenix.config.chunk_position_x() * paperPhoenix.config.chunksize();
        if(x<0){
            x = x - paperPhoenix.config.chunksize();
        }else {
            x = x + paperPhoenix.config.chunksize();
        }

        y = paperPhoenix.config.chunk_position_y() * paperPhoenix.config.chunksize();
        if(y<0){
            y = y - paperPhoenix.config.chunksize();
        }else {
            y = y + paperPhoenix.config.chunksize();
        }
        Location center = null;
        center.set(x,y,255d);
        center.setWorld(world);
        return center;
    }

    public static double getcenterx(){
        double x;
        x = paperPhoenix.config.chunk_position_x() * paperPhoenix.config.chunksize();
        if(x<0){
            x = x - paperPhoenix.config.chunksize();
        }else {
            x = x + paperPhoenix.config.chunksize();
        }
        return x;
    }

    public static double getcentery(){
        double y;
        y = paperPhoenix.config.chunk_position_y() * paperPhoenix.config.chunksize();
        if(y<0){
            y = y - paperPhoenix.config.chunksize();
        }else {
            y = y + paperPhoenix.config.chunksize();
        }
        return y;
    }

}

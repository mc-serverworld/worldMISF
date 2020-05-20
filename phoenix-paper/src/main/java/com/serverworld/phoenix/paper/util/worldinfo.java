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
import org.bukkit.Material;
import org.bukkit.World;

public class worldinfo {
    private static PaperPhoenix paperPhoenix;

    public worldinfo(PaperPhoenix paperPhoenix){
        this.paperPhoenix = paperPhoenix;
    }

    public static Location getcenterlocation(World world){
        double x,z;
        x = paperPhoenix.config.chunk_position_x() * paperPhoenix.config.chunksize();
        if(x<0){
            x = x - paperPhoenix.config.chunksize();
        }else {
            x = x + paperPhoenix.config.chunksize();
        }

        z = paperPhoenix.config.chunk_position_z() * paperPhoenix.config.chunksize();
        if(z<0){
            z = z - paperPhoenix.config.chunksize();
        }else {
            z = z + paperPhoenix.config.chunksize();
        }
        Location center = new Location(world,x,255d,z);
        center.set(x,255d, z);
        center.setWorld(world);
        while (true){
            if(center.getBlock().isEmpty()){
                center.setY(center.getBlockY()-1);
            }else if(center.getBlock().isLiquid()){
                center.getBlock().setType(Material.COBBLESTONE);
                center.setY(center.getY()+4);
                break;
            }else {
                center.setY(center.getY()+4);
                break;
            }
        }

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

    public static double getcenterz(){
        double z;
        z = paperPhoenix.config.chunk_position_z() * paperPhoenix.config.chunksize();
        if(z<0){
            z = z - paperPhoenix.config.chunksize();
        }else {
            z = z + paperPhoenix.config.chunksize();
        }
        return z;
    }

}
